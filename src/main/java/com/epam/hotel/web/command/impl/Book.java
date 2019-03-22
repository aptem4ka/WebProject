package com.epam.hotel.web.command.impl;

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
 * This {@link Command} implementation is used to make an order.
 *
 * @author Artsem Lashuk
 */
public class Book implements Command {
    private OrderService orderService= ServiceFactory.getInstance().getOrderService();
    private final static Logger logger= LogManager.getLogger(Book.class);


    /**
     * The method gets input information from the request and then call {@link OrderService}
     * which validates date and has an access to the DAO layer. In case of successful validation
     * data will be added to the DB using DAO layer. In other case client will be redirected
     * to the previous page with an error message.
     *
     * @param req {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @throws IOException if In/Out errors occur
     * @throws ServletException if any Servlet errors occur
     * @see User
     * @see Order
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Order order=new Order();
        int formNumber = Integer.parseInt(req.getParameter("formNumber"));
        HttpSession session=req.getSession();

        User user = (User)session.getAttribute(StringConstants.CURRENT_USER);
        User guest = new User();

        order.setResFrom((Date)session.getAttribute(StringConstants.RESERVED_FROM));
        order.setResTo((Date)session.getAttribute(StringConstants.RESERVED_TO));
        order.setTotalPrice(Double.parseDouble(req.getParameter(StringConstants.TOTAL_PRICE+formNumber)));
        order.setRoomID(Integer.parseInt(req.getParameter(StringConstants.ROOM_ID+formNumber)));

        try {
            if (user!=null){
                order.setUserID(user.getUserID());
                order = orderService.registeredUserBooking(order);
            }else {
                guest.setName(req.getParameter(StringConstants.NAME));
                guest.setSurname(req.getParameter(StringConstants.SURNAME));
                guest.setPhone(req.getParameter(StringConstants.PHONE));
                order = orderService.unregisteredUserBooking(order, guest);

            }
            session.setAttribute(StringConstants.ORDER, order);
            resp.sendRedirect(URLConstants.ORDER_DETAILS_COMMAND);

        }catch (ServiceException e){
            logger.warn(e);
            //resp.sendRedirect(session.getAttribute(StringConstants.PREV_PAGE_URL)+"&incorrectData=true");

        }


    }
}
