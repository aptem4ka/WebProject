package com.epam.hotel.service.impl;

import com.epam.hotel.dao.DaoFactory;
import com.epam.hotel.dao.UserDAO;
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

import javax.servlet.http.HttpServletRequest;

public class UserServiceImpl implements UserService {

    UserDAO userDAO=DaoFactory.getInstance().getUserDAO();
    ValidatorManager validatorManager=ValidatorManager.getInstance();

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
    }
