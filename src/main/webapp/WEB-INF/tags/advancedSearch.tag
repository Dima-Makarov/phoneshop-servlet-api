<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="label" required="true" %>
<%@ attribute name="errors" required="true" type="java.util.Map" %>

${label}
<c:set var="error" value="${errors[name]}"/>
<label>
    <input name="${name}" value="${param[name]}"/>
</label>
<c:if test="${not empty error}">
    <div class="error">
            ${error}
    </div>
</c:if>
