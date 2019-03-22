package com.epam.hotel.dao;

import com.epam.hotel.entity.Review;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.DAOException;
import com.epam.hotel.web.util.pagination.Pagination;

import java.util.List;

/**
 * This interface defines activities associated with reviews.
 *
 * @author Artsem Lashuk
 * @see Review
 */
public interface ReviewDAO {

    /**
     * Add review to the DB left by guest.
     *
     * @param rating client's rating entered in the appropriate input
     * @param comment client's comment to the review entered in the appropriate input
     * @param phone client's phone entered in the appropriate input
     * @param name client's name entered in the appropriate input
     * @throws DAOException if DB query executes with errors
     */
    void guestLeaveReview(int rating, String comment, String phone, String name) throws DAOException;

    /**
     * Add review to the DB left by user.
     *
     * @param rating client's rating entered in the appropriate input
     * @param comment client's comment to the review entered in the appropriate input
     * @param user {@link User}
     * @throws DAOException if DB query executes with errors
     */
    void userLeaveReview(int rating, String comment, User user) throws DAOException;

    /**
     * Get number of reviews from the DB with status WAITING.
     *
     * @return number of reviews
     * @throws DAOException if DB query executes with errors
     * @see Review.Status
     */
    int waitingForModerationReviews() throws DAOException;

    /**
     * Get reviews from the DB with status WAITING.
     *
     * @param pagination {@link Pagination}
     * @return list of reviews
     * @throws DAOException if DB query executes with errors
     * @see Review.Status
     */
    List<Review> takeReviewsForModeration(Pagination pagination) throws DAOException;

    /**
     * Get reviews from the DB with status POSTED.
     *
     * @param pagination {@link Pagination}
     * @return list of reviews
     * @throws DAOException if DB query executes with errors
     * @see Review.Status
     */
    List<Review> takePostedReviews(Pagination pagination) throws DAOException;

}
