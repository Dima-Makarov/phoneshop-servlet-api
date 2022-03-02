package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Optional<Product> getProduct(Long id);
    List<Product> findProducts(String query, SortField sortFiels, SortType sortType);
    List<Product> findProductAdvanced(String productCode, BigDecimal minPrice, BigDecimal maxPrice, Integer stock);
    void save(Product product);
    void delete(Long id);
    void clearAll();
}
