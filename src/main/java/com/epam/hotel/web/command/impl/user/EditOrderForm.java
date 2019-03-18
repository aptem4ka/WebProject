package com.epam.hotel.web.command.impl.user;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EditOrderForm implements Command {
    private final static Logger logger= LogManager.getLogger(EditOrderForm.class);
    private OrderService orderService = ServiceFactory.getInstance().getOrderService();
    private RoomService roomService = ServiceFactory.getInstance().getRoomService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String prevURL = new URLFromRequest().createURL(req);
        HttpSession session=req.getSession();
        session.setAttribute(StringConstants.PREV_PAGE_URL, prevURL);

        int userID = Integer.parseInt(req.getParameter(StringConstants.USER_ID));
        int orderID = Integer.parseInt(req.getParameter(StringConstants.ORDER_ID));
        int roomID = Integer.parseInt(req.getParameter(StringConstants.ROOM_ID));

        SimpleDateFormat formatRu = new SimpleDateFormat(StringConstants.REQUEST_DATE_FORMAT_RU);
        SimpleDateFormat formatEN = new SimpleDateFormat(StringConstants.REQUEST_DATE_FORMAT_EN);
        Date resFrom = null;
        Date resTo = null;

        try {
                resFrom = formatRu.parse(req.getParameter(StringConstants.RESERVED_FROM));
                resTo = formatRu.parse(req.getParameter(StringConstants.RESERVED_TO));

        }catch (ParseException e){
            logger.warn(e);
        }
        if (resFrom == null || resTo == null){
            logger.info("trying to parse EN date");
            try {

                resFrom = formatEN.parse(req.getParameter(StringConstants.RESERVED_FROM));
                resTo = formatEN.parse(req.getParameter(StringConstants.RESERVED_TO));

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
            session.setAttribute(StringConstants.ORDER_ID, orderID);
            session.setAttribute(StringConstants.OLD_PRICE, orderPrice);
            req.setAttribute(StringConstants.ALLOCATIONS, allocations);
            req.setAttribute(StringConstants.ROOM, room);
            req.setAttribute(StringConstants.RESERVED_FROM, resFrom);
            req.setAttribute(StringConstants.RESERVED_TO, resTo);
            req.getRequestDispatcher(URLConstants.CHANGE_ORDER_FORM).forward(req,resp);
        } else {
            req.setAttribute(StringConstants.CHANGE_FAILED,true);
            req.getRequestDispatcher(URLConstants.PROFILE_PAGE).forward(req,resp);
        }




    }
}
