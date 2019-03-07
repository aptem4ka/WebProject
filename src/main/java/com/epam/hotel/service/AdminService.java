package com.epam.hotel.service;

import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.ServiceException;

import java.util.List;

public interface AdminService {

    List<Order> activeOrderList() throws ServiceException;

    void updateOrderStatus(Order order) throws ServiceException;

    User searchUserByOrder(int orderID) throws ServiceException;
}
