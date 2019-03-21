package com.epam.hotel.web.command.impl.admin;

import com.epam.hotel.entity.Order;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.AdminService;
import com.epam.hotel.service.ServiceFactory;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This {@link Command} implementation is used to search specified order by phone.
 *
 * @author Artsem Lashuk
 */
public class SearchOrderByPhone implements Command {
    private final static Logger logger= LogManager.getLogger(SearchOrderByPhone.class);
    private AdminService adminService = ServiceFactory.getInstance().getAdminService();

    /**
     * This method is used to search orders by phone number got from the request.
     * The {@link List} of orders save in the request as an attribute.
     * {@link Pagination} is used to limit amount of data that will be received from the DB.
     * Then response dispatches to the client.
     *
     * @param req {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @throws IOException if In/Out errors occur
     * @throws ServletException if any Servlet errors occur
     * @see Pagination#setupPaginator(HttpServletRequest, String)
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String phone = req.getParameter(StringConstants.PHONE);

        Pagination pagination = Pagination.setupPaginator(req, StringConstants.ORDERS_PAGINATOR);

        List<Order> orderList = new ArrayList<>();

        try {
            orderList = adminService.searchOrderByPhone(phone, pagination);
        }catch (ServiceException e){
            logger.warn(e);
        }

        pagination.lastPageControl(orderList);

        req.setAttribute(StringConstants.ORDER_LIST, orderList);
        req.setAttribute(StringConstants.CURRENT_DATE, new Date());
        req.setAttribute(StringConstants.PHONE, phone);

        req.getRequestDispatcher(URLConstants.ORDER_SEARCH_PAGE).forward(req, resp);


    }
}
