package com.epam.hotel.web.command.impl;

import com.epam.hotel.entity.Room;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.RoomService;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.command.Command;
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
import java.util.Set;

/**
 * This {@link Command} implementations is used to get required data for the index page.
 *
 * @author Artsem Lashuk
 */
public class IndexPageCommand implements Command {
    private final static Logger logger= LogManager.getLogger(IndexPageCommand.class);
    private RoomService roomService=ServiceFactory.getInstance().getRoomService();

    /**
     * The method calls {@link RoomService#allRoomImages()} to get room images for
     * the carousel on the index page.
     * Also it calls {@link RoomService#allocationsIgnoreType()} to get all available
     * room allocations for quick booking.
     *
     * @param req {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @throws IOException if In/Out errors occur
     * @throws ServletException if any Servlet errors occur
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SavePreviousCommand.saveCommand(req);

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
