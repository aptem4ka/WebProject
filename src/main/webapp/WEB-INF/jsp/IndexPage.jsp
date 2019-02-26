<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
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
            <h1 style="font-style: italic; font-weight: bold; text-align: center"><span style="color: darkturquoise">&lt;</span>epam<span style="color: darkturquoise">&gt;</span> Hotel</h1><hr/>
                КАКОЕ-ТО ОПИСАНИЕ ОТЕЛЯ, ХЗ<hr/>
                <div id="demo" class="carousel slide auto" data-ride="carousel">

                    <!-- The slideshow -->
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <img height="60%" src="${pageContext.request.contextPath}/images/intro.jpg" alt="">
                        </div>

                        <c:forEach items="${requestScope.carouselImages}" var="it">
                            <div class="carousel-item">
                                <img height="60%" src="${pageContext.request.contextPath}${it}" alt="">
                            </div>

                        </c:forEach>

                    </div>

                    <!-- Left and right controls -->
                    <a class="carousel-control-prev" href="#demo" data-slide="prev">
                        <span class="carousel-control-prev-icon"></span>
                    </a>
                    <a class="carousel-control-next" href="#demo" data-slide="next">
                        <span class="carousel-control-next-icon"></span>
                    </a>

                </div>

        </div>
    </div>
</div>
</body>
</html>