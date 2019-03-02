
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

                <div >
                    <div>
                        <fmt:formatDate value="${requestScope.resFrom}" type="date" var="resFrom"/>
                        <fmt:formatDate value="${requestScope.resTo}" type="date" var="resTo"/>
                        <form action="ControllerServlet" method="post">
                            <c:if test="${sessionScope.currentUser == null}">
                                <hr/>
                                Введите свои контактные данные или зарегистрируйтесь в системе.<br/>
                                Зарегистрированные пользователи могут контролировать статус брони в личном кабинете, а также получают скидки с каждым новым посещением отеля.
                                <div class="form-group input-group">
                                    <input name="name" class="form-control" placeholder="Name (ex. Tim)" type="text" required>
                                    <input name="surname" class="form-control" placeholder="Surname (ex. Cook)" type="text" required>
                                </div>
                                <div class="form-group input-group">
                                    <input name="phone" class="form-control" placeholder="Phone (ex. +375291234567)" type="text" required>
                                </div>
                                <hr/>
                            </c:if>

                        <h2>Данные брони</h2>
                        <h5>Период бронирования: ${resFrom} - <fmt:formatDate value="${requestScope.resTo}" type="date"/><br/>
                            Принцип размещения: ${requestScope.allocation}<br/>
                            Дополнительные спальные места для детей: ${requestScope.children}<br/>

                        </h5>

                        <hr/>
                        <c:forEach items="${requestScope.roomList}" var="it">

                                <input type="hidden" name="command" value="book"/>
                                Тип номера: ${it.type}<br/>
                                Цена за сутки: ${it.price}<br/>
                                <c:set value="${it.price}" var="price"/>
                                <c:set value="${requestScope.days}" var="days"/>
                                <h5>Цена за период пребывания: ${price*days}</h5>

                                <input type="hidden" name="roomID" value="${it.roomID}"/>
                                <input type="hidden"  name="resFrom" value="${resFrom}"/>
                                <input type="hidden" name="resTo" value="${resTo}"/>

                                <button type="submit" class="btn btn-info">Забронировать</button>


                        <hr/>
                        </c:forEach>
                        </form>
                    </div>
                    <div align="center">
                        <c:if test="${sessionScope.currentUser!=null}">
                            <form action="ControllerServlet" method="get">
                                <input type="hidden" name="command" value="book"/>
                                <input type="hidden" name="resFrom" value="${requestScope.resFrom}"/>
                                <input type="hidden" name="resTo" value="${requestScope.resTo}"/>
                                <input type="hidden" name="type" value="${requestScope.resFrom}"/>
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
