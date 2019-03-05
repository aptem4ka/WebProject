package com.epam.hotel.web.command.impl;

import com.epam.hotel.entity.Room;
import com.epam.hotel.entity.room_info.AllocationType;
import com.epam.hotel.entity.room_info.RoomType;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.RoomService;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.StringConstants;
import com.epam.hotel.web.util.URLConstants;
import com.epam.hotel.web.util.URLFromRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchResultCommand implements Command {
    RoomService roomService = ServiceFactory.getInstance().getRoomService();


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String prevURL = new URLFromRequest().createURL(req);
        req.getSession().setAttribute(StringConstants.PREV_PAGE_URL, prevURL);

        Room room = new Room();
        initRoom(room,req);
        List<Room> roomList = new ArrayList<>();
        try {
            roomList = roomService.roomsByRequest(room);
        }catch (ServiceException e){
            //TODO error page
        }
            int days = daysBetween(room.getResFrom(), room.getResTo());
            req.setAttribute(StringConstants.ROOM_LIST, roomList);
            req.setAttribute(StringConstants.DAYS, days);
            req.setAttribute(StringConstants.RESERVED_FROM, room.getResFrom());
            req.setAttribute(StringConstants.RESERVED_TO, room.getResTo());
            req.setAttribute(StringConstants.CHILDREN, room.getChildren());
            req.setAttribute(StringConstants.ALLOCATION, room.getAllocation());

            req.getRequestDispatcher(URLConstants.SEARCH_RESULT_PAGE).forward(req,resp);

    }


    private int daysBetween(Date d1, Date d2){

        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    private void initRoom(Room room, HttpServletRequest req){

        String typeString=req.getParameter(StringConstants.ROOM_TYPE);
        if (typeString!=null) {
            room.setType(RoomType.valueOf(typeString.toUpperCase()));
        }

        SimpleDateFormat format=new SimpleDateFormat(StringConstants.REQUEST_DATE_FORMAT);
        try {
            room.setResFrom(format.parse(req.getParameter(StringConstants.RESERVED_FROM)));
            room.setResTo(format.parse(req.getParameter(StringConstants.RESERVED_TO)));
        }catch (ParseException e){
            //TODO error page
        }
        room.setAllocation(AllocationType.valueOf(req.getParameter(StringConstants.ALLOCATION).toUpperCase()));
        room.setChildren(Integer.parseInt(req.getParameter(StringConstants.CHILDREN)));
    }
}
