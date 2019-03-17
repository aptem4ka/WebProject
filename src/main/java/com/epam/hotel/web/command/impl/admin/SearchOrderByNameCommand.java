package com.epam.hotel.web.command.impl.admin;

import com.epam.hotel.entity.Order;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.AdminService;
import com.epam.hotel.service.ServiceFactory;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchOrderByNameCommand implements Command {
    private final static Logger logger= LogManager.getLogger(SearchOrderByNameCommand.class);
    private AdminService adminService = ServiceFactory.getInstance().getAdminService();
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Pagination ordersPaginator = Pagination.setupPaginator(req, StringConstants.ORDERS_PAGINATOR);

        String name = req.getParameter(StringConstants.NAME);
        String surname = req.getParameter(StringConstants.SURNAME);

        List<Order> orderList = new ArrayList<>();

        try {
            orderList = adminService.searchOrderByName(name, surname, ordersPaginator );
        } catch (ServiceException e){
            logger.warn(e);
        }

        ordersPaginator.lastPageControl(orderList);

        req.setAttribute(StringConstants.ORDER_LIST, orderList);
        req.setAttribute(StringConstants.CURRENT_DATE, new Date());
        req.setAttribute(StringConstants.NAME, name);
        req.setAttribute(StringConstants.SURNAME, surname);
        req.getRequestDispatcher(URLConstants.ORDER_SEARCH_PAGE).forward(req, resp);

    }
}
