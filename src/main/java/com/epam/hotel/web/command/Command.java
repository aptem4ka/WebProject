package com.epam.hotel.web.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This interface defines method for executing requests from {@link com.epam.hotel.web.ControllerServlet}
 *
 * @author Artsem Lashuk
 */

public interface Command {

    /**
     * This abstract method executes a request and sends a response back to client.
     *
     * @param req {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @throws IOException if In/Out errors occur
     * @throws ServletException if any Servlet errors occur
     *
     * @see com.epam.hotel.web.ControllerServlet
     */
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

}
