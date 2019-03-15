package com.epam.hotel.dao;

import com.epam.hotel.entity.Review;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.DAOException;
import com.epam.hotel.web.util.pagination.Pagination;

import java.util.List;

public interface ReviewDAO {

    void guestLeaveReview(int rating, String comment, String phone, String name) throws DAOException;

    void userLeaveReview(int rating, String comment, User user) throws DAOException;

    List<Review> takeReviewsForModeration(Pagination pagination) throws DAOException;

    List<Review> takePostedReviews(Pagination pagination) throws DAOException;

}
