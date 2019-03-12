package com.epam.hotel.web.command.impl;

import com.epam.hotel.entity.Room;
import com.epam.hotel.exception.ServiceException;
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
import java.util.List;
import java.util.Set;

public class IndexPageCommand implements Command {
    private final static Logger logger= LogManager.getLogger(IndexPageCommand.class);
    private RoomService roomService=ServiceFactory.getInstance().getRoomService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String prevURL = new URLFromRequest().createURL(req);
        HttpSession session=req.getSession();
        session.setAttribute(StringConstants.PREV_PAGE_URL, prevURL);

        Set<String> images=null;
        List<Room.AllocationType> allocations = null;

            try {
                 images = roomService.allRoomImages();
                 allocations = roomService.allocationsIgnoreType();
            }catch (ServiceException e){
                logger.warn(e);
            }
            req.setAttribute(StringConstants.CAROUSEL_IMAGES, images);
            req.setAttribute(StringConstants.ALLOCATIONS, allocations);


        req.getRequestDispatcher(URLConstants.INDEX_PAGE).forward(req,resp);

    }
}
