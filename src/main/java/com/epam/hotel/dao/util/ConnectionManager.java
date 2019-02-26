package com.epam.hotel.dao.util;

import com.epam.hotel.exception.DAOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class ConnectionManager {
    public static Connection connection;
    public static String url;

    //TEMP METHOD

    public static Connection getConnection() throws ClassNotFoundException, SQLException{

        ResourceBundle resourceBundle=ResourceBundle.getBundle("DBConnection");

        String url = resourceBundle.getString("database.url");
        String login = resourceBundle.getString("database.login");
        String pass = resourceBundle.getString("database.password");
        Class.forName(resourceBundle.getString("database.driver"));

        connection = DriverManager.getConnection(url, login, pass);

    return connection;
    }

}
