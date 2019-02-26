
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/page_component/Navbar.jsp"/>

<div class="container">
    <div class="row">

        <jsp:include page="/WEB-INF/jsp/page_component/Menubar.jsp"/>

        <div class="col-md-7" style="margin: 15px ">

                <c:if test="${empty requestScope.roomList}">
                    <h3>Извините</h3>
                    На указанный Вами период нет свободных номеров, удовлетворяющих заданным критериям.<br/>
                    Попробуйте выбрать номер другого типа, или изменить период пребывания.
                </c:if>
                <c:if test="${not empty requestScope.roomList}">
                <c:set value="${requestScope.roomList[0]}" var="it" scope="request"/>
                <div >
                    <div>
                        <h2>Данные брони</h2>
                        Тип номера: ${it.type}<br/>
                        Принцип размещения: ${it.allocation}<br/>
                        Дополнительные спальные места для детей: ${it.children}<br/>
                        Бронь с <fmt:formatDate value="${it.resFrom}" type="date"/> по <fmt:formatDate value="${it.resTo}" type="date"/>
                        <hr/>
                        <h3>Цена: ${it.price}</h3>
                        <hr/>
                    </div>
                    <div align="center">
                        <c:if test="${sessionScope.currentUser!=null}">
                            <form action="ControllerServlet" method="get">
                                <input type="hidden" name="command" value="type_std"/>
                                <button type="submit" class="btn btn-info">Забронировать</button>
                            </form>
                        </c:if>
                        <c:if test="${sessionScope.currentUser==null}">

                            <button class="btn btn-info" type="button" data-toggle="collapse" data-target="#booking" aria-expanded="false" aria-controls="Collapse">
                                Забронировать
                            </button>


                        <div id="booking" class="collapse" style="color: red">
                            Только авторизированные пользователи могут оставлять заявки на бронирование
                        </div>
                        </c:if>

                    </div>
                    </c:if>
                </div>





        </div>
    </div>
</div>
</body>
</html>
