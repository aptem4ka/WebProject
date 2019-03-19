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

public class BookCommand implements Command {
    private OrderService orderService= ServiceFactory.getInstance().getOrderService();
    private final static Logger logger= LogManager.getLogger(BookCommand.class);


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Order order=new Order();
        int formNumber = Integer.parseInt(req.getParameter("formNumber"));
        HttpSession session=req.getSession();

        User user = (User)session.getAttribute(StringConstants.CURRENT_USER);
        User guest = new User();


            order.setResFrom((Date)session.getAttribute("resFrom"));
            order.setResTo((Date)session.getAttribute("resTo"));
            order.setTotalPrice(Double.parseDouble(req.getParameter("total_price"+formNumber)));
            order.setRoomID(Integer.parseInt(req.getParameter("roomID"+formNumber)));


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
            resp.sendRedirect(session.getAttribute(StringConstants.PREV_PAGE_URL)+"&incorrectData=true");

        }


    }
}
