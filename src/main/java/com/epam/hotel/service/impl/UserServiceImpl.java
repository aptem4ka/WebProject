package com.epam.hotel.service.impl;

import com.epam.hotel.dao.DaoFactory;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.DAOException;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.UserService;
import com.epam.hotel.service.validation.ValidatorManager;
import com.epam.hotel.service.validation.ValidatorName;
import com.epam.hotel.service.validation.impl.EmailValidator;
import com.epam.hotel.service.validation.impl.PasswordValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserServiceImpl implements UserService {

    @Override
    public User loginUser(User user) throws ServiceException {

        if (!new EmailValidator().isValid(user.getEmail()) || !new PasswordValidator().isValid(user.getPassword())){
            System.out.println("validation failed");
            user.setValid(false);
            return user;
        }
        System.out.println("validation succeded");
        try {
            user = DaoFactory.getInstance().getUserDAO1().loginUser(user);
        }catch (DAOException e){
            throw new ServiceException("Login service error", e);
        }
        return user;
    }



    @Override
    public boolean checkEmail(String email) throws ServiceException {
        if (!ValidatorManager.getInstance().getValidator(ValidatorName.EMAIL).isValid(email)){
            return false;
        }

        try {
            return DaoFactory.getInstance().getUserDAO1().checkEmail(email);
        }catch (DAOException e){
            throw new ServiceException("Check email service error", e);
        }
    }


    @Override
    public void registerUser(User user) throws ServiceException{
        if (user==null){
            throw new ServiceException("Tried to register null user");
        }
    try {
        DaoFactory.getInstance().getUserDAO1().registerUser(user);
    }catch (DAOException e){
        throw new ServiceException("Register service error", e);
    }
    }

    @Override
    public boolean checkRegistrationForm(User user, HttpServletRequest req) throws ServiceException {
        boolean confirmRegistration=true;
        ValidatorManager validatorManager=ValidatorManager.getInstance();
        StringBuilder builder=new StringBuilder();

        if (!checkEmail(user.getEmail())){
            builder.append("incorrectEmail=true&");
            confirmRegistration=false;
        }
        if (!user.getPassword().equals(req.getParameter("repeat"))
                || !validatorManager.getValidator(ValidatorName.PASSWORD).isValid(user.getPassword())) {
            builder.append("incorrectPassword=true&");
            confirmRegistration=false;
        }
        if (!validatorManager.getValidator(ValidatorName.NAME).isValid(user.getName())
                ||!validatorManager.getValidator(ValidatorName.NAME).isValid(user.getSurname())){
            builder.append("incorrectName=true");
            confirmRegistration=false;
        }
        req.setAttribute("formErrors",builder.toString());

        return confirmRegistration;
    }
}
