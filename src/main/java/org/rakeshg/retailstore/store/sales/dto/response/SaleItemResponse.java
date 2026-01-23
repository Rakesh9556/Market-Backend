package org.rakeshg.retailstore.store.sales.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SaleItemResponse {
    private Long id;
    private String name;
    private BigDecimal quantity;
    private String unit;
    private BigDecimal price;
}
