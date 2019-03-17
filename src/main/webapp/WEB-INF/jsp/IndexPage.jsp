<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<html>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">
<head>


    <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
    <fmt:setBundle basename="locale" var="loc"/>
    <fmt:message bundle="${loc}" key="locale.room.result.allocation" var="allocation"/>
    <fmt:message bundle="${loc}" key="locale.order.reserved_from" var="reserved_from"/>
    <fmt:message bundle="${loc}" key="locale.order.reserved_to" var="reserved_to"/>
    <fmt:message bundle="${loc}" key="locale.room.result.baby" var="baby_cots"/>
    <fmt:message bundle="${loc}" key="locale.room.find_room" var="find_room"/>
    <fmt:message bundle="${loc}" key="locale.room.quick_search" var="quick_search"/>




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
            <!--<h1 style="font-style: italic; font-weight: bold; text-align: center"><span style="color: darkturquoise">&lt;</span>epam<span style="color: darkturquoise">&gt;</span> Hotel</h1><hr/>-->

                    <div>
                        <img width="100%" src="${pageContext.request.contextPath}/images/main_logo.jpg">
                    </div>

                   <hr/>
                    Отель EPAM, построенный в конце ХХ века, в течение двух десятков лет принимает туристов и гостей города. С того времени в его стенах произошли кардинальные изменения: о комфорте туристов позаботились современные дизайнеры, которые модернизировали интерьеры номеров. Отель EPAM готов предложить номера, которые удовлетворят все ваши требования.
<hr/>




                    <p>
                <button class="btn btn-link dropdown-toggle" type="button" data-toggle="collapse" data-target="#buttoncollapse" >
                    ${quick_search}
                </button>
            </p>
            <div class="collapse" id="buttoncollapse">
                <form action="${pageContext.request.contextPath}/ControllerServlet" method="get">
                    <input type="hidden" name="command" value="search_result"/>

                        <div  align="center">
                            ${reserved_from}
                            <input name="resFrom" class="form-control" type="date" style="width: 300px" required/>
                        </div>

                        <div align="center">
                            ${reserved_to}
                            <input name="resTo" class="form-control" type="date" style="width: 300px" required/>
                        </div>
                    <hr/>

                    <div align="center">${allocation}</div>
                            <c:forEach items="${requestScope.allocations}" var="it">
                                <fmt:message bundle="${loc}" key="locale.room.allocation.${fn:toLowerCase(it)}" var="allocationDesc"/>
                                <input type="radio" id="${it}" name="allocation" value="${it}" checked> ${allocationDesc}<br/>
                            </c:forEach>
                    <hr/>

                        <div class="form-group input-group" align="center">
                            ${baby_cots}
                            <select name="children">
                                <option value="0">0</option>
                                <option value="1">1</option>
                                <option value="2">2</option>
                            </select>
                        </div>
                        <div align="center">
                            <button type="submit" class="btn btn-info">${find_room}</button>
                        </div>

                </form>

            </div>






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

                </article>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/page_component/Footer.jsp"/>
</body>
</html>