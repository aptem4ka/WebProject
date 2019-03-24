package com.epam.hotel.web.command.impl;

import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.SavePreviousCommand;
import com.epam.hotel.web.util.constants.URLConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This {@link Command} implementation send user to the congratulation page
 * after successful registration.
 *
 * @author Artsem Lashuk
 */
public class RegisterCongratsPage implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SavePreviousCommand.saveCommand(req);
        req.getRequestDispatcher(URLConstants.REGISTER_CONGRATS_PAGE).forward(req,resp);
    }
}
