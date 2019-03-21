package com.epam.hotel.service.impl;

import com.epam.hotel.dao.DaoFactory;
import com.epam.hotel.dao.ReviewDAO;
import com.epam.hotel.entity.Review;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.DAOException;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.ReviewService;
import com.epam.hotel.service.validation.ValidatorManager;
import com.epam.hotel.service.validation.ValidatorName;
import com.epam.hotel.web.util.pagination.Pagination;

import java.util.List;

/**
 * This {@link ReviewService} implementation realizes activities with reviews.
 *
 * @author Artsem Lashuk
 */
public class ReviewServiceImpl implements ReviewService {
    private ReviewDAO reviewDAO = DaoFactory.getInstance().getReviewDAO();
    private ValidatorManager validatorManager=ValidatorManager.getInstance();

    /**
     * Method validates guest incoming data and makes a record in the reviews table in DB.
     *
     * @param rating client's rating entered in the appropriate input
     * @param comment client's comment to the review entered in the appropriate input
     * @param phone client's phone entered in the appropriate input
     * @param name client's name entered in the appropriate input
     * @throws ServiceException if validation fails or in case of catching DAOException.
     */
    @Override
    public void guestLeaveReview(int rating, String comment, String phone, String name) throws ServiceException {
        if (rating<1 || rating>5){
            throw new ServiceException("Incorrect rating");
        }
        if (comment == null){
            throw new ServiceException("comment is null");
        }
        if (!validatorManager.getValidator(ValidatorName.PHONE).isValid(phone)){
            throw new ServiceException("incorrect phone");
        }
        if (!validatorManager.getValidator(ValidatorName.NAME).isValid(name)){
            throw new ServiceException("incorrect name");
        }

        try {
            reviewDAO.guestLeaveReview(rating, comment, phone, name);
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }

    /**
     * Method validates user incoming data and makes a record in the reviews table in DB.
     *
     * @param rating client's rating entered in the appropriate input
     * @param comment client's comment to the review entered in the appropriate input
     * @throws ServiceException if validation fails or in case of catching DAOException.
     */
    @Override
    public void userLeaveReview(int rating, String comment, User user) throws ServiceException {

        if (rating<1 || rating>5){
            throw new ServiceException("Incorrect rating");
        }
        if (comment == null){
            throw new ServiceException("comment is null");
        }
        if (user.getUserID()<1){
            throw new ServiceException("Incorrect userID");
        }
        if (!validatorManager.getValidator(ValidatorName.NAME).isValid(user.getName())){
            throw new ServiceException("Incorrect name");
        }

        try {
            reviewDAO.userLeaveReview(rating, comment, user);
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }

    /**
     * Method validates incoming data and gets a list of reviews with status WAITING.
     *
     * @param pagination {@link Pagination}
     * @return list of reviews satisfying search criteria
     * @throws ServiceException if validation fails or in case of catching DAOException.
     * @see Review.Status
     */
    @Override
    public List<Review> takeReviewsForModeration(Pagination pagination) throws ServiceException {
        if (pagination==null){
            throw new ServiceException("Null paginator");
        }

        try {
            return reviewDAO.takeReviewsForModeration(pagination);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    /**
     * Method validates incoming data and gets a list of reviews with status POSTED.
     *
     * @param pagination {@link Pagination}
     * @return list of reviews satisfying search criteria
     * @throws ServiceException if validation fails or in case of catching DAOException.
     * @see Review.Status
     */
    @Override
    public List<Review> takePostedReviews(Pagination pagination) throws ServiceException {
        if (pagination==null){
            throw new ServiceException("Null paginator");
        }

        try {
            return reviewDAO.takePostedReviews(pagination);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    /**
     * Method gets from DB a number of reviews waiting for moderation.
     *
     * @return number of reviews with status WAITING
     * @throws ServiceException if validation fails or in case of catching DAOException.
     * @see Review.Status
     */
    @Override
    public int waitingForModerationReviews() throws ServiceException {
        try {
            return reviewDAO.waitingForModerationReviews();
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }
}
