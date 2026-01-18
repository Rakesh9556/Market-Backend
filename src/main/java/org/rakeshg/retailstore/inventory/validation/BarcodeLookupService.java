package org.rakeshg.retailstore.inventory.validation;

import org.rakeshg.retailstore.store.product.model.Product;

public interface BarcodeLookupService {
    Product lookup(String barcode);
}
