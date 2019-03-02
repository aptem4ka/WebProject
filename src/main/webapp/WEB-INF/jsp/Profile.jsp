
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/page_component/Navbar.jsp"/>

<div class="container">
    <div class="row">

        <jsp:include page="/WEB-INF/jsp/page_component/Menubar.jsp"/>

        <div class="col-md-7" style="margin-top: 15px ">
            <h3>Информация о пользователе</h3>
            <div style="font-size: 12pt">
                <c:set value="${sessionScope.currentUser.phone}" var="phone"/>
                <ul>
                    <li>Электронная почта: ${sessionScope.currentUser.email}</li>
                    <li>Имя: ${sessionScope.currentUser.name}</li>
                    <li>Фамилия: ${sessionScope.currentUser.surname}</li>
                    <li>Телефон: <c:if test="${empty phone}">Номер отсутствует</c:if>
                                  <c:if test="${not empty phone}">${phone}</c:if>
                    </li>
                    <li>Персональная скидка: 0%</li>
                    <hr/>
                </ul>

                <h4>История заказов</h4>

                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>Номер заказа</th>
                        <th>Бронь с</th>
                        <th>Бронь по</th>
                        <th>Статус</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.orderList}" var="it">
                    <tr>
                        <td>${it.orderID}</td>
                        <td><fmt:formatDate value="${it.resFrom}" type="date"/></td>
                        <td><fmt:formatDate value="${it.resTo}" type="date"/></td>
                        <td>${it.status}</td>
                    </tr>
                    </c:forEach>
                    </tbody>
                </table>

            </div>

        </div>

</body>
</html>
