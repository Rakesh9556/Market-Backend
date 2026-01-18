package org.rakeshg.retailstore.store.product.repository;

import org.rakeshg.retailstore.store.product.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    Optional<ProductCategory> findByName(String name);

}
