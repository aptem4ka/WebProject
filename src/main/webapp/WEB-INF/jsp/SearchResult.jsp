
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">
<head>

    <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
    <fmt:setBundle basename="locale" var="loc"/>
    <fmt:message bundle="${loc}" key="locale.room.result.sorry" var="sorry"/>
    <fmt:message bundle="${loc}" key="locale.room.result.fail" var="search_fail"/>
    <fmt:message bundle="${loc}" key="locale.room.result.authorize" var="authorize"/>
    <fmt:message bundle="${loc}" key="locale.room.result.registered" var="registered"/>
    <fmt:message bundle="${loc}" key="locale.room.result.data" var="booking_data"/>
    <fmt:message bundle="${loc}" key="locale.room.result.period" var="period_of_stay"/>
    <fmt:message bundle="${loc}" key="locale.room.result.allocation" var="allocation_type"/>
    <fmt:message bundle="${loc}" key="locale.room.result.baby" var="baby_cots"/>
    <fmt:message bundle="${loc}" key="locale.room.${fn:toLowerCase(sessionScope.allocation)}" var="allocation"/>
    <fmt:message bundle="${loc}" key="locale.room.result.type" var="room_type"/>
    <fmt:message bundle="${loc}" key="locale.room.result.day_price" var="day_price"/>
    <fmt:message bundle="${loc}" key="locale.room.result.period_price" var="period_price"/>
    <fmt:message bundle="${loc}" key="locale.room.result.book" var="book"/>
    <fmt:message bundle="${loc}" key="locale.room.result.view" var="view"/>
    <fmt:message bundle="${loc}" key="locale.room.result.floor" var="floor"/>



</head>
<body>
<jsp:include page="/WEB-INF/jsp/page_component/Navbar.jsp"/>

<div class="container">
    <div class="row">

        <jsp:include page="/WEB-INF/jsp/page_component/Menubar.jsp"/>

        <div class="col-md-7" style="margin-left:-15px ">
            <div class="card bg-light">
                <article class="card-body">

                <c:if test="${empty sessionScope.roomList}">
                    <h3>${sorry}</h3>
                    ${search_fail}
                </c:if>
                <c:if test="${not empty sessionScope.roomList}">

                <div >
                    <div>
                        <fmt:formatDate value="${sessionScope.resFrom}" type="date"  var="resFrom" />
                        <fmt:formatDate value="${sessionScope.resTo}" type="date" var="resTo"/>
                        <form action="ControllerServlet" method="post">
                            <c:if test="${sessionScope.currentUser == null}">

                                <hr/>
                                ${authorize} ${registered}
                            <div align="center">
                                <div class="form-group input-group" style="width: 300px">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                                    </div>
                                    <input name="name" class="form-control" placeholder="Name (ex. Tim)" type="text" required>
                                </div> <!-- form-group// -->
                                <div class="form-group input-group" style="width: 300px">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                                    </div>
                                    <input name="surname" class="form-control" placeholder="Surname (ex. Cook)" type="text" required>
                                </div>
                                <!-- form-group// -->
                                <div class="form-group input-group" style="width: 300px">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text"> <i class="fa fa-phone"></i> </span>
                                    </div>
                                    <input name="phone" class="form-control" placeholder="Phone (ex. +375291234567)" type="text" required>
                                </div>
                            </div>
                                <c:if test="${param.incorrectData eq true}"><span style="color: red">Пожалуйста, введите корректные данные в соответствии с шаблоном</span></c:if>

                                <hr/>
                            </c:if>

                        <h2>${booking_data}</h2>
                        <h5>${period_of_stay} ${resFrom} - ${resTo}<br/>

                            ${allocation_type}  ${allocation}<br/>
                            ${baby_cots} ${sessionScope.children}<br/>

                        </h5>

                        <hr/>
                            <input type="hidden" name="command" value="book"/>
                        <c:forEach items="${sessionScope.roomList}" var="it">
                            <fmt:message bundle="${loc}" key="locale.room.${fn:toLowerCase(it.type)}" var="type"/>
                            <fmt:message bundle="${loc}" key="locale.room.view.${fn:toLowerCase(it.windowView)}" var="windowView"/>



                                ${room_type} ${type}<br/>
                                ${floor} ${it.floor}<br/>
                                ${view} ${windowView}<br/>
                                ${day_price} ${it.price}<br/>
                                <c:set value="${it.price}" var="price"/>
                                <c:set value="${sessionScope.days}" var="days"/>
                                <h5>${period_price} ${price*days}</h5>
                                <c:if test="${sessionScope.currentUser.discount>0}">
                                <h5>Цена со скидкой: ${(price*days)*(100-sessionScope.currentUser.discount)/100}</h5></c:if>

                                <input type="hidden" name="total_price${it.roomID}" value="${(price*days)*(100-sessionScope.currentUser.discount)/100}"/>
                                <input type="hidden" name="roomID${it.roomID}" value="${it.roomID}"/>
                                <input type="hidden"  name="resFrom" value="${resFrom}"/>
                                <input type="hidden" name="resTo" value="${resTo}"/>

                                <button type="submit" class="btn btn-info" name="formNumber" value="${it.roomID}">${book}</button>


                        <hr/>
                        </c:forEach>
                        </form>
                    </div>


                </div>
                </c:if>



                </article>
        </div>

        </div>

    </div>
</div>
<jsp:include page="/WEB-INF/jsp/page_component/Footer.jsp"/>
</body>
</html>
