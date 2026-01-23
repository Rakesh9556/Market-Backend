package org.rakeshg.retailstore.store.product.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

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
}
