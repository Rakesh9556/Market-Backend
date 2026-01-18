package org.rakeshg.retailstore.store.product.service.impl;

import org.rakeshg.retailstore.store.product.model.ProductCategory;
import org.rakeshg.retailstore.store.product.repository.ProductCategoryRepository;
import org.rakeshg.retailstore.store.product.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategory create(ProductCategory category) {
        return null;
    }

    @Override
    public ProductCategory update(ProductCategory category) {
        return null;
    }

    @Override
    public void delete(ProductCategory category) {

    }

    @Override
    public ProductCategory getById(Long id) {
        return productCategoryRepository.findById(id).orElse(null);
    }
}
