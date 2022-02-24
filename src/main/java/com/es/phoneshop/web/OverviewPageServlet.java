package com.es.phoneshop.web;

import com.es.phoneshop.model.order.ArrayListOrderDao;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


public class OverviewPageServlet extends HttpServlet {
    private final OrderDao orderDao;

    public OverviewPageServlet() {
        orderDao = ArrayListOrderDao.getInstance();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String secureOrderId = request.getPathInfo().substring(1);
            Optional<Order> orderOpt = orderDao.getOrderBySecure(secureOrderId);
            if(orderOpt.isPresent()) {
                request.setAttribute("order", orderOpt.get());
                request.getRequestDispatcher("/WEB-INF/pages/overview.jsp").forward(request, response);
            } else {
                handleError(request, response);
            }
        } catch (Exception e) {
            handleError(request, response);
        }
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("incorrectId", "null");
        response.setStatus(404);
        request.getRequestDispatcher("/WEB-INF/pages/PageNotFound.jsp").forward(request, response);
    }
}
