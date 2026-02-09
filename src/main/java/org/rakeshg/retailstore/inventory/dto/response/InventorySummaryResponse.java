package org.rakeshg.retailstore.inventory.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class InventorySummaryResponse {
    int totalItems;
    int lowStockItems;
    int stockHealthPercentage;
    BigDecimal totalInventoryValue;

}
