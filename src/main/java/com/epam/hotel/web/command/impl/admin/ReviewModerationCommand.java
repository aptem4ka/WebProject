package com.epam.hotel.web.command.impl.admin;

import com.epam.hotel.entity.Review;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.ReviewService;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.StringConstants;
import com.epam.hotel.web.util.URLFromRequest;
import com.epam.hotel.web.util.pagination.Pagination;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReviewModerationCommand implements Command {
    private ReviewService reviewService = ServiceFactory.getInstance().getReviewService();
    private final static Logger logger= LogManager.getLogger(ReviewModerationCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String prevURL = new URLFromRequest().createURL(req);
        req.getSession().setAttribute(StringConstants.PREV_PAGE_URL, prevURL);

        List<Review> reviewList = new ArrayList<>();

        Pagination pagination = Pagination.setupPaginator(req, "reviewsPaginator");

        try {
            reviewList = reviewService.takeReviewsForModeration(pagination);
        }catch (ServiceException e){
            logger.warn(e);
        }

        pagination.lastPageControl(reviewList);

        req.setAttribute("reviewList", reviewList);
        req.getRequestDispatcher("/WEB-INF/jsp/ReviewModeration.jsp").forward(req, resp);




    }
}