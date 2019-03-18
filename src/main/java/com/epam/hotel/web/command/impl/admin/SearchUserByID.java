package com.epam.hotel.web.command.impl.admin;

import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.AdminService;
import com.epam.hotel.service.OrderService;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.service.UserService;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.StringConstants;
import com.epam.hotel.web.util.URLConstants;
import com.epam.hotel.web.util.pagination.Pagination;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class SearchUserByID implements Command {
    private final static Logger logger= LogManager.getLogger(SearchUserByID.class);
    private AdminService adminService = ServiceFactory.getInstance().getAdminService();
    private OrderService orderService = ServiceFactory.getInstance().getOrderService();
    private UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = null;

            int userID = Integer.parseInt(req.getParameter(StringConstants.USER_ID));
            try {
                user = adminService.searchUserByID(userID);
            }catch (ServiceException e){
                logger.warn(e);
            }

        if (user!=null) {

            Pagination activeOrdersPaginator = (Pagination) req.getSession().getAttribute(StringConstants.ACTIVE_PAGINATOR);
            Pagination ordersHistoryPaginator = (Pagination) req.getSession().getAttribute(StringConstants.HISTORY_PAGINATOR);

            if (activeOrdersPaginator == null && ordersHistoryPaginator == null) {
                activeOrdersPaginator = Pagination.setupPaginator(req, StringConstants.ACTIVE_PAGINATOR);
                ordersHistoryPaginator = Pagination.setupPaginator(req, StringConstants.HISTORY_PAGINATOR);
            } else {
                String paginatorType = req.getParameter(StringConstants.PAGINATOR_TYPE);
                if (paginatorType != null) {
                    if (paginatorType.equals(StringConstants.ACTIVE)) {
                        activeOrdersPaginator = Pagination.setupPaginator(req, StringConstants.ACTIVE_PAGINATOR);
                    }
                    if (paginatorType.equals(StringConstants.HISTORY)) {
                        ordersHistoryPaginator = Pagination.setupPaginator(req, StringConstants.HISTORY_PAGINATOR);
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

            req.setAttribute(StringConstants.USER, user);
            req.setAttribute(StringConstants.SEARCHER_COMMAND,StringConstants.SEARCH_USER_BY_ID);
            req.setAttribute(StringConstants.ACTIVE_ORDER_LIST, activeOrderList);
            req.setAttribute(StringConstants.HISTORY_ORDER_LIST, orderHistoryList);
            req.setAttribute(StringConstants.CURRENT_DATE, new Date());

            req.getRequestDispatcher(URLConstants.USER_SEARCH_PAGE).forward(req, resp);

        }else {
            req.setAttribute(StringConstants.USER, user);
            req.getRequestDispatcher(URLConstants.USER_SEARCH_PAGE).forward(req, resp);
        }



    }


}
