package com.epam.hotel.web.command.impl.user;

import com.epam.hotel.entity.Room;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.OrderService;
import com.epam.hotel.service.RoomService;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.SavePreviousCommand;
import com.epam.hotel.web.util.constants.StringConstants;
import com.epam.hotel.web.util.constants.URLConstants;
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

/**
 * This {@link Command} implementation is used to collect information about current
 * order data for further using in the new order form.
 *
 * @author Artsem Lashuk
 */
public class EditOrderForm implements Command {
    private final static Logger logger= LogManager.getLogger(EditOrderForm.class);
    private OrderService orderService = ServiceFactory.getInstance().getOrderService();
    private RoomService roomService = ServiceFactory.getInstance().getRoomService();

    /**
     * This method collect data about actual order from the request and additional
     * data from the DB about available allocation to create an edit order form.
     * If getting data successful, the client will be redirected to the search result
     * page. In other case he well be returned to the profile page.
     *
     * @param req {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @throws IOException if In/Out errors occur
     * @throws ServletException if any Servlet errors occur
     * @see Room
     * @see com.epam.hotel.entity.Room.AllocationType
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SavePreviousCommand.saveCommand(req);

        HttpSession session=req.getSession();
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
