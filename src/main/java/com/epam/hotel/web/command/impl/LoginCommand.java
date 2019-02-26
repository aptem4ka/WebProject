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


public class LoginCommand implements Command {


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user=new User();
        user.setEmail(req.getParameter("email"));
        user.setPassword(req.getParameter("password"));
        UserService service=ServiceFactory.getInstance().getUserService();

        try {
            user=service.loginUser(user);
        }catch (ServiceException e){
            //TODO error page
        }

        if (user.isValid()){
            HttpSession session=req.getSession();
            session.setAttribute("currentUser",user);

            if ((String)session.getAttribute("prevURL")!=null){
                resp.sendRedirect((String)req.getSession().getAttribute("prevURL"));
            }else {
                resp.sendRedirect("index.jsp");
            }

        }else {
            resp.sendRedirect("ControllerServlet?command=login_page");
        }


    }
}
