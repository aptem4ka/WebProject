<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<body>


<jsp:include page="/WEB-INF/jsp/page_component/Navbar.jsp"/>

<div class="container">
    <div class="row">

        <jsp:include page="/WEB-INF/jsp/page_component/Menubar.jsp"/>


        <div class="col-md-7">
            <h3 style="text-align: center">Поздравляем!</h3>
            <br/>
            <p style="text-align: center">Ваша бронь принята на обработку.<br/>
                <c:if test="${sessionScope.currentUser==null}">
                "Незарегистрированные пользователи могут проверить статус заказа можно <a href="#">здесь</a>.</p>
                </c:if>
            <div>
                <ul>
                    <li>Номер заказа: ${sessionScope.order.orderID}</li>
                    <li>Прибытие в отель: <fmt:formatDate value="${sessionScope.order.resFrom}" type="date"/></li>
                    <li>Отбытие из отеля: <fmt:formatDate value="${sessionScope.order.resTo}" type="date"/></li>
                </ul>

            </div>


        </div>