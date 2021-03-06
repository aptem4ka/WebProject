package com.epam.hotel.web.command.impl;

import com.epam.hotel.entity.RegistrationForm;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.service.UserService;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.constants.StringConstants;
import com.epam.hotel.web.util.constants.URLConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This {@link Command} implementation is used to register user by saving
 * his data in the DB.
 *
 * @author Artsem Lashuk
 */
public class Register implements Command {
    private UserService userService =ServiceFactory.getInstance().getUserService();
    private final static Logger logger = LogManager.getLogger(Register.class);

    /**
     * This method collects all data received from request and calls {@link UserService}
     * which validates data and has an access to the DAO layer. If validation fails,
     * all errors will be collected in the string and added to the response.
     * @param req {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @throws IOException if In/Out errors occur
     * @throws ServletException if any Servlet errors occur
     */
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
               resp.sendRedirect((URLConstants.REGISTER_CONGRATS_PAGE_COMMAND));
           }else {
               resp.sendRedirect(URLConstants.RETRY_REGISTRATION_COMMAND + registerErrors);
           }

        }catch (ServiceException e){
           logger.warn(e);
       }
    }

}
