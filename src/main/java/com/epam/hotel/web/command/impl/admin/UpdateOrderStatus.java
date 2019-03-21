package com.epam.hotel.web.command.impl.admin;

import com.epam.hotel.entity.Order;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.AdminService;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.constants.StringConstants;
import com.epam.hotel.web.util.constants.URLConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This {@link Command} implementation is used to apply or discard an order.
 *
 * @author Artsem Lashuk
 */
public class UpdateOrderStatus implements Command {
    private final static Logger logger = LogManager.getLogger(UpdateOrderStatus.class);
    private AdminService adminService = ServiceFactory.getInstance().getAdminService();

    /**
     * This method gets order data from the request and new order status
     * which will be applied to the specified order.
     *
     * @param req {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @throws IOException if In/Out errors occur
     * @throws ServletException if any Servlet errors occur
     * @see Order
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Order order = new Order();
        order.setOrderID(Integer.parseInt(req.getParameter(StringConstants.ORDER_ID)));
        order.setStatus(Order.Status.valueOf(req.getParameter(StringConstants.UPDATE_TYPE).toUpperCase()));
        order.setComment(req.getParameter(StringConstants.COMMENT));
        try {
            adminService.updateOrderStatus(order);
        }catch (ServiceException e){
            logger.warn(e);
        }
        resp.sendRedirect(URLConstants.CONTROL_COMMAND);
    }
}
