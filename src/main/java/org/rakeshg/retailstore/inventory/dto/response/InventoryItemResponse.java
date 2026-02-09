package org.rakeshg.retailstore.inventory.dto.response;

import lombok.Builder;
import lombok.Data;
import org.rakeshg.retailstore.inventory.model.InventoryItem;
import org.rakeshg.retailstore.store.product.model.Product;

import java.math.BigDecimal;

@Data
@Builder
public class InventoryItemResponse {

    private Long productId;
    private String name;
    private String category;
    private String unit;

    private BigDecimal availableStock;
    private BigDecimal reorderLevel;
    private boolean lowStock;

    public static InventoryItemResponse from(InventoryItem item, Product product) {

        boolean lowStock = item.getReorderLevel() != null &&
                item.getAvailableStock().compareTo(item.getReorderLevel()) <= 0;

        return InventoryItemResponse.builder()
                .productId(product.getId())
                .name(product.getName())
                .category(product.getCategory())
                .unit(product.getUnit().name())
                .availableStock(item.getAvailableStock())
                .reorderLevel(item.getReorderLevel())
                .lowStock(lowStock)
                .build();
    }
}
