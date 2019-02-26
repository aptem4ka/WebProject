package com.epam.hotel.web.command.impl;

import com.epam.hotel.web.command.Command;

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
        session.setAttribute("locale",req.getParameter("locale"));

        String prevPage=(String)session.getAttribute("prevURL");

        if (prevPage!=null){
            resp.sendRedirect(prevPage);
        }else {
            resp.sendRedirect("index.jsp");
        }

    }
}
