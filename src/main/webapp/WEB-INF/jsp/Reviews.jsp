
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="locale.feedback.reviews" var="reviews"/>
<fmt:message bundle="${loc}" key="locale.feedback.incorrect_data" var="incorrect_data"/>
<fmt:message bundle="${loc}" key="locale.feedback.answer" var="answer"/>
<fmt:message bundle="${loc}" key="locale.feedback.leave_feedback" var="leave_feedback"/>
<fmt:message bundle="${loc}" key="locale.feedback.enter_name" var="enter_name"/>
<fmt:message bundle="${loc}" key="locale.feedback.enter_phone" var="enter_phone"/>
<fmt:message bundle="${loc}" key="locale.order.comment" var="comment"/>
<fmt:message bundle="${loc}" key="locale.feedback.rating" var="rating"/>

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


                    <h3 align="center">${reviews}</h3><hr/>
                    <c:if test="${param.incorrectDate==true}"><div style="text-align: center; color: red">${incorrect_data}</div></c:if>
                    <div>
                        <c:forEach items="${requestScope.reviewList}" var="it">
                            <h5>${it.name}<br/></h5>
                            <fmt:formatDate value="${it.added}"/><br/>
                            <c:if test="${it.rating==1}"><img src="${pageContext.request.contextPath}/images/icons/star.jpg"><br/></c:if>
                            <c:if test="${it.rating==2}"><c:forEach begin="1" end="2" step="1"><img src="${pageContext.request.contextPath}/images/icons/star.jpg"></c:forEach> <br/></c:if>
                            <c:if test="${it.rating==3}"><c:forEach begin="1" end="3" step="1"><img src="${pageContext.request.contextPath}/images/icons/star.jpg"></c:forEach> <br/></c:if>
                            <c:if test="${it.rating==4}"><c:forEach begin="1" end="4" step="1"><img src="${pageContext.request.contextPath}/images/icons/star.jpg"></c:forEach> <br/></c:if>
                            <c:if test="${it.rating==5}"><c:forEach begin="1" end="5" step="1"><img src="${pageContext.request.contextPath}/images/icons/star.jpg"></c:forEach> <br/></c:if>
                            <hr/>
                            ${it.comment}
                            <c:if test="${not empty it.answer}"><br/><b>${answer}</b> ${it.answer}</c:if>
                            <hr/>
                        </c:forEach>

                        <form action="ControllerServlet" method="get">
                            <input type="hidden" name="command" value="reviews_page"/>
                            <ul class="pagination justify-content-center">
                                <c:if test="${sessionScope.postedReviewsPaginator.startPos > '0'}">
                                    <li class="page-item"><button class="page-link" type="submit" name="page" value="prev">Prev</button></li>
                                </c:if>
                                <c:if test="${sessionScope.postedReviewsPaginator.lastPage==false}">
                                    <li class="page-item"><button class="page-link" type="submit" name="page" value="next">Next</button></li>
                                </c:if>
                            </ul>
                        </form>
                    </div>

                    <h3 align="center">${leave_feedback}</h3>
                    <c:if test="${sessionScope.currentUser == null}">
                        <div>

                            <form action="ControllerServlet" method="post">
                                <input type="hidden" name="command" value="leave_review">
                                <input type="hidden" name="type" value="guest">

                                <div class="form-group" style="width: 300px">
                                    <label for="nm">${enter_name}</label>
                                    <input type="text" class="form-control" name="name" id="nm" placeholder="ex. Tim" required>
                                </div>
                                <div class="form-group" style="width: 300px">
                                    <label for="phn">${enter_phone}</label>
                                    <input type="text" class="form-control" name="phone" id="phn" placeholder="ex. +375291234567" required>
                                </div>
                                <h5>${rating}</h5>
                                <div class="form-check-inline">
                                    <label class="form-check-label">
                                        <input type="radio" name="rating" value="1">1
                                    </label>
                                </div>
                                <div class="form-check-inline">
                                    <label class="form-check-label">
                                        <input type="radio" name="rating" value="2">2
                                    </label>
                                </div>
                                <div class="form-check-inline">
                                    <label class="form-check-label">
                                        <input type="radio" name="rating" value="3">3
                                    </label>
                                </div>
                                <div class="form-check-inline">
                                    <label class="form-check-label">
                                        <input type="radio" name="rating" value="4">4
                                    </label>
                                </div>
                                <div class="form-check-inline">
                                    <label class="form-check-label">
                                        <input type="radio" name="rating" value="5" checked>5
                                    </label>
                                </div>
                                <div class="form-group">
                                    <label for="cmnt">${comment}</label>
                                    <textarea class="form-control" name="comment" rows="5" id="cmnt" placeholder="Leave a comment" required></textarea>
                                </div>

                                <button type="submit" class="btn btn-success float-right">${leave_feedback}</button>

                            </form>
                        </div>
                    </c:if>

                    <c:if test="${sessionScope.currentUser != null}">
                        <div>

                            <form action="ControllerServlet" method="post">
                                <input type="hidden" name="command" value="leave_review">
                                <input type="hidden" name="type" value="user">


                                <h5>Rating</h5>
                                <div class="form-check-inline">
                                    <label class="form-check-label">
                                        <input type="radio" name="rating" value="1">1
                                    </label>
                                </div>
                                <div class="form-check-inline">
                                    <label class="form-check-label">
                                        <input type="radio" name="rating" value="2">2
                                    </label>
                                </div>
                                <div class="form-check-inline">
                                    <label class="form-check-label">
                                        <input type="radio" name="rating" value="3">3
                                    </label>
                                </div>
                                <div class="form-check-inline">
                                    <label class="form-check-label">
                                        <input type="radio" name="rating" value="4">4
                                    </label>
                                </div>
                                <div class="form-check-inline">
                                    <label class="form-check-label">
                                        <input type="radio" name="rating" value="5" checked>5
                                    </label>
                                </div>
                                <div class="form-group">
                                    <label for="cmnt1">${comment}</label>
                                    <textarea class="form-control" name="comment" rows="5" id="cmnt1" placeholder="Leave a comment" required></textarea>
                                </div>

                                <button type="submit" class="btn btn-success float-right">${leave_feedback}</button>

                            </form>
                        </div>
                    </c:if>





                </article>
            </div>
        </div>
    </div>
</div>

</body>
</html>
