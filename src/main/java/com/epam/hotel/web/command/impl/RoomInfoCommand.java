package com.epam.hotel.web.command.impl;

import com.epam.hotel.entity.room_info.RoomType;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.RoomService;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.ResourceBundleKeys;
import com.epam.hotel.web.util.URLFromRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class RoomInfoCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String prevURL = new URLFromRequest().createURL(req);
        req.getSession().setAttribute("prevURL", prevURL);

        try {
            RoomType type = RoomType.valueOf(req.getParameter("type").toUpperCase());
            List<String> images = ServiceFactory.getInstance().getRoomService().getRoomTypeImages(type);
            List<String> facilities = new ResourceBundleKeys().getKeysByPattern("facilities." + req.getParameter("type"));
            String priceRange = ServiceFactory.getInstance().getRoomService().getPriceRange(type);

            req.setAttribute("priceRange", priceRange);
            req.setAttribute("facilities", facilities);
            req.setAttribute("type", type);
            req.setAttribute("images", images);
        }catch (ServiceException e){
            //TODO error page
        }

        req.getRequestDispatcher("/WEB-INF/jsp/RoomInfo.jsp").forward(req,resp);



    }
}
