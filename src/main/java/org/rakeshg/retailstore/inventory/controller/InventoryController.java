package org.rakeshg.retailstore.inventory.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.rakeshg.retailstore.inventory.dto.request.InventoryManipulationRequest;
import org.rakeshg.retailstore.inventory.dto.response.InventoryItemResponse;
import org.rakeshg.retailstore.inventory.dto.response.InventorySummaryResponse;
import org.rakeshg.retailstore.inventory.model.InventoryItem;
import org.rakeshg.retailstore.inventory.service.InventoryService;
import org.rakeshg.retailstore.security.principal.AuthUser;
import org.rakeshg.retailstore.store.product.model.Product;
import org.rakeshg.retailstore.store.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    private final ProductService productService;

    @GetMapping
    public List<InventoryItemResponse> getInventory(@AuthenticationPrincipal AuthUser authUser) {
        List<InventoryItem> items = inventoryService.getInventoryByStore(authUser.getStoreId());
        return items.stream()
                .map(item -> {
                    Product product = productService.findByIdAndStoreId(item.getProductId(), authUser.getStoreId())
                            .orElseThrow();

                    return InventoryItemResponse.from(item, product);
                })
                .toList();
    }

    @GetMapping("/summary")
    public InventorySummaryResponse getInventorySummary(@AuthenticationPrincipal AuthUser authUser) {
        return inventoryService.getInventorySummary(authUser.getStoreId());
    }

    @GetMapping("/low-stock")
    public List<InventoryItemResponse> getLowStockInventory(@AuthenticationPrincipal AuthUser authUser) {
        List<InventoryItem> items = inventoryService.getLowStockItems(authUser.getStoreId());
        return items.stream()
                .map(item -> {
                    Product product = productService.findByIdAndStoreId(item.getProductId(), authUser.getStoreId())
                            .orElseThrow();

                    return InventoryItemResponse.from(item, product);
                })
                .toList();
    }

    @PostMapping("/add")
    public ResponseEntity<InventoryItemResponse> addStock(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody InventoryManipulationRequest request
    ) {

        // add stock
        InventoryItem item = inventoryService.addStock(authUser.getStoreId(), request.toCommand());

        // retrieve product
        Product product = productService.findByIdAndStoreId(request.getProductId(), authUser.getStoreId())
                .orElseThrow();

        return ResponseEntity.status(HttpStatus.OK).body(InventoryItemResponse.from(item, product));

    }

    @PostMapping("/deduct")
    public ResponseEntity<InventoryItemResponse> deductStock(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody InventoryManipulationRequest request
    ) {

        // add stock
        InventoryItem item = inventoryService.deductStock(authUser.getStoreId(), request.getProductId(), request.getQuantity());

        // retrieve product
        Product product = productService.findByIdAndStoreId(request.getProductId(), authUser.getStoreId())
                .orElseThrow();

        return ResponseEntity.status(HttpStatus.OK).body(InventoryItemResponse.from(item, product));

    }
}
