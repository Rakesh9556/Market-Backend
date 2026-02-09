package org.rakeshg.retailstore.store.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.rakeshg.retailstore.security.principal.AuthUser;
import org.rakeshg.retailstore.store.product.dto.request.CreateProductRequest;
import org.rakeshg.retailstore.store.product.dto.response.ProductResponse;
import org.rakeshg.retailstore.store.product.model.Product;
import org.rakeshg.retailstore.store.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ProductResponse> createProduct(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody CreateProductRequest request
    ) {

        Product product = productService.addProduct(authUser.getStoreId(), request.toCommand());
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductResponse.from(product));
    }

    @GetMapping("/get")
    public List<Product> getProduct(@AuthenticationPrincipal AuthUser authUser) {
        return productService.getActiveProducts(authUser.getStoreId());
    }

}
