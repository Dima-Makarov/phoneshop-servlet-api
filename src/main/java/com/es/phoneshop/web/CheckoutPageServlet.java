package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.order.HttpSessionOrderService;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderService;
import com.es.phoneshop.model.order.PaymentMethod;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;


public class CheckoutPageServlet extends HttpServlet {
    private final CartService cartService;
    private final OrderService orderService;

    public CheckoutPageServlet() {
        cartService = HttpSessionCartService.getInstance();
        orderService = HttpSessionOrderService.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        Order order = orderService.getOrder(cart);
        Map<String, String> errors = new HashMap<>();
        setParameter(request, "firstName", errors, order::setFirstName);
        setParameter(request, "lastName", errors, order::setLastName);
        setParameter(request, "phoneNumber", errors, order::setPhoneNumber);
        setParameter(request, "deliveryAddress", errors, order::setDeliveryAddress);
//        setParameter(request, "deliveryDate", errors, order::setFirstName);
        setParameter(request, "paymentMethod", errors, s -> {
            try {
                order.setPaymentMethod(PaymentMethod.valueOf(s));
            } catch (IllegalArgumentException e) {
                errors.put("paymentMethod", "incorrect payment method");
            }
        });
        if (errors.isEmpty()) {
            orderService.placeOrder(order);
            cartService.ClearCart(cart, request.getSession());
            response.sendRedirect(request.getContextPath() + "/order/overview/" + order.getSecureId());
        } else {
            request.setAttribute("errors", errors);
            request.setAttribute("order", order);
            doGet(request, response);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getAttribute("order") == null) {
            request.setAttribute("order", orderService.getOrder(cartService.getCart(request)));
        }
        request.setAttribute("paymentMethod", orderService.getPaymentMethods());
        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
    }

    private void setParameter(HttpServletRequest request, String parameter, Map<String, String> errors, Consumer<String> consumer) {
        String value = request.getParameter(parameter);
        if (value == null || value.isEmpty()) {
            errors.put(parameter, parameter + " is required");
        } else {
            consumer.accept(value);
        }
    }
}
