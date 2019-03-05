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

public class ConnectionPool {
    private final static Logger logger= LogManager.getLogger(ConnectionPool.class);

    private static final ConnectionPool instance = new ConnectionPool();
    private Lock lock = new ReentrantLock();
    private BlockingQueue<Connection> connectionsAvailable;
    private BlockingQueue<Connection> connectionsTaken;

    private ConnectionPool() {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("DBConnection");
            String url = resourceBundle.getString("database.url");
            String login = resourceBundle.getString("database.login");
            String pass = resourceBundle.getString("database.password");
            int connectionsLimit = Integer.parseInt(resourceBundle.getString("database.connectionsLimit"));
            Class.forName(resourceBundle.getString("database.driver"));

            connectionsAvailable = new LinkedBlockingQueue<>(connectionsLimit);
            connectionsTaken = new LinkedBlockingQueue<>(connectionsLimit);

            for (int i=0;i<connectionsLimit;i++){
                connectionsAvailable.put(DriverManager.getConnection(url,login,pass));
            }
        }catch (ClassNotFoundException|SQLException|InterruptedException e){
            //TODO log
        }
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    public void init()throws ClassNotFoundException, SQLException, InterruptedException {


    }

    public Connection takeConnection() throws InterruptedException{
        Connection connection;
        lock.lock();

        try {
            connection = connectionsAvailable.take();
            connectionsTaken.put(connection);
        }finally {
            lock.unlock();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) throws InterruptedException{
        lock.lock();
        try {
            if (connectionsTaken.remove(connection)) {
                connectionsAvailable.put(connection);
            } else {
                throw new InterruptedException("Release connection error");
            }
        }finally {
            lock.unlock();
        }
    }


}
