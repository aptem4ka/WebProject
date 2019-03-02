package com.epam.hotel.service;

import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.ServiceException;

import java.util.List;

public interface OrderService {


    Order registeredUserBooking(Order order) throws ServiceException;

    Order unregisteredUserBooking(Order order, User user) throws ServiceException;

    List<Order> userBookingStatistics(int userID) throws ServiceException;

}
