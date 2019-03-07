package com.epam.hotel.web.command.impl;

import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.OrderService;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.StringConstants;
import com.epam.hotel.web.util.URLConstants;
import com.epam.hotel.web.util.URLFromRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ProfileCommand implements Command {
    private OrderService orderService= ServiceFactory.getInstance().getOrderService();
    private final static Logger logger = LogManager.getLogger(ProfileCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String prevURL = new URLFromRequest().createURL(req);
        List<Order> orderList = null;

        HttpSession session = req.getSession();
        session.setAttribute(StringConstants.PREV_PAGE_URL, prevURL);
        User user = (User)session.getAttribute(StringConstants.CURRENT_USER);

        try {
           orderList = orderService.userBookingStatistics(user.getUserID());
        }catch (ServiceException e){
            logger.warn(e);
        }

        req.setAttribute(StringConstants.CURRENT_DATE, new Date());
        req.setAttribute(StringConstants.ORDER_LIST, orderList);

        req.getRequestDispatcher(URLConstants.PROFILE_PAGE).forward(req,resp);

    }
}
