package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart implements Serializable {
    static final long serialVersionUID = 2L;
    private Map<Product, Integer> items;

    private int totalQuantity = 0;

    private BigDecimal totalCost;

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Cart() {
        this.items = Collections.synchronizedMap(new LinkedHashMap<>());
        totalCost = new BigDecimal(0);
    }

    public Map<Product, Integer> getItems() {
        return items;
    }

    int get(Product p) {
        Integer amount = items.get(p);
        return amount == null ? 0 : amount;
    }

    public void setItems(Map<Product, Integer> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cart: [");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            stringBuilder.append(entry.getKey().getCode());
            stringBuilder.append(": ");
            stringBuilder.append(entry.getValue());
            stringBuilder.append(", ");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
