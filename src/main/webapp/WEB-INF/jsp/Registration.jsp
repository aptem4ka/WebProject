<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>


<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="locale.user.create_account" var="create_account"/>
<fmt:message bundle="${loc}" key="locale.user.info" var="user_info"/>
<fmt:message bundle="${loc}" key="locale.user.user_email" var="user_email"/>
<fmt:message bundle="${loc}" key="locale.user.user_name" var="user_name"/>
<fmt:message bundle="${loc}" key="locale.user.user_surname" var="user_surname"/>
<fmt:message bundle="${loc}" key="locale.user.user_phone" var="user_phone"/>
<fmt:message bundle="${loc}" key="locale.user.phone_placeholder" var="phone_placeholder"/>
<fmt:message bundle="${loc}" key="locale.user.incorrect_name" var="incorrect_name"/>
<fmt:message bundle="${loc}" key="locale.user.incorrect_email" var="incorrect_email"/>
<fmt:message bundle="${loc}" key="locale.user.incorrect_password" var="incorrect_password"/>
<fmt:message bundle="${loc}" key="locale.user.incorrect_match" var="incorrect_match"/>
<fmt:message bundle="${loc}" key="locale.user.incorrect_phone" var="incorrect_phone"/>

<html>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/page_component/Navbar.jsp"/>

<div class="container">
    <div class="row">

        <jsp:include page="/WEB-INF/jsp/page_component/Menubar.jsp"/>


<div class="col-md-7" style="margin-left:-15px">
    <div class="card bg-light">
        <article class="card-body mx-auto" style="width: 400px;">
            <h4 class="card-title mt-3 text-center">${create_account}</h4>
            <form action="${pageContext.request.contextPath}/main" method="post">
                <input type="hidden" name="command" value="register"/>

                <div class="form-group input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                    </div>
                    <input name="name" class="form-control" placeholder="${user_name}" type="text" required>*
                </div> <!-- form-group// -->
                <div class="form-group input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                    </div>
                    <input name="surname" class="form-control" placeholder="${user_surname}" type="text" required>*
                </div>
                <!-- form-group// -->
                <div class="form-group input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="fa fa-envelope"></i> </span>
                    </div>
                    <input name="email" class="form-control" placeholder="${user_email}" type="email" required>*
                </div>

                <!-- form-group// -->
                <div class="form-group input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="fa fa-phone"></i> </span>
                    </div>
                    <input name="phone" class="form-control" placeholder="${user_phone} ${phone_placeholder}" type="text">
                </div> <!-- form-group// -->

                <div class="form-group input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
                    </div>
                    <input name="password" class="form-control" placeholder="Create password" type="password" required>*
                </div> <!-- form-group// -->
                <div class="form-group input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
                    </div>
                    <input name="repeat" class="form-control" placeholder="Repeat password" type="password" required>*
                </div>

                <!-- form-group// -->
                <div class="form-group">
                    <button type="submit" class="btn btn-primary btn-block"> ${create_account} </button>
                </div> <!-- form-group// -->
            </form>
            <p style="color:red; text-align: center; font-size: 8pt">
                <c:if test="${param.incorrectName eq true}">
                *${incorrect_name}<br/>
            </c:if>
                <c:if test="${param.incorrectPassword eq true}">
                    *${incorrect_password}<br/>
                </c:if>
                <c:if test="${param.passwordsMatch eq false}">
                    *${incorrect_match}<br/>
                </c:if>
                <c:if test="${param.incorrectEmail eq true}">
                    *${incorrect_email}<br/>
                </c:if>
                <c:if test="${param.incorrectPhone eq true}">
                    *${incorrect_phone}<br/>
                </c:if>
            </p>
            <p style="color:red;text-align: center"></p>
            <p style="color:red;text-align: center"></p>
        </article>

    </div>

    </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/page_component/Footer.jsp"/>
</body>
</html>