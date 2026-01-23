package org.rakeshg.retailstore.auth.service;

import org.rakeshg.retailstore.auth.dto.response.AuthResponse;
import org.rakeshg.retailstore.auth.dto.response.SendOtpResponse;

public interface AuthService {
//    void sendOtp(String phone);
    SendOtpResponse sendOtp(String phone);
    AuthResponse verifyOtpAndLogin(String phone, String otp);
    AuthResponse reIssueTokensAfterOnboarding(Long userId);
    AuthResponse refreshAccessToken(String token);
    void logout(String token);
}
