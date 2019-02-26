package com.epam.hotel.web.command.impl;

import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.StringConstants;
import com.epam.hotel.web.util.URLConstants;

import javax.print.DocFlavor;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChangeLocaleCommand implements Command {
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
