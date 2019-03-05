<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
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
        <article class="card-body mx-auto" style="width: 400px;">
            <h4 class="card-title mt-3 text-center">Create Account</h4>
            <form action="${pageContext.request.contextPath}/ControllerServlet" method="post">
                <input type="hidden" name="command" value="register"/>

                <div class="form-group input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                    </div>
                    <input name="name" class="form-control" placeholder="Name" type="text" required>*
                </div> <!-- form-group// -->
                <div class="form-group input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                    </div>
                    <input name="surname" class="form-control" placeholder="Surname" type="text" required>*
                </div>
                <!-- form-group// -->
                <div class="form-group input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="fa fa-envelope"></i> </span>
                    </div>
                    <input name="email" class="form-control" placeholder="Email address" type="email" required>*
                </div>

                <!-- form-group// -->
                <div class="form-group input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="fa fa-phone"></i> </span>
                    </div>
                    <input name="phone" class="form-control" placeholder="Phone(not requied) ex. +375291234567" type="text">
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
                    <button type="submit" class="btn btn-primary btn-block"> Create Account  </button>
                </div> <!-- form-group// -->
            </form>
            <p style="color:red; text-align: center; font-size: 8pt">
                <c:if test="${param.incorrectName eq true}">
                *Неверное имя или фамилия. Поле должно содержать только латиницу или кириллицу<br/>
            </c:if>
                <c:if test="${param.incorrectPassword eq true}">
                    *Пароль должен состоять из 3-16 латинских букв и цифр<br/>
                </c:if>
                <c:if test="${param.passwordsMatch eq false}">
                    *Введенные Вами пароли не совпадают<br/>
                </c:if>
                <c:if test="${param.incorrectEmail eq true}">
                    *Неверный или неуникальный адрес электронной почты<br/>
                </c:if>
                <c:if test="${param.incorrectPhone eq true}">
                    *Неверный формат номера телефона<br/>
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