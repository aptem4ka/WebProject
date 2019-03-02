package com.epam.hotel.web.command.impl;

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
import java.util.Map;


public class RoomListCommand implements Command {
    private RoomService roomService=ServiceFactory.getInstance().getRoomService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    Map<RoomType,String> roomPreviews = null;
    String prevURL = new URLFromRequest().createURL(req);
    req.getSession().setAttribute(StringConstants.PREV_PAGE_URL, prevURL);

    try {
        roomPreviews=roomService.roomPreviews();
    }catch (ServiceException e){
        //TODO error page
    }
    req.setAttribute(StringConstants.ROOM_PREVIEWS, roomPreviews);

    req.getRequestDispatcher(URLConstants.ROOM_LIST_PAGE).forward(req,resp);


    }
}
