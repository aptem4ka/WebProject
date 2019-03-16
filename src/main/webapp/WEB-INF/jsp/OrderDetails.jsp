<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>


<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="locale.order.congrats" var="congrats"/>
<fmt:message bundle="${loc}" key="locale.order.order_accepted" var="order_accepted"/>
<fmt:message bundle="${loc}" key="locale.order.unregistered_status" var="unregistered_status"/>
<fmt:message bundle="${loc}" key="locale.order.order_number" var="order_number"/>
<fmt:message bundle="${loc}" key="locale.order.reserved_from" var="reserved_from"/>
<fmt:message bundle="${loc}" key="locale.order.reserved_to" var="reserved_to"/>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<body>


<jsp:include page="/WEB-INF/jsp/page_component/Navbar.jsp"/>

<div class="container">
    <div class="row">

        <jsp:include page="/WEB-INF/jsp/page_component/Menubar.jsp"/>


        <div class="col-md-7" style="margin-left:-15px">

            <div class="card bg-light">
                <article class="card-body">

            <h3 style="text-align: center">${congrats}</h3>
            <br/>
            <p style="text-align: center">${order_accepted}
                <c:if test="${sessionScope.currentUser==null}">
                <br/>${unregistered_status}</p>
                </c:if>
            <div>
                <ul>
                    <li>${order_number} ${sessionScope.order.orderID}</li>
                    <li>${reserved_from} <fmt:formatDate value="${sessionScope.order.resFrom}" type="date"/></li>
                    <li>${reserved_to} <fmt:formatDate value="${sessionScope.order.resTo}" type="date"/></li>
                </ul>

            </div>

                </article>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/page_component/Footer.jsp"/>
</body>
</html>