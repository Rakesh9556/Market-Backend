package org.rakeshg.retailstore.store.product.service;

import org.rakeshg.retailstore.store.product.model.ProductCategory;

public interface CategoryService {
    ProductCategory create(ProductCategory category);
    ProductCategory update(ProductCategory category);
    void delete(ProductCategory category);
    ProductCategory getById(Long id);
}
