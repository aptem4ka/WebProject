package com.epam.hotel.web.command.impl;

import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.OrderService;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.StringConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EditBookCommand implements Command {
    private final static Logger logger= LogManager.getLogger(EditBookCommand.class);
    private OrderService orderService = ServiceFactory.getInstance().getOrderService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session=req.getSession();

        Order order=new Order();
        User user = (User)session.getAttribute(StringConstants.CURRENT_USER);

        SimpleDateFormat dateFormat = new SimpleDateFormat(StringConstants.REQUEST_DATE_FORMAT);
        try {
            order.setResFrom(dateFormat.parse(req.getParameter(StringConstants.RESERVED_FROM)));
            order.setResTo(dateFormat.parse(req.getParameter(StringConstants.RESERVED_TO)));

        }catch (ParseException e) {
            logger.warn(e);
        }

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

        req.getRequestDispatcher("/WEB-INF/jsp/EditInfo.jsp").forward(req,resp);


    }
}
