<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<html>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<body>


<jsp:include page="/WEB-INF/jsp/page_component/Navbar.jsp"/>

<div class="container">
    <div class="row">

        <jsp:include page="/WEB-INF/jsp/page_component/Menubar.jsp"/>


        <div class="col-md-7">


            <div class="card bg-light">
                <article class="card-body" style="width: 400px;">
                    <h4 class="card-title mt-3 text-center">Check your booking status</h4><br/>

                    <form action="${pageContext.request.contextPath}/ControllerServlet" method="post">
                        <input type="hidden" name="command" value="register"/>

                        <div class="form-group input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"> <i class="fa fa-clipboard-check"></i> </span>
                            </div>
                            <input name="orderID" class="form-control" placeholder="Your order number" type="text" required>*
                        </div> <!-- form-group// -->

                        <div class="form-group input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                            </div>
                            <input name="name" class="form-control" placeholder="Your name" type="text" required>*
                        </div>
                        <!-- form-group// -->
                        <div class="form-group input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                            </div>
                            <input name="surname" class="form-control" placeholder="Your surname" type="text" required>*
                        </div>

                        <!-- form-group// -->
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary btn-block"> Check status</button>
                        </div> <!-- form-group// -->
                    </form>

                </article>

            </div>


        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/page_component/Footer.jsp"/>
</body>

</html>