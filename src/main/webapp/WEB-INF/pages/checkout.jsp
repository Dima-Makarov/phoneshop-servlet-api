<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"/>


<tags:master pageTitle="Checkout">
    <a href="${pageContext.servletContext.contextPath}/products">
        <-Back to products list
    </a>
    <div class="success">
            ${param.message}
    </div>
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
            <td>
                Quantity
            </td>
        </tr>
        </thead>
        <c:forEach var="entry" items="${order.items.entrySet()}" varStatus="status">
            <tr>
                <td>
                    <img class="product-tile"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${entry.key.imageUrl}">
                </td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/products/${entry.key.id}">
                            ${entry.key.description}
                    </a>
                </td>

                <td class="price">
                    <fmt:formatNumber value="${entry.key.price}" type="currency"
                                      currencySymbol="${entry.key.currency.symbol}"/>
                </td>
                <td>
                        ${entry.value}
                </td>

            </tr>
        </c:forEach>
        <tr>
            <td></td>
            <td>
                SubTotal
            </td>
            <td class="price">
                <fmt:formatNumber value="${order.subTotal}" type="currency"
                                  currencySymbol="USD"/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                Delivery cost
            </td>
            <td class="price">
                <fmt:formatNumber value="${order.deliveryCost}" type="currency"
                                  currencySymbol="USD"/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                Total cost
            </td>
            <td class="price">
                <fmt:formatNumber value="${order.totalCost}" type="currency"
                                  currencySymbol="USD"/>
            </td>
        </tr>
    </table>
    <div class="error">
        ${errors['order']}
    </div>
    <h2> Details </h2>
    <form method="post">
        <table style="border:1px solid black;margin-left:auto;margin-right:auto;">

            <tags:orderFormRow name="firstName" label="First Name" order="${order}" errors="${errors}"/>
            <tags:orderFormRow name="lastName" label="Last Name" order="${order}" errors="${errors}"/>
            <tags:orderFormRow name="phoneNumber" label="Phone Number" order="${order}" errors="${errors}"/>
            <tags:orderFormRow name="deliveryDate" label="Delivery Date" order="${order}" errors="${errors}"/>
            <tags:orderFormRow name="deliveryAddress" label="Delivery Address" order="${order}" errors="${errors}"/>

            <tr>
                <td>Payment method<span style="color:red">*</span></td>
                <td>
                    <select name="paymentMethod">
                        <c:forEach var="method" items="${paymentMethod}">
                            <option>
                                ${method}
                            </option>
                        </c:forEach>
                    </select>
                    <c:if test="${not empty errors['paymentMethod']}">
                        <div class="error">
                                ${errors['paymentMethod']}
                        </div>
                    </c:if>
                </td>
            </tr>
        </table>
        <button>Submit</button>
    </form>
</tags:master>
