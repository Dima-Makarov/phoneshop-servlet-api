package com.es.phoneshop.web;

import com.es.phoneshop.model.product.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdvancedSearchServlet extends HttpServlet {
    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> errors = new HashMap<>();
        try {
            String productCodeString = request.getParameter("productCode");
            String minPriceString = request.getParameter("minPrice");
            String maxPriceString = request.getParameter("maxPrice");
            String minStockString = request.getParameter("minStock");
            BigDecimal minPrice = null;
            BigDecimal maxPrice = null;
            Integer minStock = null;
            try {
                if(!minPriceString.isEmpty()) {
                    minPrice = new BigDecimal(minPriceString);
                }
            } catch (NumberFormatException e) {
                errors.put("minPrice", "Incorrect min price");
            }
            try {
                if(!maxPriceString.isEmpty()) {
                    maxPrice = new BigDecimal(maxPriceString);
                }
            } catch (NumberFormatException e) {
                errors.put("maxPrice", "Incorrect max price");
            }
            try {
                if(!minStockString.isEmpty()) {
                    minStock = Integer.valueOf(minStockString);
                }
            } catch (NumberFormatException e) {
                errors.put("minStock", "Incorrect min stock");
            }
            if (errors.isEmpty()) {
                request.setAttribute("products", productDao.findProductAdvanced(productCodeString, minPrice, maxPrice, minStock));
            } else {
                request.setAttribute("products", null);
                request.setAttribute("errors", errors);
            }
            request.getRequestDispatcher("/WEB-INF/pages/advancedSearch.jsp").forward(request, response);
        } catch (Exception e) {
            errors.put("errors", "Something is wrong, please try again");
            request.setAttribute("errors", errors);
            request.setAttribute("products", new ArrayList<Product>());
            request.getRequestDispatcher("/WEB-INF/pages/advancedSearch.jsp").forward(request, response);
        }
    }
}
