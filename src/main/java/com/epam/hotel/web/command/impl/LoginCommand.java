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


public class LoginCommand implements Command {
    private UserService userService=ServiceFactory.getInstance().getUserService();


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user=new User();
        user.setEmail(req.getParameter(StringConstants.EMAIL));
        user.setPassword(req.getParameter(StringConstants.PASSWORD));

        try {
            user=userService.loginUser(user);
            int discount = userService.userDiscount(user.getUserID());
            if (discount<=10){
                user.setDiscount(discount);
            }else {
                user.setDiscount(10);
            }
        }catch (ServiceException e){
            //TODO error page
        }

        if (user.isValid()){
            HttpSession session=req.getSession();
            session.setAttribute(StringConstants.CURRENT_USER,user);

            if ((String)session.getAttribute(StringConstants.PREV_PAGE_URL)!=null){
                resp.sendRedirect((String)req.getSession().getAttribute(StringConstants.PREV_PAGE_URL));
            }else {
                resp.sendRedirect(URLConstants.GO_TO_INDEX);
            }

        }else {
            resp.sendRedirect(URLConstants.LOGIN_PAGE_COMMAND);
        }


    }
}
