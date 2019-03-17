
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>

    <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
    <fmt:setBundle basename="locale" var="loc"/>
    <fmt:message bundle="${loc}" key="locale.feedback.thanks" var="thanks"/>
    <fmt:message bundle="${loc}" key="locale.feedback.after_moderation" var="after_moderation"/>


</head>
<body>
<jsp:include page="/WEB-INF/jsp/page_component/Navbar.jsp"/>

<div class="container">
    <div class="row">

        <jsp:include page="/WEB-INF/jsp/page_component/Menubar.jsp"/>

        <div class="col-md-7" style="margin-left:-15px">
            <div class="card bg-light">
                <article class="card-body">
                    <h3>${thanks}</h3><br/>
                    <h5>${after_moderation}</h5>

                </article>
            </div>
        </div>

</body>
</html>
