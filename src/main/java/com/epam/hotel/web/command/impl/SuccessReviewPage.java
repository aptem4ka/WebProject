package com.epam.hotel.web.command.impl;

import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.SavePreviousCommand;
import com.epam.hotel.web.util.constants.URLConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This {@link Command} implementation is used to dispatch client to the congratulations page
 * after leaving feedback
 *
 * @author Artsem Lashuk
 */
public class SuccessReviewPage implements Command {
    private final static Logger logger = LogManager.getLogger(SuccessReviewPage.class);

    /**
     * The method saves previous command to the HTTP session and dispatches
     * client to the congratulations page.
     *
     * @param req {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @throws IOException if In/Out errors occur
     * @throws ServletException if any Servlet errors occur
     * @see SavePreviousCommand#saveCommand(HttpServletRequest)
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SavePreviousCommand.saveCommand(req);

        req.getRequestDispatcher(URLConstants.SUCCESS_REVIEW_PAGE).forward(req, resp);
    }
}
