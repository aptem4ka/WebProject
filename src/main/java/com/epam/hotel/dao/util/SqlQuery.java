package com.epam.hotel.dao.util;

public class SqlQuery {
    public final static String ROOM_TYPES_QUERY = "SELECT DISTINCT type FROM rooms;";
    public final static String ALL_ROOM_IMAGES_QUERY = "SELECT link FROM images WHERE preview=0;";
    public final static String ROOM_IMAGES_FOR_TYPE= "SELECT link FROM images WHERE preview=0 AND roomType=?;";
    public final static String ALL_ROOM_PREVIEWS = "SELECT link FROM images WHERE preview=1;";
    public final static String MIN_AND_MAX_PRICE = "SELECT MAX(price) AS max, MIN(price) AS min FROM rooms WHERE type=?;";
    public final static String FIND_ROOM_BY_TYPE = "SELECT roomID, type, allocation, price FROM rooms " +
            "WHERE type=? AND allocation=? AND available=1 AND roomID NOT IN(" +
            "SELECT rooms.roomID FROM orders INNER JOIN rooms ON rooms.roomID=orders.roomID " +
            "WHERE (? > orders.resFrom AND ? < orders.resTo) OR " +
            "(? > orders.resFrom AND ? < orders.resTo) OR (? < orders.resFrom AND ? > orders.resTo));";
    public final static String FIND_ROOM_IGNORE_TYPE = "SELECT roomID, type, allocation, price FROM rooms " +
                    "WHERE allocation=? AND available=1 AND roomID NOT IN(" +
                    "SELECT rooms.roomID FROM orders INNER JOIN rooms ON rooms.roomID=orders.roomID " +
                    "WHERE (? > orders.resFrom AND ? < orders.resTo) OR " +
                    "(? > orders.resFrom AND ? < orders.resTo) OR (? < orders.resFrom AND ? > orders.resTo));";


    public static final String LOGIN = "SELECT * FROM users WHERE email=?;";
    public static final String CHECK_EMAIL_QUERY = "SELECT * FROM users WHERE email=?;";
    public static final String REGISTER_QUERY = "INSERT INTO users (name,surname,email,phone,password) values (?,?,?,?,?);";
}
