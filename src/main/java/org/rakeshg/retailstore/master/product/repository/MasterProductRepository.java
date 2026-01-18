package org.rakeshg.retailstore.master.product.repository;

import org.rakeshg.retailstore.master.product.model.MasterProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MasterProductRepository extends JpaRepository<MasterProduct, Long> {
    Optional<MasterProduct> findByBarcode(String barcode);
    boolean existsByBarcode(String barcode);
}
