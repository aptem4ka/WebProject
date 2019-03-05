package com.epam.hotel.dao;

import com.epam.hotel.entity.Order;
import com.epam.hotel.exception.DAOException;

import java.util.List;

public interface AdminDAO {

    List<Order> orderList() throws DAOException;

    void updateOrderStatus(Order order) throws DAOException;
}
