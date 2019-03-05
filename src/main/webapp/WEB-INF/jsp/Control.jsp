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

                <ul>

                    <li>
                        <button class="btn btn-info btn-block" type="button" data-toggle="collapse" data-target="#unprocessed_orders" aria-expanded="false" aria-controls="Collapse">
                            Необработанные заказы
                        </button>


                        <div id="unprocessed_orders" class="collapse">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th>ID заказа</th>
                                    <th>ID юзера</th>
                                    <th>ID комнаты</th>
                                    <th>Бронь с</th>
                                    <th>Бронь по</th>
                                    <th>Статус</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${requestScope.orderList}" var="it">
                                  <c:set value="PROCESSING" var="unprocessed_status"/>
                                    <c:if test="${it.status eq 'PROCESSING'}">
                                    <tr>
                                        <td>${it.orderID}</td>
                                        <td>${it.userID}</td>
                                        <td>${it.roomID}</td>
                                        <td><fmt:formatDate value="${it.resFrom}" type="date"/></td>
                                        <td><fmt:formatDate value="${it.resTo}" type="date"/></td>
                                        <td>${it.status}</td>
                                        <td>
                                            <form action="${pageContext.request.contextPath}/ControllerServlet" method="post">
                                                <input type="hidden" name="command" value="update_order_status"/>
                                                <input type="hidden" name="orderID" value="${it.orderID}"/>
                                                <ul>
                                                <li><button type="submit" name="update_type" value="applied">Apply</button></li>
                                                <li class="dropdown  dropright"><button data-toggle="dropdown" >Cancel</button>

                                                    <div class="dropdown-menu" style="width: 300px">

                                                            <div class="form-group">
                                                                <label for="comment"> Comment</label>
                                                                <input type="text" name="comment" class="form-control" id="comment" placeholder="Comment">
                                                            </div>
                                                            <button type="submit" class="btn btn-success" name="update_type" value="cancelled">Cancel order</button>

                                                    </div>

                                                </li>
                                                </ul>
                                            </form>
                                            </td>
                                    </tr>
                                    </c:if>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>


                    </li>
                    <li>
                        <button class="btn btn-info btn-block" type="button" data-toggle="collapse" data-target="#processed_orders" aria-expanded="false" aria-controls="Collapse">
                            Обработанные заказы
                        </button>


                        <div id="processed_orders" class="collapse">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th>ID заказа</th>
                                    <th>ID юзера</th>
                                    <th>ID комнаты</th>
                                    <th>Бронь с</th>
                                    <th>Бронь по</th>
                                    <th>Статус брони</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${requestScope.orderList}" var="it">
                                    <c:set value="PROCESSING" var="unprocessed_status"/>
                                    <c:if test="${it.status ne unprocessed_status}">
                                        <tr>
                                            <td>${it.orderID}</td>
                                            <td>${it.userID}</td>
                                            <td>${it.roomID}</td>
                                            <td><fmt:formatDate value="${it.resFrom}" type="date"/></td>
                                            <td><fmt:formatDate value="${it.resTo}" type="date"/></td>
                                            <td>${it.status}</td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>


                    </li>
                    <li>
                        <button class="btn btn-info btn-block" type="button" data-toggle="collapse" data-target="#registered_users" aria-expanded="false" aria-controls="Collapse">
                            Зарегистрированные пользователи
                        </button>
                    </li>
                    <li>
                        <button class="btn btn-info btn-block" type="button" data-toggle="collapse" data-target="#unregistered_users" aria-expanded="false" aria-controls="Collapse">
                            Незарегистрированные пользователи
                        </button>
                    </li>

                </ul>






        </div>

            <jsp:include page="/WEB-INF/jsp/page_component/Footer.jsp"/>
</body>
</html>
