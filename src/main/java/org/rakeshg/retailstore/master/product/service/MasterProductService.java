package org.rakeshg.retailstore.master.product.service;

import org.rakeshg.retailstore.master.product.model.MasterProduct;

import java.util.Optional;

public interface MasterProductService {
    Optional<MasterProduct> findByUINCode(String barcode);
    MasterProduct createMasterProduct(MasterProduct masterProduct);
    boolean existByUINCode(String barcode);


}
