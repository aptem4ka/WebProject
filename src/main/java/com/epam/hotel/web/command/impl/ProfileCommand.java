package com.epam.hotel.web.command.impl;

import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.OrderService;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.StringConstants;
import com.epam.hotel.web.util.URLFromRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ProfileCommand implements Command {
    private OrderService orderService= ServiceFactory.getInstance().getOrderService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String prevURL = new URLFromRequest().createURL(req);
        session.setAttribute(StringConstants.PREV_PAGE_URL, prevURL);
        User user = (User)session.getAttribute("currentUser");
        try {
            List<Order> orderList=orderService.userBookingStatistics(user.getUserID());
            for (Order order:orderList){
                System.out.println(order.getOrderID());
            }
            req.setAttribute("orderList", orderList);
        }catch (ServiceException e){
            //TODO error page
        }

        req.getRequestDispatcher("/WEB-INF/jsp/Profile.jsp").forward(req,resp);

    }
}
