package org.rakeshg.retailstore.store.product.service;

import org.rakeshg.retailstore.store.product.command.CreateProductCommand;
import org.rakeshg.retailstore.store.product.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product addProduct(Long storeId, CreateProductCommand command);
    List<Product> getActiveProducts(Long storeId);
    int getTotalActiveProducts(Long storeId);
    Optional<Product> findByIdAndStoreId(Long productId, Long storeId);
    List<Product> findByIdsAndStoreId(List<Long> ids, Long storeId);
}
