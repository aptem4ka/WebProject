<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>





<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
    <fmt:setBundle basename="resources.locale" var="loc"/>
    <fmt:message bundle="${loc}" key="locale.navbar.signin" var="signin"/>
    <fmt:message bundle="${loc}" key="locale.navbar.register" var="register"/>
    <fmt:message bundle="${loc}" key="locale.navbar.signout" var="signout"/>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<header>

    <nav class="navbar navbar-expand-lg navbar-light py-0" style="background-color: lightcyan">
        <div class="container-fluid">

            <div class="navbar-header mr-auto" style="margin-left: 10%">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/ControllerServlet?command=index_page"><h3 style="font-weight: bold"><span style="color: darkturquoise">&lt;</span>epam<span style="color: darkturquoise">&gt;</span> Hotel</h3></a>
            </div>
            <ul class="navbar-nav ml-auto" >
            <c:if test="${sessionScope.currentUser==null}">
                    <li class=" nav-item dropdown  dropleft" style="margin: 5px">
                        <button class="btn btn-success dropdown-toggle" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            ${signin}
                        </button>

                        <div class="dropdown-menu" style="width: 300px">
                            <form class="dropdown-item p-4" action="${pageContext.request.contextPath}/ControllerServlet" method="get">
                                <input type="hidden" name="command" value="login" />
                                <div class="form-group">
                                    <label for="exampleDropdownFormEmail2">Email address</label>
                                    <input type="email" name="email" class="form-control" id="exampleDropdownFormEmail2" placeholder="email@example.com">
                                </div>
                                <div class="form-group">
                                    <label for="exampleDropdownFormPassword2">Password</label>
                                    <input type="password" name="password" class="form-control" id="exampleDropdownFormPassword2" placeholder="Password">
                                </div>
                                <button type="submit" class="btn btn-success" >${signin}</button>
                            </form>
                        </div>
                    </li>
                    <li class="nav-item" style="margin: 5px">
                        <a href="${pageContext.request.contextPath}/ControllerServlet?command=register_page" class="btn btn-info" role="button">${register}</a>

                    </li>



            </c:if>
            <c:if test="${sessionScope.currentUser!=null}">
                    <li class="nav-item py-0" style="margin: 5px">
                        <a href="${pageContext.request.contextPath}/ControllerServlet?command=profile" class="btn btn-outline-success">Личный кабинет</a>
                    </li>

                    <li class="nav-item py-0" style="margin: 5px">

                        <a href="${pageContext.request.contextPath}/ControllerServlet?command=logout" class="btn btn-secondary" role="button">${signout}</a>

                    </li>

            </c:if>

            </ul>
            <ul class="navbar-nav" style="margin-left: 15px">
                <li class="nav-item" style="margin: 5px;">
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/ControllerServlet?command=change_locale&locale=ru"  >
                        <img src="${pageContext.request.contextPath}/images/icons/russian.jpg" alt="eng">
                    </a>
                </li>
                <li class="nav-item" style="margin: 5px; margin-left: -10px">
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/ControllerServlet?command=change_locale&locale=eng">
                        <img src="${pageContext.request.contextPath}/images/icons/british.jpg" alt="eng">
                    </a>
                </li>
            </ul>

        </div>
    </nav>
</header>
</html>