package com.epam.hotel.web.command.impl.admin;

import com.epam.hotel.entity.Review;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.AdminService;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.constants.StringConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This {@link Command} implementation is used to apply or discard user feedback.
 *
 * @author Artsem Lashuk
 */
public class UpdateReviewStatus implements Command {
    private final static Logger logger= LogManager.getLogger(UpdateReviewStatus.class);
    private AdminService adminService = ServiceFactory.getInstance().getAdminService();

    /**
     * This method gets review data from the request and new review status
     * which will be applied to the specified review.
     * @param req {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @throws IOException if In/Out errors occur
     * @throws ServletException if any Servlet errors occur
     * @see Review.Status
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Review review = new Review();
        String updateType = req.getParameter(StringConstants.UPDATE_TYPE);
        if (updateType!=null){
            review.setStatus(Review.Status.valueOf(updateType.toUpperCase()));
        }

        review.setReviewID(Integer.parseInt(req.getParameter(StringConstants.REVIEW_ID)));
        review.setAnswer(req.getParameter(StringConstants.ANSWER));

        try {
            adminService.updateReviewStatus(review);
        }catch (ServiceException e){
            logger.warn(e);
        }

        resp.sendRedirect((String)req.getSession().getAttribute(StringConstants.PREV_PAGE_URL));

    }
}
