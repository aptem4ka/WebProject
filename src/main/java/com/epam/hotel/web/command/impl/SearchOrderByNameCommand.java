package com.epam.hotel.web.command.impl;

import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.AdminService;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.StringConstants;
import com.epam.hotel.web.util.URLFromRequest;
import com.epam.hotel.web.util.pagination.Pagination;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchOrderByNameCommand implements Command {
    private final static Logger logger= LogManager.getLogger(SearchOrderByNameCommand.class);
    private AdminService adminService = ServiceFactory.getInstance().getAdminService();
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String prevURL = new URLFromRequest().createURL(req);
        HttpSession session = req.getSession();
        session.setAttribute(StringConstants.PREV_PAGE_URL, prevURL);

        Pagination ordersPaginator = Pagination.setupPaginator(req, "ordersPaginator");



        String name = req.getParameter("name");
        String surname = req.getParameter("surname");

        List<Order> orderList = new ArrayList<>();


        try {
            orderList = adminService.searchOrderByName(name, surname);
        } catch (ServiceException e){
            logger.warn(e);
        }
        Collections.reverse(orderList);



    }
}
