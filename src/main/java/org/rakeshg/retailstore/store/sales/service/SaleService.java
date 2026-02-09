package org.rakeshg.retailstore.store.sales.service;

import org.rakeshg.retailstore.store.sales.command.CreateSaleCommand;
import org.rakeshg.retailstore.store.sales.dto.response.SaleDetailResponse;
import org.rakeshg.retailstore.store.sales.dto.response.SaleResponse;
import org.rakeshg.retailstore.store.sales.dto.response.TodaySaleResponse;

import java.util.List;

public interface SaleService {
    SaleResponse createSale(Long storeId, CreateSaleCommand command);
    List<SaleResponse> getSales(Long storeId);
    SaleDetailResponse getSaleById(Long storeId, Long saleId);
    TodaySaleResponse getTodaySales(Long storeId);
    TodaySaleResponse getYesterdaySales(Long storeId);
    List<SaleResponse> getTodayTop3Sales(Long storeId);
}
