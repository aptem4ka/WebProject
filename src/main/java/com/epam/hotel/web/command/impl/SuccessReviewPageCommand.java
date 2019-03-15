package com.epam.hotel.web.command.impl;

import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.StringConstants;
import com.epam.hotel.web.util.URLFromRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SuccessReviewPageCommand implements Command {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String prevURL = new URLFromRequest().createURL(req);
        session.setAttribute(StringConstants.PREV_PAGE_URL, prevURL);

        req.getRequestDispatcher("/WEB-INF/jsp/ReviewSuccessPage.jsp").forward(req, resp);
    }
}
