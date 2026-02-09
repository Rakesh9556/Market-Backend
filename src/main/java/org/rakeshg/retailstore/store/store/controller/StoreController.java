package org.rakeshg.retailstore.store.store.controller;

import lombok.RequiredArgsConstructor;
import org.rakeshg.retailstore.auth.dto.response.AuthResponse;
import org.rakeshg.retailstore.auth.service.AuthService;
import org.rakeshg.retailstore.security.principal.AuthUser;
import org.rakeshg.retailstore.store.store.dto.request.OnboardStoreRequest;
import org.rakeshg.retailstore.store.store.dto.response.DashboardSummaryResponse;
import org.rakeshg.retailstore.store.store.service.OnboardingService;
import org.rakeshg.retailstore.store.store.service.StoreService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final AuthService authService;
    private final OnboardingService onboardService;
    private final StoreService storeService;

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

    @GetMapping("/dashboard/summary")
    DashboardSummaryResponse dashboardSummary(@AuthenticationPrincipal AuthUser authUser) {
        return storeService.getSummary(authUser.getStoreId());
    }

}
