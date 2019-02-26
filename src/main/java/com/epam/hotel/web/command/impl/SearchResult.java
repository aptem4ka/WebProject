package com.epam.hotel.web.command.impl;

import com.epam.hotel.dao.UserDao;
import com.epam.hotel.entity.Room;
import com.epam.hotel.entity.room_info.AllocationType;
import com.epam.hotel.entity.room_info.RoomType;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SearchResult implements Command {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("searchResult command");
        Room room=new Room();

        room.setType(RoomType.valueOf(req.getParameter("type").toUpperCase()));
        Date resFrom, resTo;
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        try {
            room.setResFrom(format.parse(req.getParameter("resFrom")));
            room.setResTo(format.parse(req.getParameter("resTo")));
        }catch (ParseException e){
            //TODO error page
        }
        room.setAllocation(AllocationType.valueOf(req.getParameter("allocation").toUpperCase()));
        room.setChildren(Integer.parseInt(req.getParameter("children")));

        List<Room> roomList=null;

        try {
            roomList= ServiceFactory.getInstance().getRoomService().getRoomsByRequest(room);
        }catch (ServiceException e){
            //TODO error page
        }

        //roomList=UserDao.searchRooms(req);

        req.setAttribute("roomList",roomList);

       try {
           req.getRequestDispatcher("/jsp/SearchResult.jsp").forward(req,resp);
       }catch (Exception e){
           System.out.println("temp exception in SearchResult Command");
       }


    }
}
