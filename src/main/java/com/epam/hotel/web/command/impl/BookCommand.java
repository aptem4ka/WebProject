package com.epam.hotel.web.command.impl;

import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.Room;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.OrderService;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookCommand implements Command {
    OrderService orderService= ServiceFactory.getInstance().getOrderService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Order order=new Order();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");

        try {
            order.setResFrom(dateFormat.parse(req.getParameter("resFrom")));
            order.setResTo(dateFormat.parse(req.getParameter("resTo")));
        }catch (ParseException e){
            //TODO error page
        }
        order.setRoomID(Integer.parseInt(req.getParameter("roomID")));
        User user = (User)req.getSession(false).getAttribute("currentUser");

        try {
        if (user!=null){
            order.setUserID(user.getUserID());
            order = orderService.registeredUserBooking(order);
        }else {
            user=new User();
            user.setName(req.getParameter("name"));
            user.setSurname(req.getParameter("surname"));
            user.setPhone(req.getParameter("phone"));

            order = orderService.unregisteredUserBooking(order, user);
        }

        }catch (ServiceException e){
            System.out.println("error adding order");
            //TODO error page
        }

            req.getRequestDispatcher("/WEB-INF/jsp/IndexPage.jsp");




    }
}
