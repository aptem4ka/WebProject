package com.epam.hotel.dao;

import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.RegistrationForm;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.DAOException;
import com.epam.hotel.web.util.pagination.Pagination;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserDAO {

    User loginUser(User user) throws DAOException;

    void registerUser(RegistrationForm form) throws DAOException;

    boolean checkEmail(String email) throws DAOException;

    int userDiscount(int userID) throws DAOException;

    List<Order> activeOrderList(Pagination pagination, int userID) throws DAOException;

    List<Order> orderHistoryList(Pagination pagination, int userID) throws DAOException;
}
