<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>

<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<header>
    <nav class="navbar navbar-expand-sm bg-dark navbar-dark">

        <div class="container-fluid">

            <div class="navbar-header" style="margin-left: 10%">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">EPAM Hotel</a>
            </div>

            <c:if test="${sessionScope.currentUser==null}">
                <ul class="nav navbar-right" style="margin-right: 3%">
                    <li class=" nav-item dropdown  dropleft">
                        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            LOG IN
                        </button>
                        <div class="dropdown-menu" style="width: 300px">
                            <form class="dropdown-item p-4" action="ControllerServlet" method="post">
                                <input type="hidden" name="command" value="login" />
                                <div class="form-group">
                                    <label for="exampleDropdownFormEmail2">Email address</label>
                                    <input type="email" name="email" class="form-control" id="exampleDropdownFormEmail2" placeholder="email@example.com">
                                </div>
                                <div class="form-group">
                                    <label for="exampleDropdownFormPassword2">Password</label>
                                    <input type="password" name="password" class="form-control" id="exampleDropdownFormPassword2" placeholder="Password">
                                </div>
                                <button type="submit" class="btn btn-secondary" >Log in</button>
                            </form>
                        </div>
                    </li>
                    <li class="nav-item" style="margin-left: 5px">
                        <a href="${pageContext.request.contextPath}/jsp/Registration.jsp" class="btn btn-primary" role="button">REGISTER</a>

                    </li>
                </ul>
            </c:if>
            <c:if test="${sessionScope.currentUser!=null}">
                <ul class="nav navbar-nav navbar-right">
                    <li class="navbar-brand">You entered as ${sessionScope.currentUser.name}!</li>
                    <li>
                        <form action="ControllerServlet" method="get" class="form-inline">
                            <input type="hidden" name="command" value="logout"/>
                            <button class="btn btn-secondary" type="submit">LOGOUT</button>
                        </form>
                    </li>
                </ul>
            </c:if>
        </div>
    </nav>
</header>
</html>