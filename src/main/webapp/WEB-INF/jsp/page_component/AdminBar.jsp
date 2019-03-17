<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

</head>
<body>

<div class="list-group col-md-2" style="margin-left:-15px">
    <div class="card bg-light">
        <article class="card-body">
    <div style="font-size: 10pt">
        <form action="admin" method="get">
            <input type="hidden" name="command" value="search_user_by_order"/>
        Поиск пользователя по номеру заказа<hr/>
            <input type="number" name="orderID" class="form-control"  placeholder="№ заказа" required>
        <button style="margin-top: 5px" type="submit" class="btn btn-info btn-sm">Найти</button>
        </form>

        <hr style="border-top: 4px double #8c8b8b;"/>


        <form method="get" action="admin">
            <input type="hidden" name="command" value="search_order_by_name"/>
            Поиск заказа по имени и фамилии<hr/>
            <input type="text" class="form-control" name="name" placeholder="Имя" required/><br/>
            <input type="text" class="form-control" name="surname" placeholder="Фамилия" required/><br/>

            <button style="margin-top: 5px" type="submit" class="btn btn-info btn-sm">Найти</button>

        </form>

        <hr style="border-top: 4px double #8c8b8b;"/>


        <form method="get" action="admin">
            <input type="hidden" name="command" value="search_order_by_phone"/>
            Поиск заказа по номеру телефона<hr/>
            <input type="text" class="form-control" name="phone" placeholder="Телефон" value="+" required/><br/>

            <button style="margin-top: 5px" type="submit" class="btn btn-info btn-sm">Найти</button>

        </form>

        <hr style="border-top: 4px double #8c8b8b;"/>


        <form method="get" action="admin">
            <input type="hidden" name="command" value="search_user_by_id"/>
            Поиск пользователя по ID<hr/>
            <input type="number" class="form-control" name="userID" placeholder="ID" required/><br/>

            <button style="margin-top: 5px" type="submit" class="btn btn-info btn-sm">Найти</button>

        </form>

    </div>

        </article>
    </div>


</div>

</body>
</html>
