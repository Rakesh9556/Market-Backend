package org.rakeshg.retailstore.inventory.command;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class InventoryManipulationCommand {
    private Long productId;
    private BigDecimal quantity;
}
