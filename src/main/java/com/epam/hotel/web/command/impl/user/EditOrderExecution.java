package com.epam.hotel.web.command.impl.user;

import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.OrderService;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.constants.StringConstants;
import com.epam.hotel.web.util.constants.URLConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

/**
 * This {@link Command} implementation is used to change DB record about specific order.
 *
 * @author Artsem Lashuk
 */
public class EditOrderExecution implements Command {
    private final static Logger logger = LogManager.getLogger(EditOrderExecution.class);
    private OrderService orderService = ServiceFactory.getInstance().getOrderService();

    /**
     * This method gets input data from the session entered on the previous page's form.
     * Calling {@link OrderService} validates data, make changes in specified DB record
     * and redirecting to the congratulations page. In case of incorrect data user will be
     * redirected to the previous page with an error message.
     *
     * @param req {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @throws IOException if In/Out errors occur
     * @throws ServletException if any Servlet errors occur
     * @see Order
     * @see User
     * @see HttpSession
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session=req.getSession();

        Order order=new Order();
        User user = (User)session.getAttribute(StringConstants.CURRENT_USER);

        order.setResFrom((Date)session.getAttribute(StringConstants.RESERVED_FROM));
        order.setResTo((Date)session.getAttribute(StringConstants.RESERVED_TO));

        double oldPrice = (double)session.getAttribute(StringConstants.OLD_PRICE);
        double difference = Double.parseDouble(req.getParameter(StringConstants.DIFFERENCE));
        double orderPrice = oldPrice+difference;

        order.setOrderID((int)session.getAttribute(StringConstants.ORDER_ID));
        order.setUserID(user.getUserID());
        order.setTotalPrice(orderPrice);
        order.setRoomID(Integer.parseInt(req.getParameter(StringConstants.ROOM_ID)));

        try {
            orderService.editOrder(order);
        }catch (ServiceException e){
            logger.warn(e);
            resp.sendRedirect(session.getAttribute(StringConstants.PREV_PAGE_URL)+"&incorrectDate=true");
        }

        session.setAttribute(StringConstants.RESERVED_FROM, order.getResFrom());
        session.setAttribute(StringConstants.RESERVED_TO, order.getResTo());
        session.setAttribute(StringConstants.DIFFERENCE, difference);

        resp.sendRedirect(URLConstants.CHANGE_ORDER_CONGRATS_PAGE_COMMAND);


    }
}
