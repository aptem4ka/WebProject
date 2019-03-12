
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>

    <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
    <fmt:setBundle basename="locale" var="loc"/>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/page_component/Navbar.jsp"/>

<div class="container">
    <div class="row">

        <jsp:include page="/WEB-INF/jsp/page_component/Menubar.jsp"/>

        <div class="col-md-7" style="margin-left:-15px">
            <div class="card bg-light">
                <article class="card-body">
                    <h4>
                        Текущие параметры бронирования
                    </h4>
                    <fmt:formatDate value="${requestScope.resFrom}" type="date"  var="resFrom" />
                    <fmt:formatDate value="${requestScope.resTo}" type="date" var="resTo"/>
                    Номер заказа: ${sessionScope.orderID}<br/>
                    Тип номера: ${requestScope.room.type}<br/>
                    Принцип размещения: ${requestScope.room.allocation}<br/>
                    Этаж:${requestScope.room.floor}<br/>
                    Вид из окна:${requestScope.room.windowView}<br/>
                    Период пребывания: ${resFrom} - ${resTo}

                    <div align="center">
                        <h4>Новые параметры бронирования</h4>

                        <form action="${pageContext.request.contextPath}/ControllerServlet" method="get">
                            <input type="hidden" name="command" value="change_order_result"/>

                            <div style="width: 300px" align="center">
                                Дата заселения:
                                <input name="resFrom" class="form-control" type="date" required/>
                                <hr/>
                            </div>

                            <div style="width: 300px" align="center">
                                Дата выселения:
                                <input name="resTo" class="form-control" type="date" required/>
                                <hr/>
                            </div>
                            <c:if test="${param.incorrectDate == true}">
                                Вы ввели некорректные даты
                            </c:if>
                            <div style="max-width: 500px; text-align: left">
                                <div>
                                    <div style="text-align: center">Принцип размещения:</div>
                                    <c:forEach items="${requestScope.allocations}" var="it">
                                        <fmt:message bundle="${loc}" key="locale.room.${fn:toLowerCase(it)}" var="allocationDesc"/>
                                        <input type="radio" id="${it}" name="allocation" value="${it}" checked/> ${allocationDesc}<br/>
                                    </c:forEach>

                                    <hr/>
                                </div>

                                <div>
                                    Дополнительные спальные места для детей
                                    <select name="children">
                                        <option value="0">0</option>
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                    </select>
                                    <hr/>
                                </div>

                            </div>
                            <div>
                                <button type="submit" class="btn btn-info">Найти номер</button>
                            </div>


                        </form>
                    </div>






                </article>
            </div>
        </div>
    </div>
</div>