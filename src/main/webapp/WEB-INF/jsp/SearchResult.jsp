
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>

    <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
    <fmt:setBundle basename="resources.locale" var="loc"/>
    <fmt:message bundle="${loc}" key="locale.room.result.sorry" var="sorry"/>
    <fmt:message bundle="${loc}" key="locale.room.result.fail" var="search_fail"/>
    <fmt:message bundle="${loc}" key="locale.room.result.authorize" var="authorize"/>
    <fmt:message bundle="${loc}" key="locale.room.result.registered" var="registered"/>
    <fmt:message bundle="${loc}" key="locale.room.result.data" var="booking_data"/>
    <fmt:message bundle="${loc}" key="locale.room.result.period" var="period_of_stay"/>
    <fmt:message bundle="${loc}" key="locale.room.result.allocation" var="allocation_type"/>
    <fmt:message bundle="${loc}" key="locale.room.result.baby" var="baby_cots"/>
    <fmt:message bundle="${loc}" key="locale.room.${fn:toLowerCase(requestScope.allocation)}" var="allocation"/>
    <fmt:message bundle="${loc}" key="locale.room.result.type" var="room_type"/>
    <fmt:message bundle="${loc}" key="locale.room.result.day_price" var="day_price"/>
    <fmt:message bundle="${loc}" key="locale.room.result.period_price" var="period_price"/>
    <fmt:message bundle="${loc}" key="locale.room.result.book" var="book"/>



</head>
<body>
<jsp:include page="/WEB-INF/jsp/page_component/Navbar.jsp"/>

<div class="container">
    <div class="row">

        <jsp:include page="/WEB-INF/jsp/page_component/Menubar.jsp"/>

        <div class="col-md-7" style="margin: 15px ">

                <c:if test="${empty requestScope.roomList}">
                    <h3>${sorry}</h3>
                    ${search_fail}
                </c:if>
                <c:if test="${not empty requestScope.roomList}">

                <div >
                    <div>
                        <fmt:formatDate value="${requestScope.resFrom}" type="date"  var="resFrom" />
                        <fmt:formatDate value="${requestScope.resTo}" type="date" var="resTo"/>
                        <form action="ControllerServlet" method="post">
                            <c:if test="${sessionScope.currentUser == null}">
                                <hr/>
                                ${authorize}<br/>
                                ${registered}
                                <div class="form-group input-group">
                                    <input name="name" class="form-control" placeholder="Name (ex. Tim)" type="text" required>
                                    <input name="surname" class="form-control" placeholder="Surname (ex. Cook)" type="text" required>
                                </div>
                                <div class="form-group input-group">
                                    <input name="phone" class="form-control" placeholder="Phone (ex. +375291234567)" type="text" required>
                                </div>
                                <hr/>
                            </c:if>

                        <h2>${booking_data}</h2>
                        <h5>${period_of_stay} ${resFrom} - ${resTo}<br/>

                            ${allocation_type}  ${allocation}<br/>
                            ${baby_cots} ${requestScope.children}<br/>

                        </h5>

                        <hr/>
                        <c:forEach items="${requestScope.roomList}" var="it">
                            <fmt:message bundle="${loc}" key="locale.room.${fn:toLowerCase(it.type)}" var="type"/>

                                <input type="hidden" name="command" value="book"/>
                                ${room_type} ${type}<br/>
                                ${day_price} ${it.price}<br/>
                                <c:set value="${it.price}" var="price"/>
                                <c:set value="${requestScope.days}" var="days"/>
                                <h5>${period_price} ${price*days}</h5>

                                <input type="hidden" name="roomID" value="${it.roomID}"/>
                                <input type="hidden"  name="resFrom" value="${resFrom}"/>
                                <input type="hidden" name="resTo" value="${resTo}"/>

                                <button type="submit" class="btn btn-info">${book}</button>


                        <hr/>
                        </c:forEach>
                        </form>
                    </div>

                    </c:if>
                </div>





        </div>
    </div>
</div>
</body>
</html>
