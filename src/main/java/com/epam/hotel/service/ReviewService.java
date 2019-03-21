package com.epam.hotel.service;

import com.epam.hotel.entity.Review;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.web.util.pagination.Pagination;

import java.util.List;

/**
 * This interface defines methods for activities with reviews.
 *
 * @author Artsem Lashuk
 * @see Review
 */
public interface ReviewService {

    /**
     * Method validates guest incoming data and makes a record in the reviews table in DB.
     *
     * @param rating client's rating entered in the appropriate input
     * @param comment client's comment to the review entered in the appropriate input
     * @param phone client's phone entered in the appropriate input
     * @param name client's name entered in the appropriate input
     * @throws ServiceException if validation fails or in case of catching DAOException.
     */
    void guestLeaveReview(int rating, String comment, String phone, String name) throws ServiceException;

    /**
     * Method validates user incoming data and makes a record in the reviews table in DB.
     *
     * @param rating client's rating entered in the appropriate input
     * @param comment client's comment to the review entered in the appropriate input
     * @throws ServiceException if validation fails or in case of catching DAOException.
     */
    void userLeaveReview(int rating, String comment, User user) throws ServiceException;

    /**
     * Method validates incoming data and gets a list of reviews with status WAITING.
     *
     * @param pagination {@link Pagination}
     * @return list of reviews satisfying search criteria
     * @throws ServiceException if validation fails or in case of catching DAOException.
     * @see Review.Status
     */
    List<Review> takeReviewsForModeration(Pagination pagination) throws ServiceException;

    /**
     * Method gets from DB a number of reviews waiting for moderation.
     *
     * @return number of reviews with status WAITING
     * @throws ServiceException if validation fails or in case of catching DAOException.
     * @see Review.Status
     */
    int waitingForModerationReviews() throws ServiceException;

    /**
     * Method validates incoming data and gets a list of reviews with status POSTED.
     *
     * @param pagination {@link Pagination}
     * @return list of reviews satisfying search criteria
     * @throws ServiceException if validation fails or in case of catching DAOException.
     * @see Review.Status
     */
    List<Review> takePostedReviews(Pagination pagination) throws ServiceException;



}
