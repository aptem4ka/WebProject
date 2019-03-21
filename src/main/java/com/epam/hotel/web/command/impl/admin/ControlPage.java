package com.epam.hotel.web.command.impl.admin;

import com.epam.hotel.entity.Order;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.AdminService;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.SavePreviousCommand;
import com.epam.hotel.web.util.pagination.Pagination;
import com.epam.hotel.web.util.constants.StringConstants;
import com.epam.hotel.web.util.constants.URLConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * This {@link Command} implementation is used to dispatch an admin to the control page.
 *
 * @author Artsem Lashuk
 */
public class ControlPage implements Command {
    private final static Logger logger= LogManager.getLogger(ControlPage.class);
    private AdminService adminService = ServiceFactory.getInstance().getAdminService();

    /**
     * This method tries to get two lists of orders using {@link AdminService}.
     * The first list is active orders list, and the second is orders history list.
     * The content of this lists will be displayed on the control page.
     * {@link Pagination} is used to limit amount of data that will be received from the DB.
     *
     * @param req {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @throws IOException if In/Out errors occur
     * @throws ServletException if any Servlet errors occur
     * @see Order
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SavePreviousCommand.saveCommand(req);

        Pagination activeOrdersPaginator = (Pagination)req.getSession().getAttribute(StringConstants.ACTIVE_ORDER_PAGINATOR);
        Pagination needConfirmationOrdersPaginator = (Pagination)req.getSession().getAttribute(StringConstants.NEED_CONFIRM_ORDER_PAGINATOR);

        if (activeOrdersPaginator == null && needConfirmationOrdersPaginator == null){
            activeOrdersPaginator = Pagination.setupPaginator(req, StringConstants.ACTIVE_ORDER_PAGINATOR);
            needConfirmationOrdersPaginator = Pagination.setupPaginator(req, StringConstants.NEED_CONFIRM_ORDER_PAGINATOR);
        } else {
            String paginatorType = req.getParameter(StringConstants.PAGINATOR_TYPE);
            if (paginatorType!= null){
                if (paginatorType.equals(StringConstants.ACTIVE)){
                    activeOrdersPaginator = Pagination.setupPaginator(req, StringConstants.ACTIVE_ORDER_PAGINATOR);
                }
                if (paginatorType.equals(StringConstants.NEED_CONFIRM)){
                    needConfirmationOrdersPaginator = Pagination.setupPaginator(req, StringConstants.NEED_CONFIRM_ORDER_PAGINATOR);
                }
            }
        }

        List<Order> activeOrderList = null;
        List<Order> needConfirmationOrders = null;
        try {
            activeOrderList = adminService.activeOrderList(activeOrdersPaginator);
            needConfirmationOrders = adminService.needConfirmationOrderList(needConfirmationOrdersPaginator);
        }catch (ServiceException e){
            logger.warn(e);
        }

        activeOrdersPaginator.lastPageControl(activeOrderList);
        needConfirmationOrdersPaginator.lastPageControl(needConfirmationOrders);


        req.setAttribute(StringConstants.CURRENT_DATE, new Date());
        req.setAttribute(StringConstants.ACTIVE_ORDER_LIST, activeOrderList);
        req.setAttribute(StringConstants.NEED_CONFIRM_LIST, needConfirmationOrders);

        req.getRequestDispatcher(URLConstants.CONTROL_PAGE).forward(req,resp );
    }

}
