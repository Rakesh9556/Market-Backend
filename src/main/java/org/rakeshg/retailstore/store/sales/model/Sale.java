package org.rakeshg.retailstore.store.sales.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "sales",
        indexes = {
                @Index(name = "idx_sales_store_data", columnList = "storeId, createdAt")
        }
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long storeId;

    private BigDecimal totalAmount;
    private Integer totalItem;

    private LocalDateTime createdAt;
}
