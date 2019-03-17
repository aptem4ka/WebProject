package com.epam.hotel.web.util;

import com.epam.hotel.dao.util.ConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("closing app");
        ConnectionPool.getInstance().closeConnections();
    }
}
