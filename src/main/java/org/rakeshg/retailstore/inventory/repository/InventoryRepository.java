package org.rakeshg.retailstore.inventory.repository;

import org.rakeshg.retailstore.inventory.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {
    Optional<InventoryItem> findByStoreIdAndProductIdAndActiveTrue(Long storeId, Long productId);
    List<InventoryItem> findByStoreIdAndActiveTrue(Long storeId);
    boolean existsByStoreIdAndProductId(Long storeId, Long productId);

    @Query("""
    SELECT i FROM InventoryItem i
    WHERE i.storeId = :storeId
      AND i.active = true
      AND i.reorderLevel IS NOT NULL
      AND i.availableStock <= i.reorderLevel
    """)
    List<InventoryItem> findLowStockItems(Long storeId);
}
