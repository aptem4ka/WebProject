package com.epam.hotel.web.filter.impl;

import com.epam.hotel.entity.User;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.ServiceFactory;
import com.epam.hotel.web.filter.Filter;
import com.epam.hotel.web.util.constants.StringConstants;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Artsem Lashuk
 *
 * This class is used to get information about number of reviews
 * that waiting for moderation.
 *
 * @see Filter
 */

public class ReviewsModerationFilter extends Filter {


    /**
     * The method check if the user is an admin by checking {@link User#getUserID()}
     * and calls {@link com.epam.hotel.service.ReviewService} to read number of
     * reviews, waiting for moderation.
     *
     * @param servletRequest {@link ServletRequest}
     * @param servletResponse {@link ServletResponse}
     * @param filterChain {@link FilterChain}
     * @throws IOException if In/Out exception occurs
     * @throws ServletException if Servlet exception occurs
     * @see User
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        int reviews = 0;

        User user = (User)session.getAttribute(StringConstants.CURRENT_USER);

        if (user!=null){
            if (user.getUserID()==1){
                try {
                    reviews = ServiceFactory.getInstance().getReviewService().waitingForModerationReviews();
                    session.setAttribute("reviewsWaiting", reviews);
                }catch (ServiceException e){
                    throw new ServletException(e);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
