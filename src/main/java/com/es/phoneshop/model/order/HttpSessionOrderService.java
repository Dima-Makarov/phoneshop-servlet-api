package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.HttpSessionCartService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HttpSessionOrderService implements OrderService {
    private static final String LOCK_SESSION_ATTRIBUTE = HttpSessionOrderService.class.getName() + ".lock";
    private static HttpSessionOrderService instance;
    private final OrderDao orderDao = ArrayListOrderDao.getInstance();

    public static synchronized HttpSessionOrderService getInstance() {
        if (instance == null) {
            instance = new HttpSessionOrderService();
        }
        return instance;
    }

    private HttpSessionOrderService() {
    }

    @Override
    public Order getOrder(Cart cart, HttpServletRequest request) throws EmptyCartException {
        Lock lock = (Lock) request.getSession().getAttribute(LOCK_SESSION_ATTRIBUTE);
        if (lock == null) {
            synchronized (request.getSession()) {
                lock = (Lock) request.getSession().getAttribute(LOCK_SESSION_ATTRIBUTE);
                if (lock == null) {
                    lock = new ReentrantLock();
                    request.getSession().setAttribute(LOCK_SESSION_ATTRIBUTE, lock);
                }
            }
        }
        lock.lock();
        try {
            if(cart.getItems().isEmpty()) {
                throw new EmptyCartException();
            }
            Order order = new Order();
            order.setItems(new LinkedHashMap<>(cart.getItems()));
            order.setSubTotal(cart.getTotalCost());
            order.setDeliveryCost(calculateDeliveryCost());
            order.setTotalCost(order.getDeliveryCost().add(order.getSubTotal()));
            return order;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void placeOrder(Order order, Cart cart, HttpServletRequest request) {
        Lock lock = (Lock) request.getSession().getAttribute(LOCK_SESSION_ATTRIBUTE);
        lock.lock();
        try {
            order.setSecureId(UUID.randomUUID().toString());
            order.setItems(new LinkedHashMap<>(cart.getItems()));
            order.setSubTotal(cart.getTotalCost());
            order.setDeliveryCost(calculateDeliveryCost());
            order.setTotalCost(order.getDeliveryCost().add(order.getSubTotal()));
            HttpSessionCartService.getInstance().clearCart(cart, request.getSession());
            orderDao.save(order);
        } finally {
            lock.unlock();
        }
    }

    private BigDecimal calculateDeliveryCost() {
        return new BigDecimal(42);
    }
}
