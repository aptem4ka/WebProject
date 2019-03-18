package com.epam.hotel.dao;

import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.Review;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.DAOException;
import com.epam.hotel.web.util.pagination.Pagination;

import java.util.List;

public interface AdminDAO {

    List<Order> activeOrderList(Pagination pagination) throws DAOException;

    List<Order> needConfirmationOrderList(Pagination pagination) throws DAOException;

    int ordersQtyByStatus(Order.Status status) throws DAOException;

    int needConfirmationOrders() throws DAOException;

    void updateOrderStatus(Order order) throws DAOException;

    void updateReviewStatus(Review review) throws DAOException;

    User searchUserByOrder(int orderID) throws DAOException;

    User searchUserByID(int userID) throws DAOException;

    List<Order> searchOrderByFullName(String name, String surname, Pagination paginator) throws DAOException;

    List<Order> searchOrderByPhone(String phone, Pagination paginator) throws DAOException;


}
