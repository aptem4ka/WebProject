package com.epam.hotel.web.command.impl;

import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.AdminService;
import com.epam.hotel.service.OrderService;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.service.UserService;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.StringConstants;
import com.epam.hotel.web.util.pagination.Pagination;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class SearchUserByIDCommand implements Command {
    private final static Logger logger= LogManager.getLogger(SearchUserByIDCommand.class);
    private AdminService adminService = ServiceFactory.getInstance().getAdminService();
    private OrderService orderService = ServiceFactory.getInstance().getOrderService();
    private UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        User user = null;

            int userID = Integer.parseInt(req.getParameter("userID"));
            try {
                user = adminService.searchUserByID(userID);
            }catch (ServiceException e){
                logger.warn(e);
            }

        if (user!=null) {

            Pagination activeOrdersPaginator = (Pagination) req.getSession().getAttribute("activePaginator");
            Pagination ordersHistoryPaginator = (Pagination) req.getSession().getAttribute("historyPaginator");

            if (activeOrdersPaginator == null && ordersHistoryPaginator == null) {
                activeOrdersPaginator = Pagination.setupPaginator(req, "activePaginator");
                ordersHistoryPaginator = Pagination.setupPaginator(req, "historyPaginator");
            } else {
                String paginatorType = req.getParameter("paginatorType");
                if (paginatorType != null) {
                    if (paginatorType.equals("active")) {
                        activeOrdersPaginator = Pagination.setupPaginator(req, "activePaginator");
                    }
                    if (paginatorType.equals("history")) {
                        ordersHistoryPaginator = Pagination.setupPaginator(req, "historyPaginator");
                    }
                }
            }

            List<Order> activeOrderList = null;
            List<Order> orderHistoryList = null;

            try {
                activeOrderList = userService.activeOrderList(activeOrdersPaginator, user.getUserID());
                orderHistoryList = userService.orderHistoryList(ordersHistoryPaginator, user.getUserID());
            } catch (ServiceException e) {
                logger.warn(e);
            }


            activeOrdersPaginator.lastPageControl(activeOrderList);
            ordersHistoryPaginator.lastPageControl(orderHistoryList);
            req.setAttribute("user", user);
            req.setAttribute("searcherCommand", "search_user_by_id");
            req.setAttribute(StringConstants.ACTIVE_ORDER_LIST, activeOrderList);
            req.setAttribute("historyOrderList", orderHistoryList);
            req.setAttribute(StringConstants.CURRENT_DATE, new Date());

            req.getRequestDispatcher("/WEB-INF/jsp/UserSearch.jsp").forward(req, resp);

        }else {
            req.setAttribute("user", user);
            req.getRequestDispatcher("/WEB-INF/jsp/UserSearch.jsp").forward(req, resp);
        }



    }


}
