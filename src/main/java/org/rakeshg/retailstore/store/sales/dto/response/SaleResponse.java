package org.rakeshg.retailstore.store.sales.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class SaleResponse {
    private Long id;
    private BigDecimal totalAmount;
    private Integer totalItems;
    private LocalDateTime createdAt;
}
