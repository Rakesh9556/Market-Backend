package org.rakeshg.retailstore.master.request.service;


import org.rakeshg.retailstore.master.enums.MasterProductReqStatus;
import org.rakeshg.retailstore.master.request.model.MasterProductRequest;
import org.rakeshg.retailstore.master.request.repository.MasterProductRequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MasterProductRequestServiceImpl implements MasterProductRequestService {
    MasterProductRequestRepository repository;

    @Override
    public void createRequest(Long storeId, String barcode, String productName, Long categoryId) {
        MasterProductRequest request = MasterProductRequest.builder()
                .barcode(barcode)
                .suggestedName(productName)
                .suggestedCategoryId(categoryId)
                .storeId(storeId)
                .status(MasterProductReqStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(request);
    }
}
