package org.rakeshg.retailstore.auth.service;

public interface OtpService {
    // void sendOtp(String phone);
    String sendOtp(String phone);
    void validateOtp(String phone, String otp);
    String generateOtp();
}
