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
import java.util.Map;

/**
 * This {@link Command} implementation is used to dispatch client
 * to the list of available rooms
 *
 * @author Artsem Lashuk
 */
public class RoomListCommand implements Command {
    private RoomService roomService=ServiceFactory.getInstance().getRoomService();
    private final static Logger logger = LogManager.getLogger(RoomListCommand.class);

    /**
     * The method saves previous request by calling {@link SavePreviousCommand#saveCommand(HttpServletRequest)}
     * and then receive room preview images from the DB. After all the method
     * dispatches response to the client. Previews are stored in the {@link Map}
     * according to the {@link com.epam.hotel.entity.Room.RoomType}.
     *
     * @param req {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @throws IOException if In/Out errors occur
     * @throws ServletException if any Servlet errors occur
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SavePreviousCommand.saveCommand(req);
        Map<Room.RoomType,String> roomPreviews = null;
        try {
            roomPreviews=roomService.roomPreviews();
        }catch (ServiceException e){
            logger.warn(e);
        }
        req.setAttribute(StringConstants.ROOM_PREVIEWS, roomPreviews);

        req.getRequestDispatcher(URLConstants.ROOM_LIST_PAGE).forward(req,resp);


    }
}
