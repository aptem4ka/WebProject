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
        <div class="col-md-7" style="margin-top: 15px ">
            <h2>Номер СТАНДАРТ</h2>
            <hr/>
            <div>Стандартный однокомнатный номер для комфортного отдыха, включающий в себя холодильник, телевизор, беспроводной интернет, рабочий стол, средства гигиены.</div>
            <hr/>
            <div>
                <h3>Перечень удобств:</h3>
                <ul>
                    <li>Письменный стол</li>
                    <li>Спутниковое ТВ</li>
                    <li>Однокамерный холодильник</li>
                    <li>Телефон</li>
                    <li>Набор средств гигиены</li>
                </ul>
            </div>
<hr/>





            <p style="text-align: center">
                <button class="btn btn-info btn-block" type="button" data-toggle="collapse" data-target="#carousel" aria-expanded="false" aria-controls="Collapse">
                    Посмотреть фотографии
                </button>
            </p>

            <div id="carousel" class="collapse">
                <div id="demo" class="carousel slide" data-ride="carousel">

                    <!-- Indicators -->
                    <ul class="carousel-indicators">
                        <li data-target="#demo" data-slide-to="0" class="active"></li>
                        <li data-target="#demo" data-slide-to="1"></li>
                        <li data-target="#demo" data-slide-to="2"></li>
                    </ul>

                    <!-- The slideshow -->
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <img src="${pageContext.request.contextPath}/main/webapp/images/rooms/test2.jpg" alt="Los Angeles">
                        </div>
                        <div class="carousel-item">
                            <img src="${pageContext.request.contextPath}/main/webapp/images/rooms/test3.jpg" alt="Chicago">
                        </div>
                        <div class="carousel-item">
                            <img src="${pageContext.request.contextPath}/main/webapp/images/rooms/test4.jpg" alt="New York">
                        </div>
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
                <h3>Бронирование</h3>

                <form action="${pageContext.request.contextPath}/ControllerServlet" method="get">
                    <input type="hidden" name="command" value="search_result"/>
                    <input type="hidden" name="type" value="std">
                    <div style="max-width: 500px; text-align: left">
                        <div>
                            Дата заселения:
                            <input name="resFrom" class="form-control" type="date">
                            <hr/>
                        </div>

                        <div>
                            Дата выселения:
                            <input name="resTo" class="form-control" type="date">
                            <hr/>
                        </div>

                        <div>
                            Принцип размещения:<br/>

                                <input type="radio" id="SNGL" name="allocation" value="SNGL"><label for="SNGL"> STD - одноместный номер с односпальной кроватью</label><br/>
                                <input type="radio" id="DBL" name="allocation" value="DBL"><label for="DBL"> DBL - двухместный номер с двуспальной кроватью</label><br/>
                                <input type="radio" id="TWN" name="allocation" value="TWN"><label for="TWN"> TWN - двухместный номер с двумя односпальными кроватями</label><br/>
                            <hr/>
                            </div>

                            <div>
                                Дополнительные спальные места для детей
                                <select name="children">
                                    <option value="0">0</option>
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                </select>
                                <hr/>
                            </div>

                        </div>
                        <div>
                            <button type="submit" class="btn btn-info">Найти номер</button>
                        </div>


                </form>
            </div>
            </div>





        </div>
    </div>
</div>



</body>
</html>
