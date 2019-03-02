<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
    <fmt:setBundle basename="resources.locale" var="loc"/>
    <fmt:message bundle="${loc}" key="locale.menubar.about" var="about"/>
    <fmt:message bundle="${loc}" key="locale.menubar.main" var="main"/>
    <fmt:message bundle="${loc}" key="locale.menubar.rooms" var="rooms"/>
    <fmt:message bundle="${loc}" key="locale.menubar.check" var="check"/>



</head>
<body>

<div class="list-group col-md-3" style="margin-right:-15px; margin-left:-15px">

    <a href="${pageContext.request.contextPath}/ControllerServlet?command=index_page"
       class="list-group-item list-group-item-action list-group-item-light">${main}</a>
    <a href="${pageContext.request.contextPath}/ControllerServlet?command=room_list"
       class="list-group-item list-group-item-action list-group-item-light">${rooms}</a>
    <a href="#" class="list-group-item list-group-item-action list-group-item-light">${check}</a>
    <a href="#" class="list-group-item list-group-item-action list-group-item-light">${about}</a>

</div>

</body>
</html>
