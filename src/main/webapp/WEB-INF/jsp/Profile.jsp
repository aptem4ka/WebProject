
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="locale.user.info" var="user_info"/>
<fmt:message bundle="${loc}" key="locale.user.user_email" var="user_email"/>
<fmt:message bundle="${loc}" key="locale.user.user_name" var="user_name"/>
<fmt:message bundle="${loc}" key="locale.user.user_surname" var="user_surname"/>
<fmt:message bundle="${loc}" key="locale.user.user_phone" var="user_phone"/>
<fmt:message bundle="${loc}" key="locale.user.no_phone" var="no_phone"/>
<fmt:message bundle="${loc}" key="locale.user.user_discount" var="user_discount"/>
<fmt:message bundle="${loc}" key="locale.order.order_number" var="order_number"/>
<fmt:message bundle="${loc}" key="locale.order.reserved_from" var="reserved_from"/>
<fmt:message bundle="${loc}" key="locale.order.reserved_to" var="reserved_to"/>
<fmt:message bundle="${loc}" key="locale.order.status" var="order_status"/>
<fmt:message bundle="${loc}" key="locale.user.active_booking" var="active_booking"/>
<fmt:message bundle="${loc}" key="locale.user.booking_history" var="booking_istory"/>
<fmt:message bundle="${loc}" key="locale.order.change" var="change"/>
<fmt:message bundle="${loc}" key="locale.order.comment" var="comment"/>

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

            <h3>${user_info}</h3>
            <div style="font-size: 12pt">
                <c:set value="${sessionScope.currentUser.phone}" var="phone"/>
                <ul>
                    <li><b>${user_email}:</b> ${sessionScope.currentUser.email}</li>
                    <li><b>${user_name}:</b> ${sessionScope.currentUser.name}</li>
                    <li><b>${user_surname}:</b> ${sessionScope.currentUser.surname}</li>
                    <li><b>${user_phone}:</b> <c:if test="${empty phone}">${no_phone}</c:if>
                                  <c:if test="${not empty phone}">${phone}</c:if>
                    </li>
                    <li><b>${user_discount}</b> ${sessionScope.currentUser.discount}%</li>

                </ul>
                <hr/>
                <c:set value="CANCELLED" var="cancel"/>
                <c:set value="APPLIED" var="apply"/>
                <c:set value="COMPLETED" var="complete"/>
                <h4>${active_booking}</h4>

                <table class="table table-striped">

                    <thead>
                    <tr>
                        <th>${order_number}</th>
                        <th>${reserved_from}</th>
                        <th>${reserved_to}</th>
                        <th>${order_status}</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.activeOrderList}" var="it">
                        <fmt:formatDate value="${it.resFrom}" type="date" dateStyle="short" var="resFrom"/>
                        <fmt:formatDate value="${it.resTo}" type="date" dateStyle="short" var="resTo"/>

                    <tr>
                        <td>${it.orderID}</td>
                        <td>${resFrom}</td>
                        <td>${resTo}</td>
                        <td><fmt:message bundle="${loc}" key="locale.order.status.${fn:toLowerCase(it.status)}"/></td>
                        <td>
                            <form method="get" action="ControllerServlet">
                                <input type="hidden" name="command" value="change_order"/>
                                <input type="hidden" name="orderID" value="${it.orderID}"/>
                                <input type="hidden" name="roomID" value="${it.roomID}"/>
                                <input type="hidden" name="resFrom" value="${resFrom}"/>
                                <input type="hidden" name="resTo" value="${resTo}"/>

                                <input type="hidden" name="userID" value="${sessionScope.currentUser.userID}"/>
                                <button class="btn btn-info btn-sm" type="submit">
                                ${change}
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
                <h4>${booking_istory}</h4>

                <table class="table table-striped">

                    <thead>
                    <tr>
                        <th>${order_number}</th>
                        <th>${reserved_from}</th>
                        <th>${reserved_to}</th>
                        <th>${order_status}</th>
                        <th>${comment}</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.historyOrderList}" var="it">
                            <tr>
                                <td>${it.orderID}</td>
                                <td><fmt:formatDate value="${it.resFrom}" type="date" dateStyle="short"/></td>
                                <td><fmt:formatDate value="${it.resTo}" type="date" dateStyle="short"/></td>
                                <td><fmt:message bundle="${loc}" key="locale.order.status.${fn:toLowerCase(it.status)}"/></td>
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
