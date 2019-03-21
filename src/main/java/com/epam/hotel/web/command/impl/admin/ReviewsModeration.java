package com.epam.hotel.web.command.impl.admin;

import com.epam.hotel.entity.Review;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.ReviewService;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.SavePreviousCommand;
import com.epam.hotel.web.util.constants.StringConstants;
import com.epam.hotel.web.util.constants.URLConstants;
import com.epam.hotel.web.util.pagination.Pagination;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This {@link Command} implementation is used to get reviews waiting for moderation.
 *
 * @author Artsem Lashuk
 */
public class ReviewsModeration implements Command {
    private ReviewService reviewService = ServiceFactory.getInstance().getReviewService();
    private final static Logger logger= LogManager.getLogger(ReviewsModeration.class);

    /**
     * This method calls {@link ReviewService} which has an access to the DAO object,
     * which makes a query to the DB and gets reviews that waiting for moderation.
     * {@link Pagination} is used to limit amount of data that will be received from the DB.
     *
     * @param req {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @throws IOException if In/Out errors occur
     * @throws ServletException if any Servlet errors occur
     * @see Review
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SavePreviousCommand.saveCommand(req);

        List<Review> reviewList = new ArrayList<>();

        Pagination pagination = Pagination.setupPaginator(req, StringConstants.REVIEWS_PAGINATOR);

        try {
            reviewList = reviewService.takeReviewsForModeration(pagination);
        }catch (ServiceException e){
            logger.warn(e);
        }

        pagination.lastPageControl(reviewList);

        req.setAttribute(StringConstants.REVIEW_LIST, reviewList);
        req.getRequestDispatcher(URLConstants.REVIEW_MODERATION_PAGE).forward(req, resp);

    }
}
