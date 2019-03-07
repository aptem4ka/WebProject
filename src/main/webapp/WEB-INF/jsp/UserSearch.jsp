<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>

<body>
<jsp:include page="/WEB-INF/jsp/page_component/Navbar.jsp"/>

<div class="container">
    <div class="row">

        <jsp:include page="/WEB-INF/jsp/page_component/Menubar.jsp"/>


        <div class="col-md-7" style="margin-left:-15px">
            <div class="card bg-light">
                <article class="card-body">

                    <h4>Информация о пользователе</h4>

                    <ul>
                        <li>ID Пользователя: <c:if test="${requestScope.user.userID != 0}">${requestScope.user.userID}</c:if><c:if test="${requestScope.user.userID==0}">Гость</c:if></li>
                        <li>Электронная почта: ${requestScope.user.email}</li>
                        <li>Имя: ${requestScope.user.name}</li>
                        <li>Фамилия: ${requestScope.user.surname}</li>
                        <li>Телефон: <c:if test="${empty requestScope.user.phone}">Номер отсутствует</c:if>
                            <c:if test="${not empty requestScope.user.phone}">${requestScope.user.phone}</c:if>
                        </li>
                        <li>Персональная скидка: ${requestScope.user.discount}% </li>
                        <hr/>
                    </ul>

                    <hr/>

                    <c:if test="${requestScope.user.userID != 0}">
                    <c:set value="CANCELLED" var="cancel"/>
                    <c:set value="APPLIED" var="apply"/>
                    <c:set value="COMPLETED" var="complete"/>
                    <h4>Активная бронь</h4>

                    <table class="table table-striped">

                        <thead>
                        <tr>
                            <th>Номер заказа</th>
                            <th>Бронь с</th>
                            <th>Бронь по</th>
                            <th>Статус</th>
                            <th>Комментарий</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.orderList}" var="it">
                            <c:if test="${requestScope.currentDate.time lt it.resFrom.time}">
                                <c:if test="${it.status ne cancel}">
                                    <tr>
                                        <td>${it.orderID}</td>
                                        <td><fmt:formatDate value="${it.resFrom}" type="date" dateStyle="short"/></td>
                                        <td><fmt:formatDate value="${it.resTo}" type="date" dateStyle="short"/></td>
                                        <td>${it.status}</td>
                                        <td>${it.comment}</td>
                                    </tr>
                                </c:if>
                            </c:if>
                        </c:forEach>
                        </tbody>
                    </table>
                    <hr/>
                    <h4>История бронирования</h4>

                    <table class="table table-striped">

                        <thead>
                        <tr>
                            <th>Номер заказа</th>
                            <th>Бронь с</th>
                            <th>Бронь по</th>
                            <th>Статус</th>
                            <th>Комментарий</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.orderList}" var="it">
                            <c:if test="${(requestScope.currentDate.time gt it.resFrom.time) || (it.status eq cancel)|| (it.status eq complete)}">
                                <tr>
                                    <td>${it.orderID}</td>
                                    <td><fmt:formatDate value="${it.resFrom}" type="date" dateStyle="short"/></td>
                                    <td><fmt:formatDate value="${it.resTo}" type="date" dateStyle="short"/></td>
                                    <td><c:if test="${it.status eq apply}">COMPLETED</c:if><c:if test="${it.status ne apply}">${it.status}</c:if></td>
                                    <td>${it.comment}</td>
                                </tr>
                            </c:if>

                        </c:forEach>

                        </tbody>
                    </table>
                    </c:if>





                </article>
            </div>

        </div>
        <jsp:include page="/WEB-INF/jsp/page_component/AdminBar.jsp"/>
    </div>
</div>
</body>
</html>