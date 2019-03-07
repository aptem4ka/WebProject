package com.epam.hotel.dao;

import com.epam.hotel.dao.util.ConnectionPool;
import com.epam.hotel.exception.DAOException;

import java.sql.Connection;

public abstract class ParentDao {

    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();



    protected Connection getConnection() throws DAOException{

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
