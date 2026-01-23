package org.rakeshg.retailstore.store.product.repository;

import org.rakeshg.retailstore.store.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByIdAndStoreId(Long id, Long storeId);
    List<Product> findByStoreIdAndActiveTrueOrderByLastSoldAtDesc(Long storeId);
    boolean existsByStoreIdAndNameIgnoreCase(Long storeId, String name);
}
