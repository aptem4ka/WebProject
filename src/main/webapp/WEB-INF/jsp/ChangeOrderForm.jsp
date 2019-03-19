
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="locale.order.current_booking_parameters" var="current_booking_parameters"/>
<fmt:message bundle="${loc}" key="locale.room.result.type" var="room_type"/>
<fmt:message bundle="${loc}" key="locale.order.order_number" var="order_number"/>
<fmt:message bundle="${loc}" key="locale.room.result.allocation" var="room_allocation"/>
<fmt:message bundle="${loc}" key="locale.room.result.floor" var="room_floor"/>
<fmt:message bundle="${loc}" key="locale.room.result.view" var="room_view"/>
<fmt:message bundle="${loc}" key="locale.room.result.period" var="period_of_stay"/>
<fmt:message bundle="${loc}" key="locale.order.new_booking_parameters" var="new_booking_parameters"/>
<fmt:message bundle="${loc}" key="locale.order.reserved_from" var="reserved_from"/>
<fmt:message bundle="${loc}" key="locale.order.reserved_to" var="reserved_to"/>
<fmt:message bundle="${loc}" key="locale.order.incorrect_dates" var="incorrect_dates"/>
<fmt:message bundle="${loc}" key="locale.room.result.baby" var="baby_cots"/>
<fmt:message bundle="${loc}" key="locale.room.find_room" var="find_room"/>

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
                    <h4>
                        ${current_booking_parameters}
                    </h4>
                    <fmt:formatDate value="${requestScope.resFrom}" type="date"  var="resFrom" />
                    <fmt:formatDate value="${requestScope.resTo}" type="date" var="resTo"/>
                    <b>${order_number}</b> ${sessionScope.orderID}<br/>
                    <b>${room_type}</b> <fmt:message bundle="${loc}" key="locale.room.${fn:toLowerCase(requestScope.room.type)}"/> <br/>
                    <b>${room_allocation}</b> <fmt:message bundle="${loc}" key="locale.room.allocation.${fn:toLowerCase(requestScope.room.allocation)}"/><br/>
                    <b>${room_floor}</b> ${requestScope.room.floor}<br/>
                    <b>${room_view}</b> <fmt:message bundle="${loc}" key="locale.room.view.${fn:toLowerCase(requestScope.room.windowView)}"/><br/>
                    <b>${period_of_stay}</b> ${resFrom} - ${resTo}
                    <hr/>
                    <div align="center">
                        <h4>${new_booking_parameters}</h4>

                        <form action="${pageContext.request.contextPath}/user" method="get">
                            <input type="hidden" name="command" value="change_order_result"/>

                            <div style="width: 300px" align="center">
                                ${reserved_from}
                                <input name="resFrom" class="form-control" type="date" required/>
                                <hr/>
                            </div>

                            <div style="width: 300px" align="center">
                                ${reserved_to}
                                <input name="resTo" class="form-control" type="date" required/>
                                <hr/>
                            </div>
                            <c:if test="${param.incorrectDate == true}">
                                ${incorrect_dates}
                            </c:if>
                            <div style="max-width: 500px; text-align: left">
                                <div>
                                    <div style="text-align: center">${room_allocation}</div>
                                    <c:forEach items="${requestScope.allocations}" var="it">
                                        <fmt:message bundle="${loc}" key="locale.room.allocation.${fn:toLowerCase(it)}" var="allocationDesc"/>
                                        <input type="radio" id="${it}" name="allocation" value="${it}" checked/> ${allocationDesc}<br/>
                                    </c:forEach>

                                    <hr/>
                                </div>

                                <div>
                                    ${baby_cots}
                                    <select name="children">
                                        <option value="0">0</option>
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                    </select>
                                    <hr/>
                                </div>

                            </div>
                            <div>
                                <button type="submit" class="btn btn-info">${find_room}</button>
                            </div>


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