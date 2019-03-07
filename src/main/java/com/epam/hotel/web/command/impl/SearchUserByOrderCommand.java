package com.epam.hotel.web.command.impl;

import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.AdminService;
import com.epam.hotel.service.OrderService;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Provider;
import java.util.Date;
import java.util.List;

public class SearchUserByOrderCommand implements Command {
    private final static Logger logger= LogManager.getLogger(SearchUserByOrderCommand.class);
    private AdminService adminService = ServiceFactory.getInstance().getAdminService();
    private OrderService orderService = ServiceFactory.getInstance().getOrderService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int orderID = 0;
        User user = null;
        List<Order> orderList = null;
        if (req.getParameter("orderID")!=null){
            orderID = Integer.parseInt(req.getParameter("orderID"));
        }

        try {
            user = adminService.searchUserByOrder(orderID);
        }catch (ServiceException e){
            logger.warn(e);
        }

        if (user.getUserID()!=0){
            try {
                orderList = orderService.userBookingStatistics(user.getUserID());
            }catch (ServiceException e){
                logger.warn(e);
            }
        }

        req.setAttribute("orderList", orderList);
        req.setAttribute("currentDate", new Date());
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/jsp/UserSearch.jsp").forward(req,resp);



    }
}
