package com.epam.hotel.web.command.impl;

import com.epam.hotel.entity.Room;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.OrderService;
import com.epam.hotel.service.RoomService;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.StringConstants;
import com.epam.hotel.web.util.URLConstants;
import com.epam.hotel.web.util.URLFromRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChangeOrderCommand implements Command {
    private final static Logger logger= LogManager.getLogger(ChangeOrderCommand.class);
    private OrderService orderService = ServiceFactory.getInstance().getOrderService();
    private RoomService roomService = ServiceFactory.getInstance().getRoomService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String prevURL = new URLFromRequest().createURL(req);
        HttpSession session=req.getSession();
        session.setAttribute(StringConstants.PREV_PAGE_URL, prevURL);

        int userID = Integer.parseInt(req.getParameter("userID"));
        int orderID = Integer.parseInt(req.getParameter("orderID"));
        int roomID = Integer.parseInt(req.getParameter("roomID"));

        SimpleDateFormat formatRu = new SimpleDateFormat(StringConstants.REQUEST_DATE_FORMAT_RU);
        SimpleDateFormat formatEN = new SimpleDateFormat(StringConstants.REQUEST_DATE_FORMAT_EN);
        Date resFrom = null;
        Date resTo = null;

        try {
                resFrom = formatRu.parse(req.getParameter("resFrom"));
                resTo = formatRu.parse(req.getParameter("resTo"));

        }catch (ParseException e){
            logger.warn(e);
        }
        if (resFrom == null || resTo == null){
            logger.info("trying to parse EN date");
            try {

                resFrom = formatEN.parse(req.getParameter("resFrom"));
                resTo = formatEN.parse(req.getParameter("resTo"));

            }catch (ParseException e){
                logger.warn(e);
            }
        }

        List<Room.AllocationType> allocations = null;

        double orderPrice = 0;
        Room room = null;

        try {
            orderPrice = orderService.orderPrice(userID, orderID);
            room = roomService.roomInfoByRoomID(roomID);
            allocations = roomService.allocationsIgnoreType();
        }catch (ServiceException e){
            logger.warn(e);
        }

        if (orderPrice!=0 && room!=null){
            session.setAttribute("orderID", orderID);
            session.setAttribute("old_price", orderPrice);
            req.setAttribute("allocations", allocations);
            req.setAttribute("room", room);
            req.setAttribute("resFrom", resFrom);
            req.setAttribute("resTo", resTo);
            req.getRequestDispatcher("/WEB-INF/jsp/ChangeOrderForm.jsp").forward(req,resp);
        } else {
            req.setAttribute("changeFailed",true);
            req.getRequestDispatcher(URLConstants.PROFILE_PAGE).forward(req,resp);
        }




    }
}
