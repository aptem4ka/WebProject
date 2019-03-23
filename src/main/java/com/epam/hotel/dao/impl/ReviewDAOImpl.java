package com.epam.hotel.dao.impl;

import com.epam.hotel.dao.ParentDao;
import com.epam.hotel.dao.ReviewDAO;
import com.epam.hotel.dao.util.SQLConstants;
import com.epam.hotel.dao.util.SqlQuery;
import com.epam.hotel.entity.Review;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.DAOException;
import com.epam.hotel.web.util.pagination.Pagination;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * This {@link ReviewDAO} implementation realizes DB activities associated with reviews.
 *
 * @author Artsem Lashuk
 * @see Review
 */
public class ReviewDAOImpl extends ParentDao implements ReviewDAO {
    private final static Logger logger = LogManager.getLogger(ReviewDAOImpl.class);

    /**
     * Add review to the DB left by guest.
     *
     * @param rating client's rating entered in the appropriate input
     * @param comment client's comment to the review entered in the appropriate input
     * @param phone client's phone entered in the appropriate input
     * @param name client's name entered in the appropriate input
     * @throws DAOException if DB query executes with errors
     */
    @Override
    public void guestLeaveReview(int rating, String comment, String phone, String name) throws DAOException {
        Connection connection = getConnection();
        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.GUEST_LEAVE_REVIEW)){
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setDate(3, new java.sql.Date(new Date().getTime()));
            ps.setInt(4, rating);
            ps.setString(5, comment);

            int success = ps.executeUpdate();

            if (success!=1){
                throw new DAOException("updated 0 rows");
            }

        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }
    }

    /**
     * Add review to the DB left by user.
     *
     * @param rating client's rating entered in the appropriate input
     * @param comment client's comment to the review entered in the appropriate input
     * @param user {@link User}
     * @throws DAOException if DB query executes with errors
     */
    @Override
    public void userLeaveReview(int rating, String comment, User user) throws DAOException {
        Connection connection = getConnection();
        try(PreparedStatement ps = connection.prepareStatement(SqlQuery.USER_LEAVE_REVIEW)){
            ps.setInt(1, user.getUserID());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPhone());
            ps.setDate(4, new java.sql.Date(new Date().getTime()));
            ps.setInt(5, rating);
            ps.setString(6, comment);

            int success = ps.executeUpdate();

            if (success!=1){
                throw new DAOException("updated 0 rows");
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }
    }

    /**
     * Get reviews from the DB with status WAITING.
     *
     * @param pagination {@link Pagination}
     * @return list of reviews
     * @throws DAOException if DB query executes with errors
     * @see Review.Status
     */
    @Override
    public List<Review> takeReviewsForModeration(Pagination pagination) throws DAOException {
        Connection connection = getConnection();
        List<Review> reviewList = new ArrayList<>();
        Review review = null;

        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.REVIEWS_FOR_MODERATION)){
            ps.setInt(1, pagination.getStartPos());
            ps.setInt(2, pagination.getOffset());

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                review = new Review();

                review.setReviewID(resultSet.getInt(SQLConstants.REVIEW_ID));
                review.setUserID(resultSet.getInt(SQLConstants.USER_ID));
                review.setName(resultSet.getString(SQLConstants.NAME));
                review.setAdded(resultSet.getDate(SQLConstants.ADDED));
                review.setComment(resultSet.getString(SQLConstants.COMMENT));
                review.setPhone(resultSet.getString(SQLConstants.PHONE));
                review.setRating(resultSet.getInt(SQLConstants.RATING));

                reviewList.add(review);
            }

        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }
        return reviewList;
    }

    /**
     * Get reviews from the DB with status POSTED.
     *
     * @param pagination {@link Pagination}
     * @return list of reviews
     * @throws DAOException if DB query executes with errors
     * @see Review.Status
     */
    @Override
    public List<Review> takePostedReviews(Pagination pagination) throws DAOException {
        Connection connection = getConnection();
        List<Review> reviewList = new ArrayList<>();
        Review review = null;

        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.POSTED_REVIEWS)){
            ps.setInt(1, pagination.getStartPos());
            ps.setInt(2, pagination.getOffset());

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                review = new Review();

                review.setReviewID(resultSet.getInt(SQLConstants.REVIEW_ID));
                review.setUserID(resultSet.getInt(SQLConstants.USER_ID));
                review.setName(resultSet.getString(SQLConstants.NAME));
                review.setAdded(resultSet.getDate(SQLConstants.ADDED));
                review.setComment(resultSet.getString(SQLConstants.COMMENT));
                review.setPhone(resultSet.getString(SQLConstants.PHONE));
                review.setRating(resultSet.getInt(SQLConstants.RATING));
                review.setAnswer(resultSet.getString(SQLConstants.ANSWER));

                reviewList.add(review);
            }

        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }
        return reviewList;

    }

    /**
     * Get number of reviews from the DB with status WAITING.
     *
     * @return number of reviews
     * @throws DAOException if DB query executes with errors
     * @see Review.Status
     */
    @Override
    public int waitingForModerationReviews() throws DAOException {
        Connection connection = getConnection();
        int count = 0;
        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.UNMODERATED_REVIEWS)){
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            count = resultSet.getInt(1);

        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }
        return count;
    }
}
