
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

        <div class="col-md-7" style="margin-left:-15px">
            <div class="card bg-light">
                <article class="card-body">

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
                    <li>Персональная скидка: ${sessionScope.currentUser.discount}%</li>
                    <hr/>
                </ul>
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
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.activeOrderList}" var="it">
                        <fmt:formatDate value="${it.resFrom}" type="date" var="resFrom"/>
                        <fmt:formatDate value="${it.resTo}" type="date" var="resTo"/>

                    <tr>
                        <td>${it.orderID}</td>
                        <td><fmt:formatDate value="${it.resFrom}" type="date" dateStyle="short"/></td>
                        <td><fmt:formatDate value="${it.resTo}" type="date" dateStyle="short"/></td>
                        <td>${it.status}</td>
                        <td>
                            <form method="get" action="ControllerServlet">
                                <input type="hidden" name="command" value="change_order"/>
                                <input type="hidden" name="orderID" value="${it.orderID}"/>
                                <input type="hidden" name="roomID" value="${it.roomID}"/>
                                <input type="hidden" name="resFrom" value="${resFrom}"/>
                                <input type="hidden" name="resTo" value="${resTo}"/>

                                <input type="hidden" name="userID" value="${sessionScope.currentUser.userID}"/>
                                <button class="btn btn-info btn-sm" type="submit">
                                Изменить
                                </button>
                            </form>
                        </td>
                    </tr>

                    </c:forEach>
                    </tbody>
                </table>

                <form action="ControllerServlet" method="get">
                    <input type="hidden" name="command" value="profile"/>
                    <input type="hidden" name="paginatorType" value="active">
                    <ul class="pagination justify-content-center">
                        <c:if test="${sessionScope.activePaginator.startPos > '0'}">
                            <li class="page-item"><button class="page-link" type="submit" name="page" value="prev">Prev</button></li>
                        </c:if>
                        <c:if test="${sessionScope.activePaginator.lastPage!=true}">
                            <li class="page-item"><button class="page-link" type="submit" name="page" value="next">Next</button></li>
                        </c:if>

                    </ul>
                </form>



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
                    <c:forEach items="${requestScope.historyOrderList}" var="it">
                            <tr>
                                <td>${it.orderID}</td>
                                <td><fmt:formatDate value="${it.resFrom}" type="date" dateStyle="short"/></td>
                                <td><fmt:formatDate value="${it.resTo}" type="date" dateStyle="short"/></td>
                                <td>${it.status}</td>
                                <td>${it.comment}</td>
                            </tr>

                    </c:forEach>

                    </tbody>
                </table>

                <form action="ControllerServlet" method="get">
                    <input type="hidden" name="command" value="profile"/>
                    <input type="hidden" name="paginatorType" value="history">
                    <ul class="pagination justify-content-center">
                        <c:if test="${sessionScope.historyPaginator.startPos > '0'}">
                            <li class="page-item"><button class="page-link" type="submit" name="page" value="prev">Prev</button></li>
                        </c:if>
                        <c:if test="${sessionScope.historyPaginator.lastPage!=true}">
                            <li class="page-item"><button class="page-link" type="submit" name="page" value="next">Next</button></li>
                        </c:if>

                    </ul>
                </form>

            </div>
                </article>
            </div>
        </div>
    </div>
</div>
            <jsp:include page="/WEB-INF/jsp/page_component/Footer.jsp"/>
</body>
</html>
