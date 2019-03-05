package com.epam.hotel.dao;

import com.epam.hotel.dao.util.ConnectionManager;
import com.epam.hotel.dao.util.ConnectionPool;
import com.epam.hotel.exception.DAOException;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class ParentDao {

    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();



    protected Connection getConnection() throws DAOException{

    /*   Connection connection=null;
       try {
           connection = ConnectionManager.getConnection();
       }catch (ClassNotFoundException | SQLException e){
            throw new DAOException(e);
       }*/


       try {
           return connectionPool.takeConnection();
       }catch (InterruptedException e){
           throw new DAOException(e);
       }
    }

    protected void releaseConnection(Connection connection) throws DAOException{

        try {
            connectionPool.releaseConnection(connection);
        }catch (InterruptedException e){
            throw new DAOException(e);
        }
    }


}
