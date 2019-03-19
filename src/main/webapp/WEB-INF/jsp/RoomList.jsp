
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="locale.room.room_info" var="room_info"/>

<html>
<head>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/page_component/Navbar.jsp"/>

<div class="container">
    <div class="row">

        <jsp:include page="/WEB-INF/jsp/page_component/Menubar.jsp"/>

        <div class="col-md-7" style="margin-left:-15px">
            <div class="card bg-light">
                <article class="card-body">

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
                            <a href="${pageContext.request.contextPath}/main?command=room_info&type=${fn:toLowerCase(type)}"
                               class="btn btn-info" role="button">${room_info}</a>
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
