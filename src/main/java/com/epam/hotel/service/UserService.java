package com.epam.hotel.service;

import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.RegistrationForm;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.web.util.pagination.Pagination;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * This interface defines methods for authorized user activities.
 *
 * @author Artsem Lashuk
 */
public interface UserService {

    /**
     * The method validates incoming parameter and tries to get
     * user data.
     * @param user {@link User} instance with email and password.
     * @return {@link User} instance with field field {@link User#isValid()}.
     * @throws ServiceException if validation fails or in case of catching DAOException.
     */
    User loginUser(User user) throws ServiceException;


    /**
     * The method validates incoming parameter and tries to add
     * a new user to the DB.
     * @param form contains data data with input parameters from registration form.
     * @throws ServiceException if validation fails or in case of catching DAOException.
     */
    void registerUser(RegistrationForm form) throws ServiceException;

    /**
     * The method validates incoming parameter and checks if
     * such e-mail exists in the DB.
     * @param email user e-mail from the input form.
     * @return true if user with such email exist.
     * @throws ServiceException if validation fails or in case of catching DAOException.
     */
    boolean checkEmail(String email) throws ServiceException;

    /**
     * The method validates incoming form.
     * @param form an instance of {@link RegistrationForm} which contains input data
     *             from registration form.
     * @return string which contain key-value parameters with ampersand as a delimiter
     * for generating new HTTP-GET request with error message using this parameters
     * @throws ServiceException if validation fails or in case of catching DAOException.
     */
    String checkRegistrationForm(RegistrationForm form) throws ServiceException;

    /**
     * The method validates incoming parameter and tries to get
     * personal discount for {@link User} with specified ID from DB.
     *
     * @param userID unique identifier of registered user.
     * @return personal discount value from 0 to 10.
     * @throws ServiceException if validation fails or in case of catching DAOException.
     */
    int userDiscount(int userID) throws ServiceException;

    /**
     * The method validates incoming parameters and tries to get active
     * orders for {@link User} with specified ID.
     *
     * @param pagination {@link Pagination}
     * @param userID unique user identifier.
     * @return list of active orders got from DB.
     * @throws ServiceException if validation fails or in case of catching DAOException.
     * @see Order
     */
    List<Order> activeOrderList(Pagination pagination, int userID) throws ServiceException;

    /**
     * The method validates incoming parameters and tries to get orders history
     * for {@link User} with specified ID.
     *
     * @param pagination {@link Pagination}
     * @param userID unique user identifier.
     * @return list with orders history got from DB.
     * @throws ServiceException if validation fails or in case of catching DAOException.
     * @see Order
     */
    List<Order> orderHistoryList(Pagination pagination, int userID) throws ServiceException;

}
