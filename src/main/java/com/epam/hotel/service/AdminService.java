package com.epam.hotel.service;

import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.Review;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.web.util.pagination.Pagination;

import java.util.List;

public interface AdminService {

    List<Order> activeOrderList(Pagination pagination) throws ServiceException;

    List<Order> needConfirmationOrderList(Pagination pagination) throws ServiceException;

    int ordersQtyByStatus(Order.Status status) throws ServiceException;

    void updateOrderStatus(Order order) throws ServiceException;

    void updateReviewStatus(Review review) throws ServiceException;

    User searchUserByOrder(int orderID) throws ServiceException;

    User searchUserByID(int userID) throws ServiceException;

    List<Order> searchOrderByName(String name, String surname, Pagination paginator) throws ServiceException;

    List<Order> searchOrderByPhone(String phone, Pagination paginator) throws ServiceException;

}
