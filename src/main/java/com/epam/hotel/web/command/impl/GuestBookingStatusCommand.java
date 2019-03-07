package com.epam.hotel.web.command.impl;

import com.epam.hotel.web.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GuestBookingStatusCommand implements Command {
    private final static Logger logger= LogManager.getLogger(GuestBookingStatusCommand.class);

    //TEMPORARY TEST METHOD
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/jsp/GuestBookingStatus.jsp").forward(req,resp);
    }
}
