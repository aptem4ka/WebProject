package com.epam.hotel.web.command.impl;

import com.epam.hotel.entity.User;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.service.UserService;
import com.epam.hotel.web.command.Command;

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


           user.setEmail(req.getParameter("email"));
           user.setName(req.getParameter("name"));
           user.setSurname(req.getParameter("surname"));
           user.setPassword(req.getParameter("password"));
           user.setPhone(req.getParameter("phone"));
        try {
           confirmRegistration=service.checkRegistrationForm(user,req);

           if (confirmRegistration){
               service.registerUser(user);
               req.getSession().setAttribute("currentUser",user);
               resp.sendRedirect((String)req.getSession().getAttribute("prevURL"));
           }else {
               String formErrors=(String)req.getAttribute("formErrors");
               resp.sendRedirect("ControllerServlet?command=register_page&"+formErrors);
           }

        }catch (ServiceException e){
           //TODO error page
       }
    }

}
