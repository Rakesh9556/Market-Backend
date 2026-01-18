package org.rakeshg.retailstore.master.request.service;

public interface MasterProductRequestService {
    void createRequest(Long storeId, String barcode, String productName, Long categoryId);
}
