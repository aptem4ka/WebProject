package com.epam.hotel.web.command.impl;

import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.constants.URLConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This {@link Command} implementation is used to logout from the current session
 *
 * @author Artsem Lashuk
 */
public class Logout implements Command {
    private final static Logger logger = LogManager.getLogger(Logout.class);

    /**
     * The method gets current {@link HttpSession} from the request and invalidates it.
     * After all it redirects client to the index page.
     * @param req {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @throws IOException if In/Out errors occur
     * @throws ServletException if any Servlet errors occur
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session=req.getSession(false);
        session.invalidate();
        logger.info("Destroying session");
        resp.sendRedirect(URLConstants.GO_TO_INDEX);

      }
    }

