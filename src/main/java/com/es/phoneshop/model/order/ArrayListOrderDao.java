package com.es.phoneshop.model.order;

import com.es.phoneshop.model.product.*;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ArrayListOrderDao implements OrderDao {
    private static OrderDao instance;

    public static synchronized OrderDao getInstance() {
        if (instance == null) {
            instance = new ArrayListOrderDao();
        }
        return instance;
    }

    private final List<Order> orders;

    private long maxId = 0;

    ReadWriteLock lock = new ReentrantReadWriteLock();

    private ArrayListOrderDao() {
        this.orders = new ArrayList<>();
    }

    @Override
    public Optional<Order> getOrder(Long id) throws NoSuchElementException {
        if (id == null) {
            return Optional.empty();
        }
        lock.readLock().lock();
        try {
            return orders.stream()
                    .filter(order -> id.equals(order.getId()))
                    .findAny();
        } finally {
            lock.readLock().unlock();
        }
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
                orders.forEach(product1 -> {
                    if (product1.getId().equals(order.getId())) {
                        orders.set(orders.indexOf(product1), order);
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