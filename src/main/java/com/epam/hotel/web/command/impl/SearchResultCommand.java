package com.epam.hotel.web.command.impl;

import com.epam.hotel.entity.Room;
import com.epam.hotel.entity.room_info.AllocationType;
import com.epam.hotel.entity.room_info.RoomType;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.RoomService;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.StringConstants;
import com.epam.hotel.web.util.URLFromRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

public class SearchResultCommand implements Command {
    RoomService roomService = ServiceFactory.getInstance().getRoomService();

//TEMP CLASS
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        Room room=new Room();

        initRoom(room,req);

        List<Room> roomList=null;

        try {
            roomList= roomService.roomsByRequest(room);
        }catch (ServiceException e){
            //TODO error page
        }

        int days = daysBetween(room.getResFrom(), room.getResTo());

        req.setAttribute("roomList",roomList);
        req.setAttribute("days", days);
        req.setAttribute("resFrom", room.getResFrom());
        req.setAttribute("resTo",room.getResTo());
        req.setAttribute("children", room.getChildren());
        req.setAttribute("allocation", room.getAllocation());

       try {
           req.getRequestDispatcher("/WEB-INF/jsp/SearchResult.jsp").forward(req,resp);
       }catch (Exception e){

       }
    }


    private int daysBetween(Date d1, Date d2){

        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    private Room initRoom(Room room, HttpServletRequest req){

        String typestring=req.getParameter("type");
        if (typestring!=null) {
            room.setType(RoomType.valueOf(req.getParameter("type").toUpperCase()));
        }

        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        try {
            room.setResFrom(format.parse(req.getParameter("resFrom")));
            room.setResTo(format.parse(req.getParameter("resTo")));
        }catch (ParseException e){
            //TODO error page
        }
        room.setAllocation(AllocationType.valueOf(req.getParameter("allocation").toUpperCase()));
        room.setChildren(Integer.parseInt(req.getParameter("children")));

        return room;
    }
}
