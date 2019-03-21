package com.epam.hotel.web;

import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.command.CommandManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * This class redirects requests to the command
 *
 * @author Artsem Lashuk
 *
 * @see CommandManager
 * @see Command
 */

public class ControllerServlet extends HttpServlet {
    private final String COMMAND="command";

    /**
     * Method redirecting http-pust requests for further processing.
     *
     * @param req {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @throws IOException if In/Out Exception occurs
     * @throws ServletException if any Servlet exception occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command=CommandManager.getInstance().getCommand(req.getParameter(COMMAND));

        command.execute(req,resp);
    }

    /**
     * Method redirecting http-pust requests for further processing.
     *
     * @param req {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @throws IOException if In/Out Exception occurs
     * @throws ServletException if any other exception occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command=CommandManager.getInstance().getCommand(req.getParameter(COMMAND));

        command.execute(req,resp);

    }
}
