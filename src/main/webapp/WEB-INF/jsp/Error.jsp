<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>

<body>
<jsp:include page="/WEB-INF/jsp/page_component/Navbar.jsp"/>

<div class="container">
    <div class="row">

        <jsp:include page="/WEB-INF/jsp/page_component/Menubar.jsp"/>


        <div class="col-md-7" style="margin-left:-15px">
            <div class="card bg-light">
                <article class="card-body mx-auto" style="width: 400px;">
                <h2>Sorry, server is temporary unavailable</h2>

                </article>
            </div>
        </div>
    </div>
</div>
</body>
</html>