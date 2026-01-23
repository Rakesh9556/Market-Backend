package org.rakeshg.retailstore.store.sales.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TodaySaleResponse {
    private BigDecimal totalAmount;
    private Integer totalSales;
}
