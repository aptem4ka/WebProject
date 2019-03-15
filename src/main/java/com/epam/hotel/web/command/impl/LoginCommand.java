package com.epam.hotel.web.command.impl;

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
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class LoginCommand implements Command {
    private final static Logger logger= LogManager.getLogger();
    private UserService userService=ServiceFactory.getInstance().getUserService();


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user=new User();
        user.setEmail(req.getParameter(StringConstants.EMAIL));
        user.setPassword(req.getParameter(StringConstants.PASSWORD));

        try {
            user=userService.loginUser(user);
            user.setDiscount(userService.userDiscount(user.getUserID()));
        }catch (ServiceException e){
            logger.warn(e);
        }

        if (user.isValid()){
            logger.info("adding user to the session");
            HttpSession session=req.getSession();
            session.setAttribute(StringConstants.CURRENT_USER,user);

            if (session.getAttribute(StringConstants.PREV_PAGE_URL)!=null){
                resp.sendRedirect((String)req.getSession().getAttribute(StringConstants.PREV_PAGE_URL));
            }else {
                resp.sendRedirect(URLConstants.GO_TO_INDEX);
            }

        }else {
            resp.sendRedirect(URLConstants.LOGIN_PAGE_COMMAND);
        }


    }
}
