package com.epam.hotel.dao;

import com.epam.hotel.dao.util.ConnectionManager;
import com.epam.hotel.exception.DAOException;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class ParentDao {

    public Connection getConnection() throws DAOException{
       Connection connection=null;
       try {
           connection = ConnectionManager.getConnection();
       }catch (ClassNotFoundException | SQLException e){
            throw new DAOException(e);
       }
       return connection;
    }
}
