package org.rakeshg.retailstore.inventory.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.rakeshg.retailstore.common.exception.BusinessException;
import org.rakeshg.retailstore.inventory.command.InventoryManipulationCommand;
import org.rakeshg.retailstore.inventory.dto.response.InventorySummaryResponse;
import org.rakeshg.retailstore.inventory.model.InventoryItem;
import org.rakeshg.retailstore.inventory.repository.InventoryRepository;
import org.rakeshg.retailstore.inventory.service.InventoryService;
import org.rakeshg.retailstore.store.product.model.Product;
import org.rakeshg.retailstore.store.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public void createInventoryItem(Long storeId, Long productId, BigDecimal initialStock, BigDecimal reorderLevel) {
        // check if inventory item already exist or not
        if(inventoryRepository.existsByStoreIdAndProductId(storeId, productId)) {
            throw new BusinessException("Inventory already exist for product", "INVENTORY_ALREADY_EXIST");
        }

        // validate stock quantity
        validateQuantity(initialStock, "INVALID_OPENING_STOCK");

        // validate stock reorder level
        if(reorderLevel != null && reorderLevel.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Reorder level cannot be negative", "INVALID_REORDER_LEVEL");
        }

        InventoryItem item = InventoryItem.builder()
                .storeId(storeId)
                .productId(productId)
                .availableStock(initialStock)
                .reservedStock(BigDecimal.ZERO)
                .reorderLevel(reorderLevel)
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();

        // save
        inventoryRepository.save(item);
    }

    @Transactional
    @Override
    public InventoryItem addStock(Long storeId, InventoryManipulationCommand command) {
        // validate quantity
        validateQuantity(command.getQuantity(), "INVALID_QUANTITY");

        // find inventory item
        InventoryItem item = inventoryRepository.findByStoreIdAndProductIdAndActiveTrue(storeId, command.getProductId())
                .orElseThrow(() -> new BusinessException("Inventory not exist for product", "INVENTORY_NOT_EXIST"));

        // add stock
        item.setAvailableStock(
                item.getAvailableStock().add(command.getQuantity())
        );

        return inventoryRepository.save(item);

    }

    @Override
    public InventoryItem deductStock(Long storeId, Long productId, BigDecimal quantity) {
        // validate quantity
        validateQuantity(quantity, "INVALID_QUANTITY");

        // find inventory item
        InventoryItem item = inventoryRepository.findByStoreIdAndProductIdAndActiveTrue(storeId, productId)
                .orElseThrow(() -> new BusinessException("Inventory not exist for product", "INVENTORY_NOT_EXIST"));

        // validate if quantity valid after update
        if(item.getAvailableStock().compareTo(quantity) < 0) {
            throw new BusinessException("Insufficient stock", "INSUFFICIENT_STOCK");
        }

        // update quantity
        item.setAvailableStock(
                item.getAvailableStock().subtract(quantity)
        );

        return inventoryRepository.save(item);
    }

    @Override
    public List<InventoryItem> getInventoryByStore(Long storeId) {
        return inventoryRepository.findByStoreIdAndActiveTrue(storeId);
    }

    @Override
    public List<InventoryItem> getLowStockItems(Long storeId) {
        return inventoryRepository.findLowStockItems(storeId);
    }

    @Transactional
    @Override
    public InventorySummaryResponse getInventorySummary(Long storeId) {

        List<InventoryItem> items =
                inventoryRepository.findByStoreIdAndActiveTrue(storeId);

        if (items.isEmpty()) {
            return InventorySummaryResponse.builder()
                    .totalItems(0)
                    .lowStockItems(0)
                    .stockHealthPercentage(100)
                    .totalInventoryValue(BigDecimal.ZERO)
                    .build();
        }

        int totalItems = items.size();

        int lowStockItems = (int) items.stream()
                .filter(i ->
                        i.getReorderLevel() != null &&
                                i.getAvailableStock().compareTo(i.getReorderLevel()) < 0
                )
                .count();

        int stockHealth = calculateStockHealth(items);

        List<Long> productIds = items.stream()
                .map(InventoryItem::getProductId)
                .toList();

        Map<Long, Product> productMap = productRepository
                .findByIdInAndStoreIdAndActiveTrue(productIds, storeId)
                .stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        BigDecimal totalInventoryValue = items.stream()
                .map(i -> {
                    Product product = productMap.get(i.getProductId());
                    if (product == null) return BigDecimal.ZERO;
                    return product.getPrice().multiply(i.getAvailableStock());
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return InventorySummaryResponse.builder()
                .totalItems(totalItems)
                .lowStockItems(lowStockItems)
                .stockHealthPercentage(stockHealth)
                .totalInventoryValue(totalInventoryValue)
                .build();
    }

    private void validateQuantity(BigDecimal quantity, String errorCode) {
        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(
                    "Quantity must be greater than 0",
                    errorCode
            );
        }
    }

    private int calculateStockHealth(List<InventoryItem> items) {

        List<InventoryItem> reorderAware = items.stream()
                .filter(i ->
                        i.getReorderLevel() != null &&
                                i.getReorderLevel().compareTo(BigDecimal.ZERO) > 0
                )
                .toList();

        if (reorderAware.isEmpty()) return 100;

        BigDecimal avgRatio = reorderAware.stream()
                .map(i ->
                        i.getAvailableStock()
                                .divide(i.getReorderLevel(), 4, RoundingMode.HALF_UP)
                                .min(BigDecimal.ONE)
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(
                        BigDecimal.valueOf(reorderAware.size()),
                        2,
                        RoundingMode.HALF_UP
                );

        return avgRatio.multiply(BigDecimal.valueOf(100)).intValue();
    }
}
