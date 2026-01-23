package org.rakeshg.retailstore.store.product.service;

import org.rakeshg.retailstore.store.product.command.CreateProductCommand;
import org.rakeshg.retailstore.store.product.model.Product;

import java.util.List;

public interface ProductService {
    Product addProduct(Long storeId, CreateProductCommand command);
    List<Product> getActiveProducts(Long storeId);
//    Product update(Long id, Product product);
//    void delete(Long id);
}
