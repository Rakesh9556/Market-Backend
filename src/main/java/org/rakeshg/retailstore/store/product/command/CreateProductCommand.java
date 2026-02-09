package org.rakeshg.retailstore.store.product.command;

import lombok.Builder;
import lombok.Data;
import org.rakeshg.retailstore.store.product.UnitType;

import java.math.BigDecimal;

@Data
@Builder
public class CreateProductCommand {
    private String name;
    private BigDecimal price;
    private UnitType unit;
    private String category;
    private BigDecimal openingStock;
    private BigDecimal reorderLevel;
}
