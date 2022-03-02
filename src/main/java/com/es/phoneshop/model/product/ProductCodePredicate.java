package com.es.phoneshop.model.product;

import java.util.function.Predicate;

public class ProductCodePredicate implements Predicate<Product> {
    private final String productCode;

    public ProductCodePredicate(String productCode) {
        this.productCode = productCode;
    }

    @Override
    public boolean test(Product product) {
        if(productCode.isEmpty()) {
            return true;
        } else {
            return product.getCode().equals(productCode);
        }
    }
}
