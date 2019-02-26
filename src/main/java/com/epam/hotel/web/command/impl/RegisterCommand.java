package com.epam.hotel.web.command.impl;

import com.epam.hotel.entity.User;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.service.UserService;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.StringConstants;
import com.epam.hotel.web.util.URLConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegisterCommand implements Command {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            User user=new User();
            UserService service=ServiceFactory.getInstance().getUserService();
            boolean confirmRegistration;

           user.setEmail(req.getParameter(StringConstants.EMAIL));
           user.setName(req.getParameter(StringConstants.NAME));
           user.setSurname(req.getParameter(StringConstants.SURNAME));
           user.setPassword(req.getParameter(StringConstants.PASSWORD));

           if (req.getParameter(StringConstants.PHONE)!=null){
               user.setPhone(req.getParameter(StringConstants.PHONE));
           }

        try {
           confirmRegistration=service.checkRegistrationForm(user,req);

           if (confirmRegistration){
               service.registerUser(user);
               req.getSession().setAttribute(StringConstants.CURRENT_USER, user);
               resp.sendRedirect((String)req.getSession().getAttribute(StringConstants.PREV_PAGE_URL));
           }else {
               String formErrors=(String)req.getAttribute(StringConstants.REGISTRATION_FORM_ERRORS);
               resp.sendRedirect(URLConstants.RETRY_REGISTRATION_COMMAND +formErrors);
           }

        }catch (ServiceException e){
           //TODO error page
       }
    }

}
