package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.function.Predicate;

public class PriceStockPredicate implements Predicate<Product> {
    private final BigDecimal minPrice;
    private final BigDecimal maxPrice;
    private final Integer minStock;

    public PriceStockPredicate(BigDecimal minPrice, BigDecimal maxPrice, Integer minStock) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.minStock = minStock;
    }

    @Override
    public boolean test(Product product) {
        boolean minPriceCheck = minPrice == null;
        if (!minPriceCheck) {
            minPriceCheck = product.getPrice().compareTo(minPrice) >= 0;
        }
        boolean maxPriceCheck = maxPrice == null;
        if (!maxPriceCheck) {
            maxPriceCheck = product.getPrice().compareTo(maxPrice) <= 0;
        }
        boolean minStockCheck = minStock == null;
        if (!minStockCheck) {
            minStockCheck = product.getStock() >= minStock;
        }
        if(product.getStock() == 0) {
            minStockCheck = false;
        }
        return minPriceCheck && maxPriceCheck && minStockCheck;
    }
}
