package org.rakeshg.retailstore.store.store.repository;

import org.rakeshg.retailstore.store.store.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
}
