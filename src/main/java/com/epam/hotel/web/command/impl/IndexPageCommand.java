package com.epam.hotel.web.command.impl;

import com.epam.hotel.entity.room_info.AllocationType;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class IndexPageCommand implements Command {
    RoomService roomService=ServiceFactory.getInstance().getRoomService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String prevURL = new URLFromRequest().createURL(req);
        HttpSession session=req.getSession();
        Set<String> images=null;
        if (session.getAttribute(StringConstants.CAROUSEL_IMAGES)==null){
            try {
                 images = roomService.allRoomImages();
            }catch (ServiceException e){
                //TODO error page
            }
            req.setAttribute(StringConstants.CAROUSEL_IMAGES,images);
        }
       // List<AllocationType> allocations = roomService.allocationsForType(type);

        req.getSession().setAttribute(StringConstants.PREV_PAGE_URL, prevURL);

        req.getRequestDispatcher(URLConstants.INDEX_PAGE).forward(req,resp);

    }
}
