package com.epam.hotel.web.command.impl;

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

public class ReviewsPageCommand implements Command {
    private ReviewService reviewService = ServiceFactory.getInstance().getReviewService();
    private final static Logger logger= LogManager.getLogger(ReviewsPageCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SavePreviousCommand.saveCommand(req);

        List<Review> reviewList = new ArrayList<>();
        Pagination pagination = Pagination.setupPaginator(req, StringConstants.POSTED_REVIEWS_PAGINATOR);

        try {
            reviewList = reviewService.takePostedReviews(pagination);
        }catch (ServiceException e){
            logger.warn(e);
        }

        pagination.lastPageControl(reviewList);

        req.setAttribute(StringConstants.REVIEW_LIST, reviewList);
        req.getRequestDispatcher(URLConstants.REVIEWS_PAGE).forward(req, resp);
    }
}
