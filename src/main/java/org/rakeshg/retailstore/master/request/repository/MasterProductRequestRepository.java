package org.rakeshg.retailstore.master.request.repository;

import org.rakeshg.retailstore.master.request.model.MasterProductRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterProductRequestRepository extends  JpaRepository<MasterProductRequest, Long> {

}

