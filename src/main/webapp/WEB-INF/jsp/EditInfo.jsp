<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<html>
<head>
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

                    <h3 style="text-align: center">Поздравляем!</h3>
                    <br/>
                    <p style="text-align: center">Ваша бронь успешно изменена.<br/>
                        Данные заказа можно посмотреть в личном кабинете.
                    <div>
                        <ul>
                            <li>Номер заказа: ${sessionScope.orderID}</li>
                            <li>Прибытие в отель: <fmt:formatDate value="${sessionScope.resFrom}" type="date"/></li>
                            <li>Отбытие из отеля: <fmt:formatDate value="${sessionScope.resTo}" type="date"/></li>
                            <li>Разница в цене: ${sessionScope.difference}</li>
                        </ul>
                    <c:if test="${sessionScope.difference<0}">Отрицательная разница в цене будет возвращена по прибытию в отель.</c:if>

                    </div>

                </article>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/page_component/Footer.jsp"/>
</body>
</html>