package org.rakeshg.retailstore.auth.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rakeshg.retailstore.auth.repository.OtpSessionRepository;
import org.rakeshg.retailstore.auth.model.OtpSession;
import org.rakeshg.retailstore.auth.service.OtpService;
import org.rakeshg.retailstore.common.exception.business_exception.InvalidOtpException;
import org.rakeshg.retailstore.common.exception.business_exception.OtpExpiredException;
import org.rakeshg.retailstore.common.exception.business_exception.OtpNotFoundException;
import org.rakeshg.retailstore.common.exception.business_exception.OtpRateLimitExceededException;
import org.rakeshg.retailstore.notification.service.SmsSenderService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {
    private static final int OTP_EXPIRY_MINUTES = 15;
    private static final int MAX_ACTIVE_OTP_PER_PHONE = 3;

    private final OtpSessionRepository otpSessionRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /*
    @Override
    public void sendOtp(String phone) {

        enforceRateLimit(phone);

        String otp = generateOtp();
        String hashedOtp = passwordEncoder.encode(otp);

        OtpSession otpSession = OtpSession.builder()
                .phone(phone)
                .hashedOtp(hashedOtp)
                .expiresAt(LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES))
                .used(false)
                .build();

        otpSessionRepository.save(otpSession);

        // Call Fast2SmsService
        String message = "Your OTP for login is " + otp + ". Valid for 5 minutes.";
        smsSenderService.send(phone, message);
        log.info("Otp created successfully");

    }
    */

    @Override
    public String sendOtp(String phone) {

        enforceRateLimit(phone);

        String otp = generateOtp();
        String hashedOtp = passwordEncoder.encode(otp);

        OtpSession otpSession = OtpSession.builder()
                .phone(phone)
                .hashedOtp(hashedOtp)
                .expiresAt(LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES))
                .used(false)
                .build();

        otpSessionRepository.save(otpSession);

        return otp;
    }

    @Transactional
    @Override
    public void validateOtp(String phone, String otp) {
        // Check otp exist for the given phone or not
        OtpSession session = otpSessionRepository.findTopByPhoneAndUsedFalseOrderByExpiresAtDesc(phone)
                .orElseThrow(OtpNotFoundException::new);

        // Check the present otp is valid or not
        if(session.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new OtpExpiredException();
        }

        // validate the otp with session otp
        if(!passwordEncoder.matches(otp, session.getHashedOtp())) {
            throw new InvalidOtpException();
        }

        // if valid: mark it as used
        session.setUsed(true);
        otpSessionRepository.save(session);
        log.info("Otp validated successfully");

    }

    @Override
    public String generateOtp() {
        return String.valueOf(100000 + new SecureRandom().nextInt(900000));
    }

    private void enforceRateLimit(String phone) {
        long activeOtpCount = otpSessionRepository.countByPhoneAndUsedFalseAndExpiresAtAfter(
                phone,
                LocalDateTime.now()
        );

        if (activeOtpCount >= MAX_ACTIVE_OTP_PER_PHONE) {
            throw new OtpRateLimitExceededException();
        }
    }
}
