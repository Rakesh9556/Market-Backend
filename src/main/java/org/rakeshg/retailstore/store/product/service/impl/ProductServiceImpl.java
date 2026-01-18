package org.rakeshg.retailstore.store.product.service.impl;

import lombok.RequiredArgsConstructor;
import org.rakeshg.retailstore.master.request.service.MasterProductRequestService;
import org.rakeshg.retailstore.store.product.dto.request.CreateProductByBarcodeRequest;
import org.rakeshg.retailstore.store.product.model.Product;
import org.rakeshg.retailstore.store.product.repository.ProductRepository;
import org.rakeshg.retailstore.store.product.service.CategoryService;
import org.rakeshg.retailstore.store.product.service.ProductService;
import org.rakeshg.retailstore.inventory.validation.BarcodeLookupService;
import org.rakeshg.retailstore.master.product.model.MasterProduct;
import org.rakeshg.retailstore.master.product.service.MasterProductService;
import org.rakeshg.retailstore.common.utils.SkuGenerator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final MasterProductService masterProductService;
    private final CategoryService categoryService;
    private final MasterProductRequestService  masterProductRequestService;
    private final BarcodeLookupService barcodeLookupService;
    private final SkuGenerator skuGenerator;

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product scanByBarcode(String barcode, CreateProductByBarcodeRequest createProductRequest) {
        // case 1: product already exist in store db
        Optional<Product> storeProductOpt = productRepository.findByBarcode(barcode);
        if (storeProductOpt.isPresent()) {
            return storeProductOpt.get();
        }

        // case 2: product not exist in store chek master db
        Optional<MasterProduct> masterProductOpt = masterProductService.findByUINCode(barcode);

        if(masterProductOpt.isPresent()) {
            MasterProduct masterProduct = masterProductOpt.get();

            Product product = Product.builder()
                    .sku(masterProduct.getGlobalSku())
                    .name(masterProduct.getName())
                    .barcode(barcode)
                    .category(masterProduct.getCategory())
                    .price(masterProduct.getPrice())
                    .stock(0)
                    .active(true)
                    .build();

            return productRepository.save(product);
        }

        // case 3: Product not exist in store and not in master db
        Product newProduct = Product.builder()
                .sku(skuGenerator.generate(barcode))
                .name(createProductRequest.getName())
                .barcode(barcode)
                .category(categoryService.getById(createProductRequest.getCategoryId()))
                .price(createProductRequest.getPrice())
                .stock(createProductRequest.getStock())
                .active(true)
                .build();

        // save prouct in store
        Product savedProduct = productRepository.save(newProduct);

        // make a req to masterdb
        Long storeId = 0L;  // must be retrieved from context
        masterProductRequestService.createRequest(storeId, barcode,createProductRequest.getName(), createProductRequest.getCategoryId());

        return savedProduct;
    }

    @Override
    public Product update(Long id, Product updated) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setName(updated.getName());
        existing.setSku(updated.getSku());
        existing.setPrice(updated.getPrice());
        existing.setStock(updated.getStock());
        existing.setActive(updated.getActive());
        existing.setCategory(updated.getCategory());

        return productRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }
}
