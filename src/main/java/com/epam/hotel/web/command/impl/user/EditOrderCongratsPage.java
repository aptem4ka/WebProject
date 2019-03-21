package com.epam.hotel.web.command.impl.user;

import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.SavePreviousCommand;
import com.epam.hotel.web.util.constants.URLConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This {@link Command} implementation is used to dispatch to the congratulations page
 * after successful order editing.
 *
 * @author Artsem Lashuk
 */
public class EditOrderCongratsPage implements Command {
    private final static Logger logger = LogManager.getLogger(EditOrderCongratsPage.class);

    /**
     * The method saves previous command by calling {@link SavePreviousCommand#saveCommand(HttpServletRequest)}
     * and dispatches client to the congratulations page.
     *
     * @param req {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @throws IOException if In/Out errors occur
     * @throws ServletException if any Servlet errors occur
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SavePreviousCommand.saveCommand(req);

        req.getRequestDispatcher(URLConstants.EDIT_INFO_PAGE).forward(req,resp);
    }
}
