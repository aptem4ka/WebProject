package com.epam.hotel.dao.util;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class is used to keep available connections to the DB.
 *
 * @author Artsem Lashuk
 * @see Connection
 */
public class ConnectionPool {
    private final static Logger logger= LogManager.getLogger(ConnectionPool.class);

    private static final ConnectionPool instance = new ConnectionPool();
    /**
     * Thread-safe collection of available connections that might be taken.
     */
    private BlockingQueue<Connection> connectionsAvailable;

    /**
     * Thread-safe collection of taken connections that might be brought back.
     */
    private BlockingQueue<Connection> connectionsTaken;

    /**
     * Private constructor initializes a connection to the DB. All data is taken from
     * the resource bundle.
     */
    private ConnectionPool() {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(SQLConstants.DB_BUNDLE);
            String url = resourceBundle.getString(SQLConstants.DB_URL_KEY);
            String login = resourceBundle.getString(SQLConstants.DB_LOGIN_KEY);
            String pass = resourceBundle.getString(SQLConstants.DB_PASSWORD_KEY);
            int connectionsLimit = Integer.parseInt(resourceBundle.getString(SQLConstants.DB_CONNECTION_LIMIT_KEY));
            Class.forName(resourceBundle.getString(SQLConstants.DB_DRIVER_KEY));

            connectionsAvailable = new LinkedBlockingQueue<>(connectionsLimit);
            connectionsTaken = new LinkedBlockingQueue<>(connectionsLimit);

            for (int i=0;i<connectionsLimit;i++){
                connectionsAvailable.put(DriverManager.getConnection(url,login,pass));
            }
        }catch (ClassNotFoundException|SQLException|InterruptedException e){
            logger.warn(e); throw new RuntimeException(e);
        }
    }

    /**
     * Get instance of the connection pool
     * @return {@link ConnectionPool} instance
     */
    public static ConnectionPool getInstance() {
        return instance;
    }

    /**
     * Take one connection if there are available connections in the collection.
     * Taken connection puts at the appropriate collection.
     * @return {@link Connection}
     * @throws InterruptedException if any error occurred during taking or putting connection
     *                              to one of the collections.
     */
    public Connection takeConnection() throws InterruptedException{
        Connection connection;

            connection = connectionsAvailable.take();
            connectionsTaken.put(connection);

        if (connectionsTaken.size()==5){
            logger.warn("all connections are busy");
        }
        return connection;
    }

    /**
     * Bring connection back to the {@link #connectionsAvailable} collection
     * @param connection {@link Connection} that you want to release
     * @throws InterruptedException if any error occurred during taking or putting connection
     *                              to one of the collections.
     */
    public void releaseConnection(Connection connection) throws InterruptedException{
            if (connectionsTaken.remove(connection)) {
                connectionsAvailable.put(connection);
            } else {
                throw new InterruptedException("Release connection error");
            }
    }

    /**
     * Closing all connections from both collections.
     */
    public void closeConnections(){

       try {
           for (Connection connection : connectionsTaken) {
               connection.close();
           }

           for (Connection connection : connectionsAvailable) {
               connection.close();
           }
       }catch (SQLException e){
           logger.warn(e);
       }

    }


}
