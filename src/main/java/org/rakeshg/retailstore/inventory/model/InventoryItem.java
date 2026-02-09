package org.rakeshg.retailstore.inventory.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "inventory_items",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"store_id", "product_id"})
        },
        indexes = {
                @Index(name = "idx_inventory_store", columnList = "store_id"),
                @Index(name = "idx_inventory_product", columnList = "product_id")
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_id", nullable = false)
    private Long storeId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(nullable = false, precision = 38, scale = 2)
    private BigDecimal availableStock;

    @Column(precision = 38, scale = 2)
    private BigDecimal reservedStock;

    @Column(precision = 38, scale = 2)
    private BigDecimal reorderLevel;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;
}