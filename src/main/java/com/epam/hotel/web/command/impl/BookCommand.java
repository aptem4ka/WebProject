package com.epam.hotel.web.command.impl;

import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.OrderService;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.StringConstants;
import com.epam.hotel.web.util.URLConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BookCommand implements Command {
    private OrderService orderService= ServiceFactory.getInstance().getOrderService();
    private final static Logger logger= LogManager.getLogger(BookCommand.class);


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Order order=new Order();
        int formNumber = Integer.parseInt(req.getParameter("formNumber"));
        HttpSession session=req.getSession();
        SimpleDateFormat dateFormat = new SimpleDateFormat(StringConstants.REQUEST_DATE_FORMAT);
        User user = (User)session.getAttribute(StringConstants.CURRENT_USER);
        User guest = new User();
        try {
            order.setResFrom(dateFormat.parse(req.getParameter(StringConstants.RESERVED_FROM)));
            order.setResTo(dateFormat.parse(req.getParameter(StringConstants.RESERVED_TO)));
        }catch (ParseException e) {
            logger.warn(e);
        }

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
            resp.sendRedirect((String)session.getAttribute(StringConstants.PREV_PAGE_URL)+"&incorrectData=true");

        }


    }
}
