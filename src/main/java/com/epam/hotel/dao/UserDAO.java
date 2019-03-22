package com.epam.hotel.dao;

import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.RegistrationForm;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.DAOException;
import com.epam.hotel.web.util.pagination.Pagination;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * This interface defines DB activities associated with user.
 *
 * @author Artsem Lashuk
 * @see User
 */
public interface UserDAO {

    /**
     * This method check user data in the DB table and return user with
     * filled field {@link User#isValid()}
     *
     * @param user user that tries to sign in
     * @return user with {@link User#isValid()} true if signing in succeeded
     * @throws DAOException if DB query executes with errors
     */
    User loginUser(User user) throws DAOException;

    /**
     * This method make a record in the DB with userdata.
     *
     * @param form {@link RegistrationForm}
     * @throws DAOException if DB query executes with errors
     */
    void registerUser(RegistrationForm form) throws DAOException;

    /**
     * This method check through DB if the e-mail is unique.
     *
     * @param email user e-mail
     * @return true if entered e-mail is unique
     * @throws DAOException if DB query executes with errors
     */
    boolean checkEmail(String email) throws DAOException;

    /**
     * This method searches through the DB the number of the specified user completed orders.
     * Discount is equal to the number of completed orders but not greater than 10%.
     * @param userID unique identifier of the user
     * @return discount value
     * @throws DAOException if DB query executes with errors
     */
    int userDiscount(int userID) throws DAOException;

    /**
     * This method searches throw the DB orders with status APPLIED made by specified user.
     *
     * @param pagination {@link Pagination}
     * @param userID unique identifier of the user
     * @return list of {@link Order} with status APPLIED
     * @throws DAOException if DB query executes with errors
     * @see Order.Status
     */
    List<Order> activeOrderList(Pagination pagination, int userID) throws DAOException;

    /**
     * This method searches throw the DB orders with status COMPLETED or CANCELLED
     * made by specified user.
     *
     * @param pagination {@link Pagination}
     * @param userID unique identifier of the user
     * @return list of {@link Order} with status CANCELLED or COMPLETED
     * @throws DAOException if DB query executes with errors
     * @see Order.Status
     */
    List<Order> orderHistoryList(Pagination pagination, int userID) throws DAOException;
}
