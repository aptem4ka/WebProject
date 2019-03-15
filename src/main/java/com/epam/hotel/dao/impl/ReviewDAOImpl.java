package com.epam.hotel.dao.impl;

import com.epam.hotel.dao.ParentDao;
import com.epam.hotel.dao.ReviewDAO;
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

public class ReviewDAOImpl extends ParentDao implements ReviewDAO {
    private final static Logger logger = LogManager.getLogger(ReviewDAOImpl.class);

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

                review.setReviewID(resultSet.getInt("reviewID"));
                review.setUserID(resultSet.getInt("userID"));
                review.setName(resultSet.getString("name"));
                review.setAdded(resultSet.getDate("added"));
                review.setComment(resultSet.getString("comment"));
                review.setPhone(resultSet.getString("phone"));
                review.setRating(resultSet.getInt("rating"));

                reviewList.add(review);
            }

        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }
        return reviewList;
    }

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

                review.setReviewID(resultSet.getInt("reviewID"));
                review.setUserID(resultSet.getInt("userID"));
                review.setName(resultSet.getString("name"));
                review.setAdded(resultSet.getDate("added"));
                review.setComment(resultSet.getString("comment"));
                review.setPhone(resultSet.getString("phone"));
                review.setRating(resultSet.getInt("rating"));
                review.setAnswer(resultSet.getString("answer"));

                reviewList.add(review);
            }

        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }
        return reviewList;

    }
}
