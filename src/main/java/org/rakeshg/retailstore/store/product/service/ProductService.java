package org.rakeshg.retailstore.store.product.service;

import org.rakeshg.retailstore.store.product.dto.request.CreateProductByBarcodeRequest;
import org.rakeshg.retailstore.store.product.model.Product;

import java.util.List;

public interface ProductService {
    Product create(Product product);
    Product scanByBarcode(String barcode, CreateProductByBarcodeRequest createProductRequest);

    Product update(Long id, Product product);
    void delete(Long id);
    Product getById(Long id);
    List<Product> getAll();
}
