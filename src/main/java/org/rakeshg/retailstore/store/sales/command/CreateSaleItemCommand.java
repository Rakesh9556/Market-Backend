package org.rakeshg.retailstore.store.sales.command;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreateSaleItemCommand {
    private Long productId;
    private BigDecimal quantity;
}
