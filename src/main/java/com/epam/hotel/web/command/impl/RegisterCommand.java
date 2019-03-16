package com.epam.hotel.web.command.impl;

import com.epam.hotel.entity.RegistrationForm;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.service.UserService;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.StringConstants;
import com.epam.hotel.web.util.URLConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterCommand implements Command {
    private UserService userService =ServiceFactory.getInstance().getUserService();
    private final static Logger logger = LogManager.getLogger(RegisterCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String registerErrors="";
            RegistrationForm form=new RegistrationForm();

            form.setEmail(req.getParameter(StringConstants.EMAIL));
            form.setName(req.getParameter(StringConstants.NAME));
            form.setSurname(req.getParameter(StringConstants.SURNAME));
            form.setPassword(req.getParameter(StringConstants.PASSWORD));
            form.setPhone(req.getParameter(StringConstants.PHONE));
            form.setConfirmPassword(req.getParameter(StringConstants.REPEAT_PASSWORD));

        try {
           registerErrors = userService.checkRegistrationForm(form);

           if (registerErrors.equals("")){
               userService.registerUser(form);
               resp.sendRedirect((URLConstants.GO_TO_INDEX));
           }else {
               resp.sendRedirect(URLConstants.RETRY_REGISTRATION_COMMAND + registerErrors);
           }

        }catch (ServiceException e){
           logger.warn(e);
       }
    }

}
