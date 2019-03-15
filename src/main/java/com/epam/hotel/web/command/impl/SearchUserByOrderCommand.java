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
import com.epam.hotel.web.util.URLFromRequest;
import com.epam.hotel.web.util.pagination.Pagination;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Provider;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SearchUserByOrderCommand implements Command {
    private final static Logger logger = LogManager.getLogger(SearchUserByOrderCommand.class);
    private AdminService adminService = ServiceFactory.getInstance().getAdminService();
    private OrderService orderService = ServiceFactory.getInstance().getOrderService();
    private UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int orderID = 0;
        User user = null;

            if (req.getParameter("orderID") != null) {
                orderID = Integer.parseInt(req.getParameter("orderID"));
            }
            try {
                user = adminService.searchUserByOrder(orderID);
            } catch (ServiceException e) {
                logger.warn(e);
            }

            if (user == null || user.getUserID()==0) {
                req.setAttribute("user", user);
                req.getRequestDispatcher("/WEB-INF/jsp/UserSearch.jsp").forward(req, resp);

            } else {

                Pagination activeOrdersPaginator = (Pagination) req.getSession().getAttribute("searchByOrderActivePaginator");
                Pagination ordersHistoryPaginator = (Pagination) req.getSession().getAttribute("searchByOrderHistoryPaginator");

                if (activeOrdersPaginator == null && ordersHistoryPaginator == null) {
                    activeOrdersPaginator = Pagination.setupPaginator(req, "searchByOrderActivePaginator");
                    ordersHistoryPaginator = Pagination.setupPaginator(req, "searchByOrderHistoryPaginator");
                } else {
                    String paginatorType = req.getParameter("paginatorType");
                    if (paginatorType != null) {
                        if (paginatorType.equals("active")) {
                            activeOrdersPaginator = Pagination.setupPaginator(req, "searchByOrderActivePaginator");
                        }
                        if (paginatorType.equals("history")) {
                            ordersHistoryPaginator = Pagination.setupPaginator(req, "searchByOrderHistoryPaginator");
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

                req.setAttribute("orderID", orderID);
                req.setAttribute("user", user);
                req.setAttribute("searcherCommand", "search_user_by_order");
                req.setAttribute(StringConstants.ACTIVE_ORDER_LIST, activeOrderList);
                req.setAttribute("historyOrderList", orderHistoryList);
                req.setAttribute(StringConstants.CURRENT_DATE, new Date());

                req.getRequestDispatcher("/WEB-INF/jsp/UserSearch.jsp").forward(req, resp);
                }


            }
        }

