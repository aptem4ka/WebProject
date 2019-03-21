package com.epam.hotel.web.command.impl;

import com.epam.hotel.entity.User;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.ReviewService;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.constants.StringConstants;
import com.epam.hotel.web.util.constants.URLConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This {@link Command} implementation is used to leave a review about hotel.
 *
 * @author Artsem Lashuk
 */
public class LeaveReview implements Command {
    private ReviewService reviewService = ServiceFactory.getInstance().getReviewService();
    private final static Logger logger= LogManager.getLogger(LeaveReview.class);

    /**
     * This method gets input data from request and calls {@link ReviewService} which
     * validates data and has an access to the DAO layer. If data is correct, review
     * will be added to the DB, in other case client will be reported about incorrect data.
     *
     * @param req {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @throws IOException if In/Out errors occur
     * @throws ServletException if any Servlet errors occur
     * @see com.epam.hotel.entity.Review
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int rating = Integer.parseInt(req.getParameter(StringConstants.RATING));
        boolean reviewAdded = true;
        String comment = req.getParameter(StringConstants.COMMENT);
        User currentUser = (User)req.getSession().getAttribute(StringConstants.CURRENT_USER);
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
            resp.sendRedirect(URLConstants.SUCCESS_REVIEW_PAGE_COMMAND);
        }else {
            resp.sendRedirect(req.getSession().getAttribute(StringConstants.PREV_PAGE_URL)+"&incorrectDate=true");
        }
    }
}
