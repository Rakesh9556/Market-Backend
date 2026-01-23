package org.rakeshg.retailstore.store.sales.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.rakeshg.retailstore.store.sales.command.CreateSaleItemCommand;

import java.math.BigDecimal;

@Data
public class SaleItemRequest {
    @NotNull private Long productId;
    @NotNull private BigDecimal quantity;

    public CreateSaleItemCommand toCommand() {
        return CreateSaleItemCommand.builder()
                .productId(productId)
                .quantity(quantity)
                .build();
    }
}
