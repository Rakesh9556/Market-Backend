package org.rakeshg.retailstore.inventory.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.rakeshg.retailstore.inventory.command.InventoryManipulationCommand;

import java.math.BigDecimal;

@Data
public class InventoryManipulationRequest {
    @NotNull private Long productId;
    @NotNull @Positive
    private BigDecimal quantity;

    public InventoryManipulationCommand toCommand() {
        return InventoryManipulationCommand.builder()
                .productId(productId)
                .quantity(quantity)
                .build();
    }
}
