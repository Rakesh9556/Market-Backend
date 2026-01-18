package org.rakeshg.retailstore.store.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductByBarcodeRequest {
    @NotBlank private String barcode;

    @NotBlank private String sku;

    @NotBlank private String name;

    @NotNull private Long categoryId;

    @NotNull @Positive private BigDecimal price;

    @NotNull @PositiveOrZero private Integer stock;
}
