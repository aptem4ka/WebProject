package com.epam.hotel.web.command.impl;

import com.epam.hotel.entity.room_info.RoomType;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.RoomService;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.ResourceBundleKeys;
import com.epam.hotel.web.util.StringConstants;
import com.epam.hotel.web.util.URLConstants;
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
        req.getSession().setAttribute(StringConstants.PREV_PAGE_URL, prevURL);

        try {
            RoomType type = RoomType.valueOf(req.getParameter(StringConstants.ROOM_TYPE).toUpperCase());
            List<String> images = ServiceFactory.getInstance().getRoomService().getRoomTypeImages(type);

            List<String> facilities = new ResourceBundleKeys()
                    .getKeysByPattern(StringConstants.FACILITIES_PATTERN + req.getParameter(StringConstants.ROOM_TYPE));

            String priceRange = ServiceFactory.getInstance().getRoomService().getPriceRange(type);

            req.setAttribute(StringConstants.PRICE_RANGE, priceRange);
            req.setAttribute(StringConstants.FACILITIES, facilities);
            req.setAttribute(StringConstants.ROOM_TYPE, type);
            req.setAttribute(StringConstants.IMAGES, images);
        }catch (ServiceException e){
            //TODO error page
        }

        req.getRequestDispatcher(URLConstants.ROOM_INFO_PAGE).forward(req,resp);



    }
}
