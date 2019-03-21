package com.epam.hotel.service.impl;

import com.epam.hotel.dao.DaoFactory;
import com.epam.hotel.dao.UserDAO;
import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.RegistrationForm;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.DAOException;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.UserService;
import com.epam.hotel.service.validation.Validator;
import com.epam.hotel.service.validation.ValidatorManager;
import com.epam.hotel.service.validation.ValidatorName;
import com.epam.hotel.service.validation.impl.EmailValidator;
import com.epam.hotel.service.validation.impl.PasswordValidator;
import com.epam.hotel.web.util.pagination.Pagination;

import javax.servlet.http.HttpServletRequest;
import java.security.Provider;
import java.util.List;

/**
 * This {@link UserService} implementation realizes user activities.
 *
 * @author Artsem Lashuk
 */
public class UserServiceImpl implements UserService {
    private UserDAO userDAO=DaoFactory.getInstance().getUserDAO();
    private ValidatorManager validatorManager=ValidatorManager.getInstance();

    /**
     * The method validates incoming parameter and tries to get
     * user data.
     * @param user {@link User} instance with email and password.
     * @return {@link User} instance with field field {@link User#isValid()}.
     * @throws ServiceException if validation fails or in case of catching DAOException.
     */
    @Override
    public User loginUser(User user) throws ServiceException {

        if (!validatorManager.getValidator(ValidatorName.EMAIL).isValid(user.getEmail())
                || !validatorManager.getValidator(ValidatorName.PASSWORD).isValid(user.getPassword())){
            user.setValid(false);
            return user;
        }
        try {
            user = userDAO.loginUser(user);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
        return user;
    }


    /**
     * The method validates incoming parameter and checks if
     * such e-mail exists in the DB.
     * @param email user e-mail from the input form.
     * @return true if user with such email exist.
     * @throws ServiceException if validation fails or in case of catching DAOException.
     */
    @Override
    public boolean checkEmail(String email) throws ServiceException {
        if (!validatorManager.getValidator(ValidatorName.EMAIL).isValid(email)){
            throw new ServiceException("Incorrect email");
        }

        try {
            return userDAO.checkEmail(email);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    /**
     * The method validates incoming parameter and tries to add
     * a new user to the DB.
     * @param form contains data data with input parameters from registration form.
     * @throws ServiceException if validation fails or in case of catching DAOException.
     */
    @Override
    public void registerUser(RegistrationForm form) throws ServiceException{
        if (form==null){
            throw new ServiceException("Null user");
        }
        try {
            userDAO.registerUser(form);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    /**
     * The method validates incoming form.
     * @param form an instance of {@link RegistrationForm} which contains input data
     *             from registration form.
     * @return string which contain key-value parameters with ampersand as a delimiter
     * for generating new HTTP-GET request with error message using this parameters
     * @throws ServiceException if validation fails or in case of catching DAOException.
     */
    @Override
    public String checkRegistrationForm(RegistrationForm form) throws ServiceException {

        String registerErrors="";

        if (!checkEmail(form.getEmail())){
            registerErrors+="incorrectEmail=true&";
        }

        if (!validatorManager.getValidator(ValidatorName.PASSWORD).isValid(form.getPassword())) {
            registerErrors+="incorrectPassword=true&";
        }else {
            if (!form.getPassword().equals(form.getConfirmPassword())){
                registerErrors+="passwordsMatch=false&";
            }
        }

        if (!validatorManager.getValidator(ValidatorName.NAME).isValid(form.getName())
                ||!validatorManager.getValidator(ValidatorName.NAME).isValid(form.getSurname())){
            registerErrors+="incorrectName=true&";
        }

        if (!form.getPhone().equals("")) {
            if (!validatorManager.getValidator(ValidatorName.PHONE).isValid(form.getPhone())) {
                registerErrors+="incorrectPhone=true&";
            }
        }

        return registerErrors;
        }

    /**
     * The method validates incoming parameter and tries to get
     * personal discount for {@link User} with specified ID from DB.
     *
     * @param userID unique identifier of registered user.
     * @return personal discount value from 0 to 10.
     * @throws ServiceException if validation fails or in case of catching DAOException.
     */
    @Override
    public int userDiscount(int userID) throws ServiceException {
        if (userID<=0){
            throw new ServiceException("Incorrect userID");
        }

        try {
            return userDAO.userDiscount(userID);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

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
    @Override
    public List<Order> activeOrderList(Pagination pagination, int userID) throws ServiceException {
        if (pagination == null){
            throw new ServiceException("Null paginator error");
        }
        if (userID<=0){
            throw new ServiceException("incorrect userID");
        }

        try {
            return userDAO.activeOrderList(pagination, userID);
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }

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
    @Override
    public List<Order> orderHistoryList(Pagination pagination, int userID) throws ServiceException {
        if (pagination == null){
            throw new ServiceException("Null paginator error");
        }
        if (userID<=0){
            throw new ServiceException("incorrect userID");
        }

        try {
            return userDAO.orderHistoryList(pagination, userID);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }
}
