package com.epam.hotel.web.command.impl;

import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.SavePreviousCommand;
import com.epam.hotel.web.util.constants.URLConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This {@link Command} implementation is used to dispatch client to the about page.
 *
 * @author Artsem Lashuk
 */
public class AboutPageCommand implements Command {

    /**
     * This method saves previous command by calling {@link SavePreviousCommand#saveCommand(HttpServletRequest)}
     * and dispatches client to the about page.
     *
     * @param req {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @throws IOException if In/Out errors occur
     * @throws ServletException if any Servlet errors occur
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SavePreviousCommand.saveCommand(req);

        req.getRequestDispatcher(URLConstants.ABOUT_PAGE).forward(req, resp);
    }
}
