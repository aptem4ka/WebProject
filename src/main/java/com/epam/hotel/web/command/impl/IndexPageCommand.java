package com.epam.hotel.web.command.impl;

import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.URLFromRequest;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

public class IndexPageCommand implements Command {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String prevURL = new URLFromRequest().createURL(req);
        HttpSession session=req.getSession();
        Set<String> images=null;
        if (session.getAttribute("carouselImages")==null){
            System.out.println("getting images for index page from DAO");
            try {
                 images = ServiceFactory.getInstance().getRoomService().getAllRoomImages();
            }catch (ServiceException e){
                //TODO error page
            }
            req.setAttribute("carouselImages",images);
        }

        req.getSession().setAttribute("prevURL", prevURL);

        req.getRequestDispatcher("/WEB-INF/jsp/IndexPage.jsp").forward(req,resp);

    }
}
