package org.rakeshg.retailstore.master.product.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rakeshg.retailstore.store.product.model.ProductCategory;
import org.rakeshg.retailstore.master.enums.Source;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "master_products",
uniqueConstraints = {
        @UniqueConstraint(columnNames = "barcode"),
        @UniqueConstraint(columnNames = "globalSku")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MasterProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String barcode;

    @Column(nullable = false)
    private String globalSku;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory category;

    // Additional product information
    private String brand;
    private BigDecimal price;
    private String unit;
    private String icon;
    private String description;

    // Metadata
    @Column(nullable = false)
    private Boolean isActive;

    @Column(nullable = false)
    private Boolean isVerified;

    @Column(nullable = false)
    private Source source;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime verifiedAt;

}
