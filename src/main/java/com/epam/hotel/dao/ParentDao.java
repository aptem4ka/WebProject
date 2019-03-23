package com.epam.hotel.dao;

import com.epam.hotel.dao.util.ConnectionPool;
import com.epam.hotel.exception.DAOException;

import java.sql.Connection;

/**
 * This abstract method has general functionality for all DAO implementations.
 *
 * @author Artsem Lashuk
 * @see ConnectionPool
 */
public abstract class ParentDao {

    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();

    /**
     * Get one free connection from connection pool
     * @return {@link Connection}
     * @throws DAOException if connection is taken with errors
     */
    protected Connection getConnection() throws DAOException{

       try {
           return connectionPool.takeConnection();
       }catch (InterruptedException e){
           throw new DAOException(e);
       }
    }

    /**
     * Put connection back to the connections pool.
     *
     * @param connection {@link Connection}
     * @throws DAOException if connection released with errors
     */
    protected void releaseConnection(Connection connection) throws DAOException{

        try {
            connectionPool.releaseConnection(connection);
        }catch (InterruptedException e){
            throw new DAOException(e);
        }
    }


}
