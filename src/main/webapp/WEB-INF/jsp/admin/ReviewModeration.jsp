
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>

    <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
    <fmt:setBundle basename="locale" var="loc"/>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/page_component/Navbar.jsp"/>

<div class="container">
    <div class="row">

        <jsp:include page="/WEB-INF/jsp/page_component/Menubar.jsp"/>

        <div class="col-md-7" style="margin-left:-15px">
            <div class="card bg-light">
                <article class="card-body">
                    <h4>Отзывы, ожидающие модерацию</h4><hr/>

                    <div>
                        <c:forEach items="${requestScope.reviewList}" var="it">
                            <form action="" method="post">
                                <input type="hidden" name="command" value="update_review_status">
                                <input type="hidden" name="reviewID" value="${it.reviewID}">
                        Отзыв №${it.reviewID} | Пользователь: <c:if test="${it.userID==0}">Гость</c:if>
                        <c:if test="${it.userID>0}">${it.userID}</c:if> |
                        Имя: ${it.name}<br/>
                        <c:if test="${not empty it.phone}">Телефон: ${it.phone} |</c:if>
                        Добавлен: <fmt:formatDate value="${it.added}"/><br/>
                        Оценка: ${it.rating}<br/>
                        Комментарий: ${it.comment}

                                <ul class="list-unstyled">

                                    <li style="margin: 5px">

                                        <button type="submit" class="btn btn-danger btn-sm float-right" name="update_type" value="cancelled" style="margin: 5px">Cancel</button>
                                        <button data-toggle="dropdown" class="btn btn-success btn-sm float-right" style="margin: 5px">Apply</button>
                                        <div class="dropdown-menu" style="width: 350px">

                                            <div class="form-group">
                                                <label for="comment1">Answer</label>
                                                <textarea name="answer" class="form-control" id="comment1" placeholder="Answer" rows="5"></textarea>
                                            </div>
                                            <button type="submit" class="btn btn-success" name="update_type" value="posted">Post review</button>

                                        </div>
                                    </li>
                                </ul>
                                <br/>



                            </form>
                            <hr style="border-top: 4px double #8c8b8b;"/>
                        </c:forEach>

                        <form action="" method="get">
                            <input type="hidden" name="command" value="review_moderation"/>
                            <ul class="pagination justify-content-center">
                                <c:if test="${sessionScope.reviewsPaginator.startPos > '0'}">
                                    <li class="page-item"><button class="page-link" type="submit" name="page" value="prev">Prev</button></li>
                                </c:if>
                                <c:if test="${sessionScope.reviewsPaginator.lastPage==false}">
                                    <li class="page-item"><button class="page-link" type="submit" name="page" value="next">Next</button></li>
                                </c:if>
                            </ul>
                        </form>
                    </div>


                </article>
            </div>
        </div>
        <jsp:include page="/WEB-INF/jsp/page_component/AdminBar.jsp"/>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/page_component/Footer.jsp"/>
</body>
</html>
