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
import java.util.Date;

public class EditBookCommand implements Command {
    private final static Logger logger= LogManager.getLogger(EditBookCommand.class);
    private OrderService orderService = ServiceFactory.getInstance().getOrderService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session=req.getSession();

        Order order=new Order();
        User user = (User)session.getAttribute(StringConstants.CURRENT_USER);

        order.setResFrom((Date)session.getAttribute(StringConstants.RESERVED_FROM));
        order.setResTo((Date)session.getAttribute(StringConstants.RESERVED_TO));


        System.out.println("resFrom is "+order.getResFrom());

        double oldPrice = (double)session.getAttribute("old_price");
        double difference = Double.parseDouble(req.getParameter("difference"));
        double orderPrice = oldPrice+difference;

        order.setOrderID((int)session.getAttribute("orderID"));
        order.setUserID(user.getUserID());
        order.setTotalPrice(orderPrice);
        order.setRoomID(Integer.parseInt(req.getParameter("roomID")));

        try {
            orderService.editOrder(order);
        }catch (ServiceException e){
            logger.warn(e);
            resp.sendRedirect((String)session.getAttribute(StringConstants.PREV_PAGE_URL)+"&incorrectDate=true");
        }

        session.setAttribute("resFrom", order.getResFrom());
        session.setAttribute("resTo", order.getResTo());
        session.setAttribute("difference", difference);

        resp.sendRedirect(URLConstants.CHANGE_ORDER_CONGRATS_PAGE_COMMAND);


    }
}
