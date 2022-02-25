package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.order.EmptyCartException;
import com.es.phoneshop.model.order.HttpSessionOrderService;
import com.es.phoneshop.model.order.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class CheckoutServletTest {
    private final CheckoutPageServlet servlet = new CheckoutPageServlet();
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ServletConfig servletConfig;
    @Mock
    private HttpSession httpSession;

    @Before
    public void setup() throws ServletException {
        servlet.init(servletConfig);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }
    @Test
    public void testDoGet() throws ServletException, IOException {
        when(request.getAttribute(anyString())).thenReturn(new Order());
        servlet.doGet(request,response);
        verify(requestDispatcher).forward(request, response);
    }
    @Test
    public void testdoPost() throws ServletException, IOException {
        Cart cart = new Cart();
        when(httpSession.getAttribute(HttpSessionCartService.class.getName() + ".cart")).thenReturn(cart);
        Lock lock = new ReentrantLock();
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute(HttpSessionCartService.class.getName() + ".lock")).thenReturn(lock);
        when(httpSession.getAttribute(HttpSessionOrderService.class.getName() + ".lock")).thenReturn(lock);
        servlet.doPost(request,response);
    }
}
