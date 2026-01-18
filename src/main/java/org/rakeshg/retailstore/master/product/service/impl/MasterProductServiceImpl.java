package org.rakeshg.retailstore.master.product.service.impl;

import lombok.RequiredArgsConstructor;
import org.rakeshg.retailstore.master.product.model.MasterProduct;
import org.rakeshg.retailstore.master.product.repository.MasterProductRepository;
import org.rakeshg.retailstore.master.product.service.MasterProductService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MasterProductServiceImpl implements MasterProductService {

    private final MasterProductRepository masterProductRepo;

    @Override
    public MasterProduct createMasterProduct(MasterProduct masterProduct) {
        return masterProductRepo.save(masterProduct);
    }

    @Override
    public boolean existByUINCode(String barcode) {
        return false;
    }

    @Override
    public Optional<MasterProduct> findByUINCode(String barcode) {
        return masterProductRepo.findByBarcode(barcode);
    }
}
