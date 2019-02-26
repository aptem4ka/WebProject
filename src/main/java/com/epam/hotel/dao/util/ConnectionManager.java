package com.epam.hotel.dao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionManager {
    public static Connection connection;
    public static String url;

    public static Connection getConnection() throws Exception{


     //String url = "jdbc:mysql://localhost:3306/hoteldb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String url = "jdbc:mysql://localhost:3306/hoteldb?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

        //Class.forName("com.mysql.cj.jdbc.Driver");
        Class.forName("com.mysql.jdbc.Driver");
    connection= DriverManager.getConnection(url, "root","LGD802g2");

    return connection;
    }

}
