package org.rakeshg.retailstore.store.product.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.rakeshg.retailstore.common.exception.BusinessException;
import org.rakeshg.retailstore.inventory.service.InventoryService;
import org.rakeshg.retailstore.store.product.command.CreateProductCommand;
import org.rakeshg.retailstore.store.product.model.Product;
import org.rakeshg.retailstore.store.product.repository.ProductRepository;
import org.rakeshg.retailstore.store.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final InventoryService inventoryService;

    @Transactional
    @Override
    public Product addProduct(Long storeId, CreateProductCommand command) {
        // Check product already exists or not
        String productName = command.getName().trim().replaceAll("\\s+", " ");

        if(productRepository.existsByStoreIdAndNameIgnoreCase(storeId, productName)) {
            throw new BusinessException("Product already added", "PRODUCT_ALREADY_EXISTS");
        }

        Product product = Product.builder()
                .storeId(storeId)
                .name(productName)
                .price(command.getPrice())
                .unit(command.getUnit())
                .category(command.getCategory())
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();

        // save product
        Product savedProduct = productRepository.save(product);

        // create inventory for the product
        inventoryService.createInventoryItem(
                storeId,
                savedProduct.getId(),
                command.getOpeningStock(),
                command.getReorderLevel()
        );

        return savedProduct;
    }

    @Override
    public List<Product> getActiveProducts(Long storeId) {
        return productRepository.findByStoreIdAndActiveTrue(storeId);
    }

    @Override
    public int getTotalActiveProducts(Long storeId) {
        return productRepository.countByStoreIdAndActiveTrue(storeId);
    }

    @Override
    public Optional<Product> findByIdAndStoreId(Long productId, Long storeId) {
        return productRepository.findByIdAndStoreId(productId, storeId);
    }

    @Override
    public List<Product> findByIdsAndStoreId(List<Long> ids, Long storeId) {
        return productRepository.findByIdInAndStoreIdAndActiveTrue(ids, storeId);
    }

}
