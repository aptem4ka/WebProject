package com.epam.hotel.web.command.impl.user;

import com.epam.hotel.entity.Room;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.RoomService;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.command.impl.SearchResult;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This {@link Command} implementation is used to search available rooms to edit
 * current order's data.
 *
 * @author Artsem Lashuk
 */
public class EditOrderSearchResult implements Command {
    private final static Logger logger = LogManager.getLogger(SearchResult.class);
    private RoomService roomService = ServiceFactory.getInstance().getRoomService();

    /**
     * First of all, this method gets new dates and allocation for current order.
     * Then method calls {@link RoomService} to validate this new data and to get
     * an access to the DAO layer. After all the client will be redirected to the
     * search result page.
     *
     * @param req {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @throws IOException if In/Out errors occur
     * @throws ServletException if any Servlet errors occur
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        Room room = new Room();
        List<Room> roomList = new ArrayList<>();

        SimpleDateFormat format=new SimpleDateFormat(StringConstants.INPUT_DATE_FORMAT);
        try {
            room.setResFrom(format.parse(req.getParameter(StringConstants.RESERVED_FROM)));
            room.setResTo(format.parse(req.getParameter(StringConstants.RESERVED_TO)));
        }catch (ParseException e){
            logger.warn(e);
        }
        room.setAllocation(Room.AllocationType.valueOf(req.getParameter(StringConstants.ALLOCATION).toUpperCase()));

        try {
            roomList = roomService.changeOrderSearchResult(room, (int)req.getSession().getAttribute("orderID"));
        }catch (ServiceException e){
            logger.warn(e);
        }

        int children = Integer.parseInt(req.getParameter(StringConstants.CHILDREN));
        for (Room tempRoom:roomList){
            tempRoom.setChildren(children);
        }

        int days = daysBetween(room.getResFrom(), room.getResTo());
        session.setAttribute(StringConstants.ROOM_LIST, roomList);
        session.setAttribute(StringConstants.DAYS, days);
        session.setAttribute(StringConstants.RESERVED_FROM, room.getResFrom());
        session.setAttribute(StringConstants.RESERVED_TO, room.getResTo());
        session.setAttribute(StringConstants.CHILDREN, room.getChildren());
        session.setAttribute(StringConstants.ALLOCATION, room.getAllocation());
        resp.sendRedirect(URLConstants.CHANGE_ORDER_RESULT_PAGE_COMMAND);

    }

    private int daysBetween(Date d1, Date d2){

        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }
}
