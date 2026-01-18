package org.rakeshg.retailstore.inventory.validation;

import org.rakeshg.retailstore.store.product.model.Product;
import org.springframework.stereotype.Service;

@Service
public class BarcodeLookupServiceImpl  implements BarcodeLookupService {
    @Override
    public Product lookup(String barcode) {
        return null;
    }
}
