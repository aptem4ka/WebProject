package com.epam.hotel.web.command.impl;

import com.epam.hotel.entity.Order;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.AdminService;
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
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ControlCommand implements Command {
    private final static Logger logger= LogManager.getLogger(ControlCommand.class);
    private AdminService adminService = ServiceFactory.getInstance().getAdminService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String prevURL = new URLFromRequest().createURL(req);
        req.getSession().setAttribute(StringConstants.PREV_PAGE_URL, prevURL);

        List<Order> orderList = null;
        try {
            orderList = adminService.activeOrderList();
        }catch (ServiceException e){
            logger.warn(e);
        }
        req.setAttribute(StringConstants.CURRENT_DATE, new Date());
        req.setAttribute(StringConstants.ORDER_LIST, orderList);

            req.getRequestDispatcher(URLConstants.CONTROL_PAGE).forward(req,resp );


    }

}
