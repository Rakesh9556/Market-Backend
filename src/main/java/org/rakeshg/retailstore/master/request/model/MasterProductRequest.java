package org.rakeshg.retailstore.master.request.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rakeshg.retailstore.master.enums.MasterProductReqStatus;

import java.time.LocalDateTime;


@Entity
@Table(name = "master_product_requests")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MasterProductRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String barcode;

    @Column(nullable = false)
    private String suggestedName;

    @Column(nullable = false)
    private Long suggestedCategoryId;

    private String icon;

    @Column(nullable = false)
    private Long storeId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MasterProductReqStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime processedAt;
}

