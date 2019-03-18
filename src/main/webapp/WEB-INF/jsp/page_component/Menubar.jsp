<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
    <fmt:setBundle basename="locale" var="loc"/>
    <fmt:message bundle="${loc}" key="locale.menubar.about" var="about"/>
    <fmt:message bundle="${loc}" key="locale.menubar.main" var="main"/>
    <fmt:message bundle="${loc}" key="locale.menubar.rooms" var="rooms"/>
    <fmt:message bundle="${loc}" key="locale.menubar.reviews" var="reviews"/>



</head>
<body>

<div class="list-group col-md-3" style="margin-right:-15px; margin-left:-15px">

    <a href="${pageContext.request.contextPath}/main?command=index_page"
       class="list-group-item list-group-item-action list-group-item-light">${main}</a>
    <a href="${pageContext.request.contextPath}/main?command=room_list"
       class="list-group-item list-group-item-action list-group-item-light">${rooms}</a>
    <a href="${pageContext.request.contextPath}/main?command=reviews_page"
       class="list-group-item list-group-item-action list-group-item-light">${reviews}</a>
    <a href="${pageContext.request.contextPath}/main?command=about_page"
       class="list-group-item list-group-item-action list-group-item-light">${about}</a>
    <c:if test="${sessionScope.currentUser.role eq 'ADMIN'}">
        <a href="${pageContext.request.contextPath}/admin?command=review_moderation" class="list-group-item list-group-item-action list-group-item-light" style="color: coral">Управление отзывами <span class="badge badge-primary badge-pill">${sessionScope.reviewsWaiting}</span></a>
    </c:if>

</div>

</body>
</html>
