package com.epam.hotel.web.command.impl;

import com.epam.hotel.entity.Room;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.RoomService;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.ResourceBundleKeys;
import com.epam.hotel.web.util.SavePreviousCommand;
import com.epam.hotel.web.util.constants.StringConstants;
import com.epam.hotel.web.util.constants.URLConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * This {@link Command} implementation is used to show information
 * about specific room type
 *
 * @author Artsem Lashuk
 */
public class RoomInfo implements Command {
    private RoomService roomService=ServiceFactory.getInstance().getRoomService();
    private final static Logger logger = LogManager.getLogger(RoomInfo.class);

    /**
     * This method collects data about available {@link com.epam.hotel.entity.Room.AllocationType},
     * facilities, images, prices of specific {@link com.epam.hotel.entity.Room.RoomType}. All
     * collected data dispatches to the client as a request attribute.
     *
     * @param req {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @throws IOException if In/Out errors occur
     * @throws ServletException if any Servlet errors occur
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SavePreviousCommand.saveCommand(req);

        try {
            Room.RoomType type = Room.RoomType.valueOf(req.getParameter(StringConstants.ROOM_TYPE).toUpperCase());
            List<String> images = roomService.roomTypeImages(type);
            List<Room.AllocationType> allocations = roomService.allocationsForType(type);

            List<String> facilities = new ResourceBundleKeys()
                    .getKeysByPattern(StringConstants.FACILITIES_PATTERN + req.getParameter(StringConstants.ROOM_TYPE));

            String priceRange = roomService.priceRange(type);

            req.setAttribute(StringConstants.ALLOCATIONS, allocations);
            req.setAttribute(StringConstants.PRICE_RANGE, priceRange);
            req.setAttribute(StringConstants.FACILITIES, facilities);
            req.setAttribute(StringConstants.ROOM_TYPE, type);
            req.setAttribute(StringConstants.IMAGES, images);
        }catch (ServiceException e){
            logger.warn(e);
        }

        req.getRequestDispatcher(URLConstants.ROOM_INFO_PAGE).forward(req,resp);



    }
}
