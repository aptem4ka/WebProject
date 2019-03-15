package com.epam.hotel.web.command.impl;

import com.epam.hotel.entity.User;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.ReviewService;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.StringConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LeaveReviewCommand implements Command {
    private ReviewService reviewService = ServiceFactory.getInstance().getReviewService();
    private final static Logger logger= LogManager.getLogger(LeaveReviewCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        int rating = Integer.parseInt(req.getParameter("rating"));
        boolean reviewAdded = true;
        String comment = req.getParameter("comment");
        User currentUser = (User)req.getSession().getAttribute("currentUser");
        try {

        if (currentUser == null){
            String phone = req.getParameter(StringConstants.PHONE);
            String name = req.getParameter(StringConstants.NAME);
            reviewService.guestLeaveReview(rating, comment, phone, name);

        }else {
            reviewService.userLeaveReview(rating, comment, currentUser);
        }

        }catch (ServiceException e){
            reviewAdded = false;
            logger.warn(e);
        }

        if (reviewAdded){
            resp.sendRedirect("ControllerServlet?command=success_review_page");
        }else {
            resp.sendRedirect((String)req.getSession().getAttribute(StringConstants.PREV_PAGE_URL)+"&incorrectDate=true");
        }
    }
}
