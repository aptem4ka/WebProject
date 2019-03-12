package com.epam.hotel.service;

import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.RegistrationForm;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.web.util.pagination.Pagination;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    User loginUser(User user) throws ServiceException;

    void registerUser(RegistrationForm form) throws ServiceException;

    boolean checkEmail(String email) throws ServiceException;

    String checkRegistrationForm(RegistrationForm form) throws ServiceException;

    int userDiscount(int userID) throws ServiceException;

    List<Order> activeOrderList(Pagination pagination, int userID) throws ServiceException;

    List<Order> orderHistoryList(Pagination pagination, int userID) throws ServiceException;

}
