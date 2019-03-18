package com.epam.hotel.service;

import com.epam.hotel.entity.Review;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.web.util.pagination.Pagination;

import java.util.List;

public interface ReviewService {
    void guestLeaveReview(int rating, String comment, String phone, String name) throws ServiceException;

    void userLeaveReview(int rating, String comment, User user) throws ServiceException;

    List<Review> takeReviewsForModeration(Pagination pagination) throws ServiceException;

    int waitingForModerationReviews() throws ServiceException;

    List<Review> takePostedReviews(Pagination pagination) throws ServiceException;



}
