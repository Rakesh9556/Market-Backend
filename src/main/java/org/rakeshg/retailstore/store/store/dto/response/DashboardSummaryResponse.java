package org.rakeshg.retailstore.store.store.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class DashboardSummaryResponse {

    public TodaySales todaySales;
    public int totalProducts;
    public Inventory inventory;
    public List<RecentSale> recentSales;

    @Builder
    public static class TodaySales {
        public BigDecimal amount;
        public int transactionCount;
        public int percentageChange;
    }

    @Builder
    public static class Inventory {
        public BigDecimal totalValue;
        public int stockHealth;
        public int lowStockItems;
    }

    @Builder
    public static class RecentSale {
        public Long id;
        public BigDecimal totalAmount;
        public Integer totalItems;
        public long time;
    }

}
