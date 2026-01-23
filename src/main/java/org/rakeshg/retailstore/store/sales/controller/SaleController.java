package org.rakeshg.retailstore.store.sales.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.rakeshg.retailstore.security.principal.AuthUser;
import org.rakeshg.retailstore.store.sales.dto.request.CreateSaleRequest;
import org.rakeshg.retailstore.store.sales.dto.response.SaleDetailResponse;
import org.rakeshg.retailstore.store.sales.dto.response.SaleResponse;
import org.rakeshg.retailstore.store.sales.dto.response.TodaySaleResponse;
import org.rakeshg.retailstore.store.sales.service.SaleService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {
    private final SaleService saleService;

    @PostMapping("/create")
    public SaleResponse createSale(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody CreateSaleRequest request
    ) {
        return saleService.createSale(authUser.getStoreId(), request.toCommand());
    }

    @GetMapping("/{id}")
    SaleDetailResponse getSale(@PathVariable Long saleId, @AuthenticationPrincipal AuthUser authUser) {
        return saleService.getSaleById(authUser.getStoreId(), saleId);
    }

    @GetMapping("/today")
    TodaySaleResponse getTodaySales(@AuthenticationPrincipal AuthUser authUser) {
        return saleService.getTodaySales(authUser.getStoreId());
    }

    @GetMapping
    List<SaleResponse> getSales(@AuthenticationPrincipal AuthUser user) {
        return saleService.getSales(user.getStoreId());
    }

}
