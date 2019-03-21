package com.epam.hotel.web.command.impl;

import com.epam.hotel.entity.User;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This {@link Command} implementation is used to sign in user by getting
 * his data from the DB.
 *
 * @author Artsem Lashuk
 */
public class Login implements Command {
    private final static Logger logger= LogManager.getLogger();
    private UserService userService=ServiceFactory.getInstance().getUserService();

    /**
     * This method collects all data received from request and calls {@link UserService}
     * which validates data and has an access to the DAO layer. If validation successful,
     * user will be added to the current HTTP session. In other case client will be
     * redirected to login page with an error message.
     *
     * @param req {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @throws IOException if In/Out errors occur
     * @throws ServletException if any Servlet errors occur
     * @see HttpSession
     */
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
            resp.sendRedirect(URLConstants.GO_TO_INDEX);

        }else {
            resp.sendRedirect(URLConstants.LOGIN_PAGE_COMMAND+"&incorrectData=true");
        }


    }
}
