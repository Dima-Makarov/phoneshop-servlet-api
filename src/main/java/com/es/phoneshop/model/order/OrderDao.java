package com.es.phoneshop.model.order;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.SortField;
import com.es.phoneshop.model.product.SortType;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    Optional<Order> getOrder(Long id);
    List<Order> getOrders();
    Optional<Order> getOrderBySecure(String secureId);
    void save(Order order);
}
