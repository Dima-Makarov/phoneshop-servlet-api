package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public interface OrderService {
    Order getOrder(Cart cart, HttpServletRequest request);

    static List<PaymentMethod> getPaymentMethods() {
        return Arrays.asList(PaymentMethod.values());
    }

    void placeOrder(Order order, Cart cart, HttpServletRequest request);
}
