package org.rakeshg.retailstore.store.sales.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.rakeshg.retailstore.common.exception.BusinessException;
import org.rakeshg.retailstore.inventory.service.InventoryService;
import org.rakeshg.retailstore.store.product.UnitType;
import org.rakeshg.retailstore.store.product.model.Product;
import org.rakeshg.retailstore.store.product.service.ProductService;
import org.rakeshg.retailstore.store.sales.command.CreateSaleCommand;
import org.rakeshg.retailstore.store.sales.command.CreateSaleItemCommand;
import org.rakeshg.retailstore.store.sales.dto.response.SaleDetailResponse;
import org.rakeshg.retailstore.store.sales.dto.response.SaleItemResponse;
import org.rakeshg.retailstore.store.sales.dto.response.SaleResponse;
import org.rakeshg.retailstore.store.sales.dto.response.TodaySaleResponse;
import org.rakeshg.retailstore.store.sales.model.Sale;
import org.rakeshg.retailstore.store.sales.model.SaleItem;
import org.rakeshg.retailstore.store.sales.repository.SaleItemRepository;
import org.rakeshg.retailstore.store.sales.repository.SaleRepository;
import org.rakeshg.retailstore.store.sales.service.SaleService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final SaleItemRepository saleItemRepository;
    private final ProductService productService;
    private final InventoryService inventoryService;

    @Transactional
    @Override
    public SaleResponse createSale(Long storeId, CreateSaleCommand command) {

        if (command.getItems() == null || command.getItems().isEmpty()) {
            throw new BusinessException("Sale must contain at least one item", "EMPTY_SALE");
        }

        BigDecimal totalAmount = BigDecimal.ZERO;
        int totalItems = 0;

        List<SaleItem> saleItems = new ArrayList<>();

        // Create a sale object
        Sale sale = Sale.builder()
                .storeId(storeId)
                .createdAt(LocalDateTime.now())
                .build();

        saleRepository.save(sale);

        // process each sale item
        for(CreateSaleItemCommand itemCommand : command.getItems()) {

            // find product
            Product product = productService.findByIdAndStoreId(itemCommand.getProductId(), storeId)
                    .orElseThrow(() -> new BusinessException("Product not found", "PRODUCT_NOT_FOUND"));

            // Calculate product sale price
            BigDecimal productSalePrice = product.getPrice().multiply(itemCommand.getQuantity());

            // Create sale item object
            SaleItem saleItem = SaleItem.builder()
                    .saleId(sale.getId())
                    .productId(product.getId())
                    .productName(product.getName())
                    .price(product.getPrice())
                    .quantity(itemCommand.getQuantity())
                    .unit(UnitType.valueOf(product.getUnit().name()))
                    .salePrice(productSalePrice)
                    .build();

            // Add sale items to list
            saleItems.add(saleItem);

            // Update stock in inventory
            inventoryService.deductStock(storeId, product.getId(), itemCommand.getQuantity());

            // calculate total sale amount and total distinct items
            totalAmount = totalAmount.add(productSalePrice);
            totalItems++;
        }

        // Save all sale items in a batch
        saleItemRepository.saveAll(saleItems);

        // update total sale amount and item in sale entity
        sale.setTotalAmount(totalAmount);
        sale.setTotalItem(totalItems);

        // return SaleResponse
        return SaleResponse.builder()
                .id(sale.getId())
                .totalAmount(totalAmount)
                .totalItems(totalItems)
                .createdAt(sale.getCreatedAt())
                .build();
    }

    @Override
    public List<SaleResponse> getSales(Long storeId) {
        return saleRepository.findByStoreIdOrderByCreatedAtDesc(storeId)
                .stream()
                .map(this::toSaleResponse)
                .toList();
    }

    @Override
    public SaleDetailResponse getSaleById(Long storeId, Long saleId) {

        Sale sale = saleRepository.findByIdAndStoreId(saleId, storeId)
                .orElseThrow(() -> new RuntimeException("Sale not found"));

        List<SaleItem> items = saleItemRepository.findBySaleId(saleId);

        return SaleDetailResponse.builder()
                .id(sale.getId())
                .totalAmount(sale.getTotalAmount())
                .totalItems(sale.getTotalItem())
                .createdAt(sale.getCreatedAt())
                .items(
                        items.stream()
                                .map(this::toSaleItemResponse)
                                .toList()
                )
                .build();
    }

    @Override
    public TodaySaleResponse getTodaySales(Long storeId) {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        return getTodaySaleResponse(storeId, today);
    }

    @Override
    public TodaySaleResponse getYesterdaySales(Long storeId) {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata")).minusDays(1);
        return getTodaySaleResponse(storeId, today);
    }

    @Override
    public List<SaleResponse> getTodayTop3Sales(Long storeId) {
        return saleRepository.findTop3ByStoreIdOrderByCreatedAtDesc(storeId)
                .stream()
                .map(this:: toSaleResponse)
                .toList();
    }

    private TodaySaleResponse getTodaySaleResponse(Long storeId, LocalDate today) {
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = start.plusDays(1);

        BigDecimal totalAmount = saleRepository.getTodayTotalSales(storeId, start, end);
        Integer totalSales = saleRepository.countByStoreIdAndCreatedAtBetween(storeId, start, end);

        if(totalAmount == null) totalAmount = BigDecimal.ZERO;
        if(totalSales == null) totalSales = 0;

        return TodaySaleResponse.builder()
                .totalAmount(totalAmount)
                .totalSales(totalSales)
                .build();
    }

    private SaleResponse toSaleResponse(Sale sale) {
        return SaleResponse.builder()
                .id(sale.getId())
                .totalAmount(sale.getTotalAmount())
                .totalItems(sale.getTotalItem())
                .createdAt(sale.getCreatedAt())
                .build();
    }

    private SaleItemResponse toSaleItemResponse(SaleItem item) {
        return SaleItemResponse.builder()
                .id(item.getId())
                .name(item.getProductName())
                .quantity(item.getQuantity())
                .unit(item.getUnit().name())
                .price(item.getPrice())
                .build();
    }
}
