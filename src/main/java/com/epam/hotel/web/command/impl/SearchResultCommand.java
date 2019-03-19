package com.epam.hotel.web.command.impl;

import com.epam.hotel.entity.Room;
import com.epam.hotel.exception.ServiceException;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchResultCommand implements Command {
    private final static Logger logger = LogManager.getLogger(SearchResultCommand.class);
    private RoomService roomService = ServiceFactory.getInstance().getRoomService();


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SavePreviousCommand.saveCommand(req);
        HttpSession session = req.getSession();
        Room room = new Room();
        initRoom(room,req);
        List<Room> roomList = new ArrayList<>();
        try {
            roomList = roomService.roomsByRequest(room);
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

            resp.sendRedirect(URLConstants.SEARCH_RESULT_PAGE_COMMAND);

    }


    private int daysBetween(Date d1, Date d2){

        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    private void initRoom(Room room, HttpServletRequest req){

        String typeString=req.getParameter(StringConstants.ROOM_TYPE);
        if (typeString!=null) {
            room.setType(Room.RoomType.valueOf(typeString.toUpperCase()));
        }

        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        try {
            room.setResFrom(format.parse(req.getParameter(StringConstants.RESERVED_FROM)));
            room.setResTo(format.parse(req.getParameter(StringConstants.RESERVED_TO)));
        }catch (ParseException e){
            logger.warn(e);
        }
        room.setAllocation(Room.AllocationType.valueOf(req.getParameter(StringConstants.ALLOCATION).toUpperCase()));
    }
}
