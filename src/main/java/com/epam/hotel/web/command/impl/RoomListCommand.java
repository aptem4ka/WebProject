package com.epam.hotel.web.command.impl;

import com.epam.hotel.dao.DaoFactory;
import com.epam.hotel.dao.UserDao;
import com.epam.hotel.entity.Room;
import com.epam.hotel.entity.room_info.RoomType;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.RoomService;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.URLFromRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class RoomListCommand implements Command {
    private final static String IMAGE_URI="/images/rooms/";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    RoomService service=ServiceFactory.getInstance().getRoomService();
    Map<RoomType,String> roomPreviews = null;
    String prevURL = new URLFromRequest().createURL(req);
    req.getSession().setAttribute("prevURL", prevURL);

   // List<RoomType> roomTypes= service.getRoomTypes();
    try {
        roomPreviews=service.getRoomPreviews();
    }catch (ServiceException e){
        //TODO error page
    }
    req.setAttribute("roomPreviews", roomPreviews);

    req.getRequestDispatcher("/WEB-INF/jsp/RoomList.jsp").forward(req,resp);


    }
}
