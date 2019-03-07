package com.epam.hotel.dao;

import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.DAOException;

import java.util.List;

public interface AdminDAO {

    List<Order> activeOrderList() throws DAOException;

    void updateOrderStatus(Order order) throws DAOException;

    User searchUserByOrder(int orderID) throws DAOException;
}
