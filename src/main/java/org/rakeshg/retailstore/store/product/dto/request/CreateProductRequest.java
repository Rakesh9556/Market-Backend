package org.rakeshg.retailstore.store.product.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.rakeshg.retailstore.store.product.UnitType;
import org.rakeshg.retailstore.store.product.command.CreateProductCommand;

import java.math.BigDecimal;

@Data
public class CreateProductRequest {
    @NotBlank private String name;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    @Pattern(
            regexp = "PIECE|KG|LITRE|GRAM|ML|LOOSE",
            message = "Invalid unit type"
    )
    @NotBlank private String unit;

    private String category;

    @NotNull
    @Positive private BigDecimal openingStock;

    @Positive private BigDecimal reorderLevel;

    public CreateProductCommand toCommand() {
        return CreateProductCommand.builder()
                .name(name)
                .price(price)
                .unit(UnitType.valueOf(unit.toUpperCase()))
                .category(category)
                .openingStock(openingStock)
                .reorderLevel(reorderLevel)
                .build();
    }
}
