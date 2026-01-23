package org.rakeshg.retailstore.store.product.service.impl;

import lombok.RequiredArgsConstructor;
import org.rakeshg.retailstore.common.exception.BusinessException;
import org.rakeshg.retailstore.store.product.command.CreateProductCommand;
import org.rakeshg.retailstore.store.product.model.Product;
import org.rakeshg.retailstore.store.product.repository.ProductRepository;
import org.rakeshg.retailstore.store.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product addProduct(Long storeId, CreateProductCommand command) {
        // Check product already exists or not
        if(productRepository.existsByStoreIdAndNameIgnoreCase(storeId, command.getName())) {
            throw new BusinessException("Product already added", "PRODUCT_ALREADY_EXISTS");
        }

        Product product = Product.builder()
                .storeId(storeId)
                .name(command.getName().trim())
                .price(command.getPrice())
                .unit(command.getUnit())
                .category(command.getCategory())
                .stock(BigDecimal.ZERO)
                .soldCount(0)
                .lastSoldAt(null)
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();

        return productRepository.save(product);

    }

    @Override
    public List<Product> getActiveProducts(Long storeId) {
        return productRepository.findByStoreIdAndActiveTrueOrderByLastSoldAtDesc(storeId);
    }
}
