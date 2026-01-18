package org.rakeshg.retailstore.store.product.repository;

import org.rakeshg.retailstore.store.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findBySku(String sku);
    Optional<Product> findByBarcode(String barcode);
}
