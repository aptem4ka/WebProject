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

        <div class="col-md-7" style="margin-left: -15px ">
            <div class="card bg-light">
                <article class="card-body">


                            <h4>Забронированные номера</h4>
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th>Заказ</th>
                                    <th>ID юзера</th>
                                    <th>Номер</th>
                                    <th>Бронь с</th>
                                    <th>Бронь по</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${requestScope.activeOrderList}" var="it">
                                    <c:if test="${it.resFrom.time gt requestScope.currentDate.time}">
                                    <tr>
                                        <td>${it.orderID}</td>
                                        <td><c:if test="${it.userID=='0'}">Гость</c:if><c:if test="${it.userID!='0'}">${it.userID}</c:if></td>
                                        <td>${it.roomID}</td>
                                        <td><fmt:formatDate value="${it.resFrom}" type="date" dateStyle="short"/></td>
                                        <td><fmt:formatDate value="${it.resTo}" type="date" dateStyle="short"/></td>
                                        <td>
                                            <form action="${pageContext.request.contextPath}/ControllerServlet" method="post">
                                                <input type="hidden" name="command" value="update_order_status"/>
                                                <input type="hidden" name="orderID" value="${it.orderID}"/>
                                                <ul class="list-unstyled">

                                                <li class="dropdown  dropright"><button data-toggle="dropdown" class="btn btn-danger btn-sm">Cancel</button>

                                                    <div class="dropdown-menu" style="width: 300px">

                                                            <div class="form-group">
                                                                <label for="comment">Comment</label>
                                                                <input type="text" name="comment" class="form-control" id="comment" placeholder="Comment">
                                                            </div>
                                                            <button type="submit" class="btn btn-danger" name="update_type" value="cancelled">Cancel order</button>

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
                    <form action="ControllerServlet" method="get">
                        <input type="hidden" name="command" value="control"/>
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

                    <h4>Требуется подтверждение</h4>

                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>Заказ</th>
                            <th>ID юзера</th>
                            <th>Номер</th>
                            <th>Бронь с</th>
                            <th>Бронь по</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.needConfirmList}" var="it">

                            <c:if test="${(it.resFrom.time eq requestScope.currentDate.time || it.resFrom.time lt requestScope.currentDate.time) }">
                                <tr>
                                    <td>${it.orderID}</td>
                                    <td><c:if test="${it.userID=='0'}">Гость</c:if><c:if test="${it.userID!='0'}">${it.userID}</c:if></td>
                                    <td>${it.roomID}</td>
                                    <td><fmt:formatDate value="${it.resFrom}" type="date" dateStyle="short"/></td>
                                    <td><fmt:formatDate value="${it.resTo}" type="date" dateStyle="short"/></td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/ControllerServlet" method="post">
                                            <input type="hidden" name="command" value="update_order_status"/>
                                            <input type="hidden" name="orderID" value="${it.orderID}"/>
                                            <ul class="list-unstyled">

                                                <li>

                                                    <button type="submit" class="btn btn-success btn-sm" name="update_type" value="completed">Arrived</button>
                                                    <button data-toggle="dropdown" class="btn btn-danger btn-sm" >Cancel</button>
                                                    <div class="dropdown-menu" style="width: 300px">

                                                        <div class="form-group">
                                                            <label for="comment">Comment</label>
                                                            <input type="text" name="comment" class="form-control" id="comment1" placeholder="Comment">
                                                        </div>
                                                        <button type="submit" class="btn btn-danger" name="update_type" value="cancelled">Cancel order</button>

                                                    </div>
                                                </li>

                                                <li class="dropdown  dropright nav-item">



                                                </li>

                                            </ul>
                                        </form>
                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        </tbody>
                    </table>

                    <form action="ControllerServlet" method="get">
                        <input type="hidden" name="command" value="control"/>
                        <input type="hidden" name="paginatorType" value="needConfirm">
                        <ul class="pagination justify-content-center">
                            <c:if test="${sessionScope.needConfirmPaginator.startPos > '0'}">
                                <li class="page-item"><button class="page-link" type="submit" name="page" value="prev">Prev</button></li>
                            </c:if>
                            <c:if test="${sessionScope.needConfirmPaginator.lastPage!=true}">
                                <li class="page-item"><button class="page-link" type="submit" name="page" value="next">Next</button></li>
                            </c:if>

                        </ul>
                    </form>

                </article>
        </div>
        </div>
        <jsp:include page="/WEB-INF/jsp/page_component/AdminBar.jsp"/>
    </div>
</div>

            <jsp:include page="/WEB-INF/jsp/page_component/Footer.jsp"/>
</body>
</html>
