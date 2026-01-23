package org.rakeshg.retailstore.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.rakeshg.retailstore.auth.dto.request.RefreshTokenRequest;
import org.rakeshg.retailstore.auth.dto.request.SendOtpRequest;
import org.rakeshg.retailstore.auth.dto.request.VerifyOtpRequest;
import org.rakeshg.retailstore.auth.dto.response.SendOtpResponse;
import org.rakeshg.retailstore.auth.dto.response.AuthResponse;
import org.rakeshg.retailstore.auth.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/send-otp")
    public SendOtpResponse senOtp(@Valid @RequestBody SendOtpRequest request) {
        return authService.sendOtp(request.getPhone());
    }

    @PostMapping("/verify-otp")
    public AuthResponse verifyOtp(@Valid @RequestBody VerifyOtpRequest request) {
        return authService.verifyOtpAndLogin(request.getPhone(), request.getOtp());
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return authService.refreshAccessToken(request.getToken());
    }

    @PostMapping("/logout")
    public void logout(@Valid @RequestBody RefreshTokenRequest request) {
        authService.logout(request.getToken());
    }
}
