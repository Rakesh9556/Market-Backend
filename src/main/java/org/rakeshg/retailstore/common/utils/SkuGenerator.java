package org.rakeshg.retailstore.common.utils;


import org.springframework.stereotype.Component;

@Component
public class SkuGenerator {

    public String generate(String barcode) {
        return "SKU-" + barcode.substring(Math.max(0, barcode.length() - 6));
    }
}
