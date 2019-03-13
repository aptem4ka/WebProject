package com.epam.hotel.dao.util;

import com.epam.hotel.web.util.StringConstants;

public class SqlQuery {
    public final static String ROOM_TYPES_QUERY = "SELECT DISTINCT type FROM rooms;";
    public final static String ALL_ROOM_IMAGES_QUERY = "SELECT link FROM images WHERE preview=0;";
    public final static String ROOM_IMAGES_FOR_TYPE= "SELECT link FROM images WHERE preview=0 AND roomType=?;";
    public final static String ALL_ROOM_PREVIEWS = "SELECT link FROM images WHERE preview=1;";
    public final static String MIN_AND_MAX_PRICE = "SELECT MAX(price) AS max, MIN(price) AS min FROM rooms WHERE type=?;";
    public final static String ALLOCATIONS_FOR_TYPE = "SELECT DISTINCT allocation FROM rooms WHERE type=?;";
    public final static String ALLOCATIONS_IGNORE_TYPE = "SELECT DISTINCT allocation FROM rooms;";
    public final static String FIND_ROOM_BY_TYPE = "SELECT roomID, type, allocation, price, view, floor FROM rooms " +
            "WHERE type=? AND allocation=? AND available=1 AND roomID NOT IN(" +
            "SELECT rooms.roomID FROM orders INNER JOIN rooms ON rooms.roomID=orders.roomID " +
            "WHERE status='APPLIED' AND((? > orders.resFrom AND ? < orders.resTo) OR " +
            "(? > orders.resFrom AND ? < orders.resTo) OR (? < orders.resFrom AND ? > orders.resTo)));";
    public final static String FIND_ROOM_IGNORE_TYPE = "SELECT roomID, type, allocation, price, view, floor FROM rooms " +
                    "WHERE allocation=? AND available=1 AND roomID NOT IN(" +
                    "SELECT rooms.roomID FROM orders INNER JOIN rooms ON rooms.roomID=orders.roomID " +
                    "WHERE status='APPLIED' AND((? > orders.resFrom AND ? < orders.resTo) OR " +
                    "(? > orders.resFrom AND ? < orders.resTo) OR (? < orders.resFrom AND ? > orders.resTo)));";
    public final static String CHANGE_ORDER_IGNORE_TYPE = "SELECT roomID, type, allocation, price, view, floor FROM rooms " +
            "WHERE allocation=? AND available=1 AND roomID NOT IN(" +
            "SELECT rooms.roomID FROM orders INNER JOIN rooms ON rooms.roomID=orders.roomID " +
            "WHERE status='APPLIED' AND((? > orders.resFrom AND ? < orders.resTo) OR " +
            "(? > orders.resFrom AND ? < orders.resTo) OR (? < orders.resFrom AND ? > orders.resTo)) AND orders.orderID<>?);";


    public static final String LOGIN = "SELECT * FROM users WHERE email=?;";
    public static final String CHECK_EMAIL_QUERY = "SELECT * FROM users WHERE email=?;";
    public static final String REGISTER_QUERY = "INSERT INTO users (name,surname,email,phone,password) values (?,?,?,?,?);";
    public static final String ADD_ORDER = "INSERT INTO orders (userID, roomID, resFrom, resTo, price) VALUES (?,?,?,?,?);";
    public static final String ADD_UNREGISTERED_USER = "INSERT INTO unregistered_users (orderID, name, surname, phone) VALUES (?,?,?,?);";
    public static final String USER_ORDERS_STATISTICS ="SELECT * FROM orders WHERE userID=?;";
    public static final String ACTIVE_ORDERS = "SELECT * FROM orders WHERE status='APPLIED' and resFrom>? LIMIT ?,?;";
    public static final String ORDERS_HISTORY = "SELECT * FROM orders WHERE status='APPLIED' and resFrom<=? LIMIT ?,?;";
    public static final String UPDATE_ORDER_STATUS = "UPDATE orders SET status=?, comment=? WHERE orderID=?;";
    public static final String USER_COMPLETED_ORDERS = "SELECT COUNT(*) as count FROM orders WHERE userID=? AND status='COMPLETED';";
    public static final String REGISTERED_USER_BY_ORDER = "SELECT users.userID, name, surname, email, phone FROM users INNER JOIN orders ON users.userID = orders.userID WHERE orders.orderID=?;";
    public static final String GUEST_BY_ORDER = "SELECT name, surname, phone FROM unregistered_users INNER JOIN orders ON orders.orderID = unregistered_users.orderID WHERE orders.orderID=?;";
    public static final String COUNT_ORDERS_BY_STATUS ="SELECT COUNT(orderID) as count FROM orders WHERE status=?;";
    public static final String USER_ACTIVE_ORDERS = "SELECT * FROM orders WHERE userID=? AND status='APPLIED' LIMIT ?,?;";
    public static final String USER_ORDERS_HISTORY = "SELECT * FROM orders WHERE userID=? AND (status='CANCELLED' OR status='COMPLETED') LIMIT ?,?;";
    public static final String ACTUAL_ORDER_PRICE = "SELECT price FROM orders WHERE userID=? AND orderID=? AND status='APPLIED';";
    public static final String ROOM_INFO_BY_ROOMID = "SELECT * FROM rooms WHERE roomID=?;";
    public static final String UPDATE_ORDER = "UPDATE orders SET roomID=?, resFrom=?, resTo=?, price=? WHERE orderID=?;";
    public static final String SEARCH_ORDER_BY_FULLNAME = "SELECT orders.orderID, orders.userID, orders.resFrom, orders.resTo, orders.status FROM orders INNER JOIN users ON users.userID=orders.userID WHERE users.name LIKE ? AND users.surname LIKE ? UNION ALL SELECT orders.orderID, orders.userID, orders.resFrom, orders.resTo, orders.status FROM orders INNER JOIN unregistered_users ON unregistered_users.orderID=orders.orderID WHERE unregistered_users.name LIKE ? AND unregistered_users.surname LIKE ?;";
    public static final String SEARCH_ORDER_BY_GUEST_FULLNAME = "SELECT orders.orderID, orders.userID, orders.resFrom, orders.resTo FROM orders INNER JOIN unregistered_users ON unregistered_users.userID=orders.userID WHERE unregistered_users.name LIKE ? AND unregistered_users.surname LIKE ?;";

}