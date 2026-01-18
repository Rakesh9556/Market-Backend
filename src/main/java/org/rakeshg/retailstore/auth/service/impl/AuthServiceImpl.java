package org.rakeshg.retailstore.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.rakeshg.retailstore.auth.dto.AuthResponse;
import org.rakeshg.retailstore.auth.dto.SendOtpResponse;
import org.rakeshg.retailstore.auth.model.RefreshToken;
import org.rakeshg.retailstore.auth.service.AuthService;
import org.rakeshg.retailstore.auth.service.JwtService;
import org.rakeshg.retailstore.auth.service.OtpService;
import org.rakeshg.retailstore.auth.service.RefreshTokenService;
import org.rakeshg.retailstore.common.exception.business_exception.InactiveUserException;
import org.rakeshg.retailstore.common.exception.business_exception.UserNotFoundException;
import org.rakeshg.retailstore.user.model.User;
import org.rakeshg.retailstore.user.repository.UserRepository;
import org.rakeshg.retailstore.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${security.jwt.access-token-expiry-seconds}")
    private long ACCESS_TOKEN_EXPIRY_SECONDS;

    private final OtpService otpService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final Environment environment;


//    @Override
//    public void sendOtp(String phone) {
//        otpService.sendOtp(phone);
//    }

    @Override
    public SendOtpResponse sendOtp(String phone) {
        String otp = otpService.sendOtp(phone);

        boolean exposedOtp = environment.getProperty(
                "otp.expose",
                Boolean.class,
                false
        );

        return new SendOtpResponse(exposedOtp ? otp : null);
    }


    @Override
    public AuthResponse verifyOtpAndLogin(String phone,String name, String otp) {
        // Verify otp
        otpService.validateOtp(phone, otp);

        // Retrieve user
        User user = userRepository.findByPhone(phone)
                .orElseGet(() -> userService.createOwnerUser(phone,name));

        // Check user is active
        if(!Boolean.TRUE.equals(user.getActive())) {
            throw new InactiveUserException();
        }

        // Issue tokens
        return issueTokens(user);

    }

    @Override
    public AuthResponse reIssueTokensAfterOnboarding(Long userId) {
        // Load user
        User updatedUser = userService.getUserById(userId);

        // revoke all existing sessions
        refreshTokenService.revokeAllForUser(userId);

        // issue fresh token to the user
        return issueTokens(updatedUser);
    }

    @Override
    public AuthResponse refreshAccessToken(String refreshToken) {
        RefreshToken session = refreshTokenService.validateAndGet(refreshToken);

        User user = userRepository.findById(session.getUserId())
                .orElseThrow(UserNotFoundException::new);

        if(!Boolean.TRUE.equals(user.getActive())) {
            throw new InactiveUserException();
        }

        String accessToken = jwtService.generate(user);
        String newRefreshToken = refreshTokenService.rotate(session, user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken)
                .expiresIn(ACCESS_TOKEN_EXPIRY_SECONDS)
                .build();
    }

    @Override
    public void logout(String refreshToken) {
        refreshTokenService.revoke(refreshToken);

    }

    private AuthResponse issueTokens(User user) {
        String accessToken = jwtService.generate(user);
        String refreshToken = refreshTokenService.generate(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(ACCESS_TOKEN_EXPIRY_SECONDS)
                .build();
    }
}
