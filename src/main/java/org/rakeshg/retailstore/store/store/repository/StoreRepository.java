package org.rakeshg.retailstore.store.store.repository;

import org.rakeshg.retailstore.store.store.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    boolean existsByPhone(String phone);
    Optional<Store> findStoreByPhone(String phone);
}
