package org.rakeshg.retailstore.store.sales.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class SaleDetailResponse {
    private Long id;
    private BigDecimal totalAmount;
    private int totalItems;
    private LocalDateTime createdAt;
    private List<SaleItemResponse> items;
}
