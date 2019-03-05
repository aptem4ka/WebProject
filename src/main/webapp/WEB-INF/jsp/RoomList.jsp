
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>

    <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
    <fmt:setBundle basename="resources.locale" var="loc"/>


</head>
<body>
<jsp:include page="/WEB-INF/jsp/page_component/Navbar.jsp"/>

<div class="container">
    <div class="row">

        <jsp:include page="/WEB-INF/jsp/page_component/Menubar.jsp"/>

        <div class="col-md-7" style="margin-left:-15px">
            <div class="card bg-light">
                <article class="card-body mx-auto">

            <p>
                <button class="btn btn-info dropdown-toggle" type="button" data-toggle="collapse" data-target="#buttoncollapse" aria-expanded="false" aria-controls="Collapse">
                    найти номер любого типа
                </button>
            </p>
            <div class="collapse" id="buttoncollapse">
                <form action="${pageContext.request.contextPath}/ControllerServlet" method="get">
                    <input type="hidden" name="command" value="search_result"/>
                <div style="max-width: 300px">
                    <div>
                        Дата заселения:
                        <input name="resFrom" class="form-control" type="date">
                    </div>

                    <div>
                        Дата выселения:
                        <input name="resTo" class="form-control" type="date">
                    </div>
                    <div class="form-group input-group">
                        Количество спальных мест для взрослых
                        <select name="allocation">
                            <option value="SNGL">Одно</option>
                            <option value="DBL">Двухспальная кровать</option>
                            <option value="TWN">Две односпальные кровати</option>
                        </select>
                    </div>
                    <div class="form-group input-group">
                        Дополнительные спальные места для детей
                        <select name="children">
                            <option value="0">0</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                        </select>
                    </div>
                    <div>
                        <button type="submit" class="btn btn-info">Найти номер</button>
                    </div>
                </div>
                </form>

            </div>




            <c:forEach items="${requestScope.roomPreviews}" var="it">
                <c:set value="${it.key}" var="type"/>
                <fmt:message bundle="${loc}" key="locale.room.${fn:toLowerCase(type)}" var="roomType"/>
                <fmt:message bundle="${loc}" key="locale.room.description.${fn:toLowerCase(type)}" var="roomDesc"/>

                <div class="row  bg-light" style="margin-bottom: 10px">
                    <div class="col-md-5">
                        <img src="${pageContext.request.contextPath}${it.value}" width="100%" height="210px"/>
                    </div>
                    <div class="col-md-7">
                        <div>
                            <h5>${roomType}</h5><hr/>
                        </div>
                        <div style="font-size: 10pt">
                            ${roomDesc}
                        </div><br/>
                        <div style="font-size: 10pt">
                            <a href="${pageContext.request.contextPath}/ControllerServlet?command=room_info&type=${fn:toLowerCase(type)}"
                               class="btn btn-info" role="button">Информация о номере</a>
                        </div>
                    </div>

                </div>
                <hr/>

            </c:forEach>
                </article>
        </div>
        </div>

    </div>

</div>
<jsp:include page="/WEB-INF/jsp/page_component/Footer.jsp"/>
</body>
</html>
