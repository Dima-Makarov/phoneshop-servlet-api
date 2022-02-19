package com.es.phoneshop.web;

import com.es.phoneshop.model.order.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


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
            Order order = orderDao.getOrderBySecure(secureOrderId).get();
            request.setAttribute("order", order);
            request.getRequestDispatcher("/WEB-INF/pages/overview.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("incorrectId", "null");
            response.setStatus(404);
            request.getRequestDispatcher("/WEB-INF/pages/PageNotFound.jsp").forward(request, response);
        }
    }
}
