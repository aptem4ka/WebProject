package com.epam.hotel.web.command.impl.admin;

import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.AdminService;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.service.UserService;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.constants.StringConstants;
import com.epam.hotel.web.util.constants.URLConstants;
import com.epam.hotel.web.util.pagination.Pagination;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * This {@link Command} implementation is used to search user data by order ID.
 *
 * @author Artsem Lashuk
 * @see User
 * @see Order
 */
public class SearchUserByOrder implements Command {
    private final static Logger logger = LogManager.getLogger(SearchUserByOrder.class);
    private AdminService adminService = ServiceFactory.getInstance().getAdminService();
    private UserService userService = ServiceFactory.getInstance().getUserService();

    /**
     * This method is used to search user profile by specified {@link Order#getOrderID()}.
     * In case of success search admin will be dispatched to the user profile.
     * {@link Pagination} is used to limit amount of data that will be received from the DB.
     * Then response dispatches to the client.
     *
     * @param req {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @throws IOException if In/Out errors occur
     * @throws ServletException if any Servlet errors occur
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int orderID = 0;
        User user = null;
        String stringOrderID = req.getParameter(StringConstants.ORDER_ID);
            if (stringOrderID != null) {
                orderID = Integer.parseInt(stringOrderID);
            }
            try {
                user = adminService.searchUserByOrder(orderID);
            } catch (ServiceException e) {
                logger.warn(e);
            }

            if (user == null || user.getUserID()==0) {
                req.setAttribute(StringConstants.USER, user);
                req.getRequestDispatcher(URLConstants.USER_SEARCH_PAGE).forward(req, resp);

            } else {

                Pagination activeOrdersPaginator = (Pagination) req.getSession().getAttribute(StringConstants.SEARCH_BY_ORDER_ACTIVE_PAGINATOR);
                Pagination ordersHistoryPaginator = (Pagination) req.getSession().getAttribute(StringConstants.SEARCH_BY_ORDER_HISTORY_PAGINATOR);

                if (activeOrdersPaginator == null && ordersHistoryPaginator == null) {
                    activeOrdersPaginator = Pagination.setupPaginator(req, StringConstants.SEARCH_BY_ORDER_ACTIVE_PAGINATOR);
                    ordersHistoryPaginator = Pagination.setupPaginator(req, StringConstants.SEARCH_BY_ORDER_HISTORY_PAGINATOR);
                } else {
                    String paginatorType = req.getParameter(StringConstants.PAGINATOR_TYPE);
                    if (paginatorType != null) {
                        if (paginatorType.equals(StringConstants.ACTIVE)) {
                            activeOrdersPaginator = Pagination.setupPaginator(req, StringConstants.SEARCH_BY_ORDER_ACTIVE_PAGINATOR);
                        }
                        if (paginatorType.equals(StringConstants.HISTORY)) {
                            ordersHistoryPaginator = Pagination.setupPaginator(req, StringConstants.SEARCH_BY_ORDER_HISTORY_PAGINATOR);
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

                req.setAttribute(StringConstants.ORDER_ID, orderID);
                req.setAttribute(StringConstants.USER, user);
                req.setAttribute(StringConstants.SEARCHER_COMMAND, StringConstants.SEARCH_USER_BY_ORDER);
                req.setAttribute(StringConstants.ACTIVE_ORDER_LIST, activeOrderList);
                req.setAttribute(StringConstants.HISTORY_ORDER_LIST, orderHistoryList);
                req.setAttribute(StringConstants.CURRENT_DATE, new Date());

                req.getRequestDispatcher(URLConstants.USER_SEARCH_PAGE).forward(req, resp);
                }
            }
        }

