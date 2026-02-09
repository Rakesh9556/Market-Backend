package org.rakeshg.retailstore.store.sales.repository;

import org.rakeshg.retailstore.store.sales.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("""
    SELECT COALESCE(SUM(s.totalAmount), 0)
    FROM Sale s
    WHERE s.storeId = :storeId
      AND s.createdAt BETWEEN :start AND :end
    """)
    BigDecimal getTodayTotalSales(
            @Param("storeId") Long storeId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
    Integer countByStoreIdAndCreatedAtBetween(
            Long storeId,
            LocalDateTime start,
            LocalDateTime end
    );
    List<Sale> findByStoreIdOrderByCreatedAtDesc(Long storeId);
    Optional<Sale> findByIdAndStoreId(Long id, Long storeId);
    List<Sale> findTop3ByStoreIdOrderByCreatedAtDesc(Long storeId);
}
