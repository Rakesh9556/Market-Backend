package org.rakeshg.retailstore.store.store.controller;

import lombok.RequiredArgsConstructor;
import org.rakeshg.retailstore.auth.dto.response.AuthResponse;
import org.rakeshg.retailstore.auth.service.AuthService;
import org.rakeshg.retailstore.security.principal.AuthUser;
import org.rakeshg.retailstore.store.store.dto.request.OnboardStoreRequest;
import org.rakeshg.retailstore.store.store.service.OnboardingService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final AuthService authService;
    private final OnboardingService onboardService;

    @PostMapping("/onboard")
    AuthResponse onboardStore(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody OnboardStoreRequest request
    ) {
        // Create store
        onboardService.onboardStore(authUser.getUserId(), request.toCommand());

        // reissue tokens
        return authService.reIssueTokensAfterOnboarding(authUser.getUserId());

    }

}
