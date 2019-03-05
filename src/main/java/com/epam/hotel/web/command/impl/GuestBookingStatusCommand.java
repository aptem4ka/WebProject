package com.epam.hotel.web.command.impl;

import com.epam.hotel.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GuestBookingStatusCommand implements Command {

    //TEMPORARY TEST METHOD
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/jsp/GuestBookingStatus.jsp").forward(req,resp);
    }
}
