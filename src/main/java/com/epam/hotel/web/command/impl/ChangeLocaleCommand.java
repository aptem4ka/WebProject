package com.epam.hotel.web.command.impl;

import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.constants.StringConstants;
import com.epam.hotel.web.util.constants.URLConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This {@link Command} implementation is used for changing locale
 *
 * @author Artsem Lashuk
 */
public class ChangeLocaleCommand implements Command {
    private final static Logger logger= LogManager.getLogger(ChangeLocaleCommand.class);

    /**
     * The method gets new locale from the request and put it to the session
     * as an attribute. Then it redirects client to the previous page.
     *
     * @param req {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @throws IOException if In/Out errors occur
     * @throws ServletException if any Servlet errors occur
     * @see HttpSession
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session=req.getSession(false);
        session.setAttribute(StringConstants.LOCALE, req.getParameter(StringConstants.LOCALE));

        String prevPage=(String)session.getAttribute(StringConstants.PREV_PAGE_URL);

        if (prevPage!=null){
            resp.sendRedirect(prevPage);
        }else {
            resp.sendRedirect(URLConstants.GO_TO_INDEX);
        }

    }
}
