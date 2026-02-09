package org.rakeshg.retailstore.store.product.dto.response;

import lombok.Builder;
import org.rakeshg.retailstore.store.product.model.Product;

import java.math.BigDecimal;

@Builder
public class ProductResponse {
    public String name;
    public BigDecimal price;
    public String unit;
    private String category;
    public boolean active;

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .unit(product.getUnit().name())
                .category(product.getCategory())
                .active(product.getActive())
                .build();
    }
}

