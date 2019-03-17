<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="locale.navbar.signin" var="signin"/>
<fmt:message bundle="${loc}" key="locale.user.signin.enter_email" var="enter_email"/>
<fmt:message bundle="${loc}" key="locale.user.signin.enter_password" var="enter_password"/>
<fmt:message bundle="${loc}" key="locale.user.signin.error" var="error"/>

<html>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>

<body>
<jsp:include page="/WEB-INF/jsp/page_component/Navbar.jsp"/>

<div class="container">
    <div class="row">

        <jsp:include page="/WEB-INF/jsp/page_component/Menubar.jsp"/>


        <div class="col-md-7" style="margin-left:-15px">
            <div class="card bg-light">
                <article class="card-body" style="max-width: 800px;">
                    <h4 class="card-title mt-3 text-center">${signin}</h4>
                    <form action="${pageContext.request.contextPath}/ControllerServlet" method="post">
                        <input type="hidden" name="command" value="login"/>
                    <div align="center">
                        <div class="form-group input-group" style="width: 300px">
                            <div class="input-group-prepend">
                                <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                            </div>
                            <input name="email" class="form-control" placeholder="${enter_email}" type="text">
                        </div> <!-- form-group// -->

                        <div class="form-group input-group" style="width: 300px">
                            <div class="input-group-prepend">
                                <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
                            </div>
                            <input name="password" class="form-control" placeholder="${enter_password}" type="password">
                        </div> <!-- form-group// -->

                            <div style="color: red">${error}</div>
                    </div>

                        <p class="text-center"> <button type="submit" class="btn btn-info">${signin}</button> </p>
                    </form>
                </article>
            </div>

        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/page_component/Footer.jsp"/>
</body>
</html>