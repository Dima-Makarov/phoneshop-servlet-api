<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>

<tags:master pageTitle="Advanced Search">
    <div class="success">
            ${param.message}
    </div>
    <h>
        Advanced Search Page
    </h>
    <div class="error">
            ${errors['errors']}
    </div>
    <form>
        <p>
            <tags:advancedSearch name="productCode" label="Product Code" errors="${errors}"/>
        </p>
        <p>
            <tags:advancedSearch name="minPrice" label="Min price" errors="${errors}"/>
        </p>
        <p>
            <tags:advancedSearch name="maxPrice" label="Max price" errors="${errors}"/>
        </p>
        <p>
            <tags:advancedSearch name="minStock" label="Min stock" errors="${errors}"/>
        </p>
        <button>Search</button>
    </form>
    <c:if test="${empty errors}">
        <div class="success">
            Found ${products.size()} items
        </div>
    </c:if>
    <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
        <thead>
        <tr>
            <td>Image</td>
            <td>
                Description
            </td>
            <td class="price">
                Price
            </td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <img class="product-tile"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}"
                         alt="">
                </td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                            ${product.description}
                    </a>
                </td>
                <td class="price">
                    <fmt:formatNumber value="${product.price}" type="currency"
                                      currencySymbol="${product.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>
