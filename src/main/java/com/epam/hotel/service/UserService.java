package com.epam.hotel.service;

import com.epam.hotel.entity.User;
import com.epam.hotel.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    User loginUser(User user) throws ServiceException;

    void registerUser(User user) throws ServiceException;

    boolean checkEmail(String email) throws ServiceException;

    boolean checkRegistrationForm(User user, HttpServletRequest req) throws ServiceException;


}
