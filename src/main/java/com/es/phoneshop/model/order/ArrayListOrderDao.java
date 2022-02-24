package com.es.phoneshop.model.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ArrayListOrderDao implements OrderDao{
    private static OrderDao instance;

    public static synchronized OrderDao getInstance() {
        if (instance == null) {
            instance = new ArrayListOrderDao();
        }
        return instance;
    }

    private final List<Order> orders;

    private long maxId = 0;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private ArrayListOrderDao() {
        this.orders = new ArrayList<>();
    }

    @Override
    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public Optional<Order> getOrderBySecure(String secureId) {

        if (secureId == null) {
            return Optional.empty();
        }
        lock.readLock().lock();
        try {
            return orders.stream()
                    .filter(order -> secureId.equals(order.getSecureId()))
                    .findAny();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void save(Order order) {
        if (order == null) {
            return;
        }
        lock.writeLock().lock();
        try {
            if (order.getId() != null) {
                orders.forEach(order1 -> {
                    if (order1.getId().equals(order.getId())) {
                        orders.set(orders.indexOf(order1), order);
                    }
                });
            } else {
                order.setId(maxId++);
                orders.add(order);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }
}