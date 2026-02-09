package org.rakeshg.retailstore.inventory.service;

import org.rakeshg.retailstore.inventory.command.InventoryManipulationCommand;
import org.rakeshg.retailstore.inventory.dto.response.InventorySummaryResponse;
import org.rakeshg.retailstore.inventory.model.InventoryItem;

import java.math.BigDecimal;
import java.util.List;

public interface InventoryService {
    void createInventoryItem(Long storeId, Long productId, BigDecimal openingQuantity, BigDecimal reorderLevel);
    InventoryItem addStock(Long storeId, InventoryManipulationCommand command);
    InventoryItem deductStock(Long storeId, Long productId, BigDecimal quantity);
    List<InventoryItem> getInventoryByStore(Long storeId);
    List<InventoryItem> getLowStockItems(Long storeId);
    InventorySummaryResponse getInventorySummary(Long storeId);
}
