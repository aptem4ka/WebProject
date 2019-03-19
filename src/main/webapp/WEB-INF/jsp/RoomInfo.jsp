<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>


    <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
    <fmt:setBundle basename="locale" var="loc"/>

    <c:set value="${requestScope.type}" var="type"/>

    <fmt:message bundle="${loc}" key="locale.room.${fn:toLowerCase(type)}" var="roomType"/>
    <fmt:message bundle="${loc}" key="locale.room.description.${fn:toLowerCase(type)}" var="roomDesc"/>
    <fmt:message bundle="${loc}" key="locale.room.facilities" var="facilities"/>
    <fmt:message bundle="${loc}" key="locale.room.prices" var="prices"/>
    <fmt:message bundle="${loc}" key="locale.room.photos" var="photos"/>
    <fmt:message bundle="${loc}" key="locale.room.booking" var="booking"/>
    <fmt:message bundle="${loc}" key="locale.order.reserved_from" var="reserved_from"/>
    <fmt:message bundle="${loc}" key="locale.order.reserved_to" var="reserved_to"/>
    <fmt:message bundle="${loc}" key="locale.room.result.allocation" var="allocation"/>
    <fmt:message bundle="${loc}" key="locale.room.result.baby" var="baby_cots"/>
    <fmt:message bundle="${loc}" key="locale.room.find_room" var="find_room"/>



</head>
<body>

<jsp:include page="/WEB-INF/jsp/page_component/Navbar.jsp"/>

<div class="container">
    <div class="row">

        <jsp:include page="/WEB-INF/jsp/page_component/Menubar.jsp"/>
        <div class="col-md-7" style="margin-left:-15px">
            <div class="card bg-light">
                <article class="card-body">

            <h2>${roomType}</h2>
            <hr/>
            <div>${roomDesc}</div>
            <hr/>
            <div>
                <h3>${facilities}</h3>

                <ul>
                        <c:forEach items="${requestScope.facilities}" var="key">
                            <fmt:message bundle="${loc}" key="${key}" var="facility"/>
                            <li> ${facility}</li>
                        </c:forEach>
                </ul>

                <h3>${prices} ${requestScope.priceRange}USD</h3>
            </div>
            <hr/>


            <p style="text-align: center">
                <button class="btn btn-info btn-block" type="button" data-toggle="collapse" data-target="#carousel" aria-expanded="false" aria-controls="Collapse">
                    ${photos}
                </button>
            </p>

            <div id="carousel" class="collapse">
                <div id="demo" class="carousel slide auto" data-ride="carousel">

                    <!-- The slideshow -->
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <img height="60%" src="${pageContext.request.contextPath}${requestScope.images[0]}" alt="">
                        </div>

                        <c:forEach items="${requestScope.images}" var="it" begin="1">
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

            <hr/>

            <div align="center">
                <h3>${booking}</h3>

                <form action="${pageContext.request.contextPath}/main" method="get">
                    <input type="hidden" name="command" value="search_result"/>
                    <input type="hidden" name="type" value="${type}">

                        <div style="width: 300px" align="center">
                            ${reserved_from}
                            <input name="resFrom" class="form-control" type="date" required>
                            <hr/>
                        </div>

                        <div style="width: 300px" align="center">
                            ${reserved_to}
                            <input name="resTo" class="form-control" type="date"  required>
                            <hr/>
                        </div>
                    <div style="max-width: 500px; text-align: left">
                        <div>
                            <div style="text-align: center">${allocation}</div>
                            <c:forEach items="${requestScope.allocations}" var="it">
                                <fmt:message bundle="${loc}" key="locale.room.allocation.${fn:toLowerCase(it)}" var="allocationDesc"/>
                                <input type="radio" id="${it}" name="allocation" value="${it}" checked> ${allocationDesc}<br/>
                            </c:forEach>

                            <hr/>
                        </div>

                        <div>
                            ${baby_cots}
                            <select name="children">
                                <option value="0">0</option>
                                <option value="1">1</option>
                                <option value="2">2</option>
                            </select>
                            <hr/>
                        </div>

                    </div>
                    <div>
                        <button type="submit" class="btn btn-info">${find_room}</button>
                    </div>


            </form>
            </div>
                </article>
        </div>





    </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/page_component/Footer.jsp"/>

</body>
</html>
