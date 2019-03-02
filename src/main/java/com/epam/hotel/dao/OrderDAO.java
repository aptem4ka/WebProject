package com.epam.hotel.dao;

import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.DAOException;

import java.util.List;

public interface OrderDAO {

    Order unregisteredUserBooking(Order order, User user)throws DAOException;

    Order registeredUserBooking(Order order) throws DAOException;

    List<Order> userBookingStatistics(int userID) throws DAOException;
}
