package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.cart.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class OrderTests {
    private OrderDao orderDao;
    @Mock
    private OrderService orderService;
    @Mock
    private HttpSession httpSession;
    @Mock
    private HttpServletRequest request;

    @Before
    public void setup() {
        orderDao = ArrayListOrderDao.getInstance();
        orderService = HttpSessionOrderService.getInstance();
    }
    @Test
    public void testSaveGetOrder() throws OutOfStockException {
        Lock lock = new ReentrantLock();
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute(HttpSessionCartService.class.getName() + ".lock")).thenReturn(lock);
        when(httpSession.getAttribute(HttpSessionOrderService.class.getName() + ".lock")).thenReturn(lock);
        HttpSessionCartService cartService = HttpSessionCartService.getInstance();
        Cart cart = new Cart();
        ProductDao productDao = ArrayListProductDao.getInstance();
        productDao.clearAll();
        productDao.save(new Product("sgs", "Xiaomi", new BigDecimal(100), Currency.getInstance("USD"), 100, "https://just/a/shortened/link.jpg"));
        cartService.add(cart,0L,1,httpSession);
        Order order = orderService.getOrder(cart, request);
        orderService.placeOrder(order, cart, request);
        assertFalse(orderDao.getOrders().isEmpty());
        productDao.clearAll();
    }
}
