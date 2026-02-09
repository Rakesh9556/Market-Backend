package org.rakeshg.retailstore.store.store.service.impl;

import lombok.RequiredArgsConstructor;
import org.rakeshg.retailstore.inventory.dto.response.InventorySummaryResponse;
import org.rakeshg.retailstore.inventory.service.InventoryService;
import org.rakeshg.retailstore.store.product.service.ProductService;
import org.rakeshg.retailstore.store.sales.dto.response.SaleResponse;
import org.rakeshg.retailstore.store.sales.dto.response.TodaySaleResponse;
import org.rakeshg.retailstore.store.sales.service.SaleService;
import org.rakeshg.retailstore.store.store.command.OnboardStoreCommand;
import org.rakeshg.retailstore.store.store.dto.response.DashboardSummaryResponse;
import org.rakeshg.retailstore.store.store.model.Store;
import org.rakeshg.retailstore.store.store.repository.StoreRepository;
import org.rakeshg.retailstore.store.store.service.StoreService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneId;
import java.util.List;


@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final ProductService productService;
    private final InventoryService inventoryService;
    private final SaleService saleService;

    @Override
    public Store createStore(OnboardStoreCommand command) {

        Store store = Store.builder()
                .name(command.getStoreName())
                .address(command.getAddress())
                .active(true)
                .build();

        // Save store into db
        storeRepository.save(store);
        return store;
    }

    @Override
    public DashboardSummaryResponse getSummary(Long storeId) {

        TodaySaleResponse todaySale = saleService.getTodaySales(storeId);
        TodaySaleResponse yesterdaySale = saleService.getYesterdaySales(storeId);
        int percentageChange = 0;

        if(yesterdaySale.getTotalAmount().compareTo(BigDecimal.ZERO) > 0) {
            percentageChange = todaySale.getTotalAmount().subtract(yesterdaySale.getTotalAmount())
                    .divide(yesterdaySale.getTotalAmount(), 0, RoundingMode.DOWN)
                    .intValue();
        }

        DashboardSummaryResponse.TodaySales todaySales = DashboardSummaryResponse.TodaySales.builder()
                .amount(todaySale.getTotalAmount())
                .transactionCount(todaySale.getTotalSales())
                .percentageChange(percentageChange)
                .build();

        int totalProducts = productService.getTotalActiveProducts(storeId);

        // retrieve inventory summary
        InventorySummaryResponse inventorySummary = inventoryService.getInventorySummary(storeId);

        DashboardSummaryResponse.Inventory inventory = DashboardSummaryResponse.Inventory.builder()
                .totalValue(inventorySummary.getTotalInventoryValue())
                .stockHealth(inventorySummary.getStockHealthPercentage())
                .lowStockItems(inventorySummary.getLowStockItems())
                .build();

        List<DashboardSummaryResponse.RecentSale> recentSales = saleService.getTodayTop3Sales(storeId)
                .stream()
                .map(this::toRecentSale)
                .toList();

        return DashboardSummaryResponse.builder()
                .todaySales(todaySales)
                .totalProducts(totalProducts)
                .inventory(inventory)
                .recentSales(recentSales)
                .build();
    }

    private DashboardSummaryResponse.RecentSale toRecentSale(SaleResponse saleResponse) {
        return DashboardSummaryResponse.RecentSale
                .builder()
                .id(saleResponse.getId())
                .totalAmount(saleResponse.getTotalAmount())
                .totalItems(saleResponse.getTotalItems())
                .time(saleResponse.getCreatedAt()
                        .atZone(ZoneId.of("Asia/Kolkata"))
                        .toInstant()
                        .toEpochMilli())
                .build();
    }
}
