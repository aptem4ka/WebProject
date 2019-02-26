package com.epam.hotel.dao;

import com.epam.hotel.entity.User;
import com.epam.hotel.exception.DAOException;

import javax.servlet.http.HttpServletRequest;

public interface UserDAO {

    User loginUser(User user) throws DAOException;

    void registerUser(User user) throws DAOException;

    boolean checkEmail(String email) throws DAOException;
}
