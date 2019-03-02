package com.epam.hotel.dao;

import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.DAOException;

public interface OrderDAO {

    Order unregisteredUserBooking(Order order, User user)throws DAOException;

    Order registeredUserBooking(Order order) throws DAOException;
}
