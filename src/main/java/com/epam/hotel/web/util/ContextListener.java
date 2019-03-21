package com.epam.hotel.web.util;

import com.epam.hotel.dao.util.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * This {@link ServletContextListener} implementation is used to inform
 * about initialization and destroying context and to close DB connections.
 *
 * @author Artsem Lashuk
 */
public class ContextListener implements ServletContextListener {
    private final static Logger logger = LogManager.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("context initialized");
    }

    /**
     * This method close DB connection if context is being destroyed.
     *
     * @param sce {@link ServletContextEvent}
     * @see ConnectionPool#closeConnections()
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("closing connections");
        ConnectionPool.getInstance().closeConnections();
    }
}
