package com.epam.hotel.web.command.impl;

import com.epam.hotel.entity.Order;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.AdminService;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.StringConstants;
import com.epam.hotel.web.util.URLConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateOrderStatusCommand implements Command {
    AdminService adminService = ServiceFactory.getInstance().getAdminService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Order order = new Order();
        order.setOrderID(Integer.parseInt(req.getParameter(StringConstants.ORDER_ID)));
        order.setStatus(Order.Status.valueOf(req.getParameter(StringConstants.UPDATE_TYPE).toUpperCase()));
        order.setComment(req.getParameter(StringConstants.COMMENT));
        try {
            adminService.updateOrderStatus(order);
        }catch (ServiceException e){
            //TODO error page
        }
        resp.sendRedirect((String)req.getSession().getAttribute(StringConstants.PREV_PAGE_URL));


    }
}
