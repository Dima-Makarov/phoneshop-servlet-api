package com.es.phoneshop.model.order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    List<Order> getOrders();
    Optional<Order> getOrderBySecure(String secureId);
    void save(Order order);
}
