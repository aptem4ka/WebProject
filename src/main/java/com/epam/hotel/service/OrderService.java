package com.epam.hotel.service;

import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.ServiceException;

import java.util.List;

/**
 * This interface defines methods for activities with orders.
 *
 * @author Artsem Lashuk
 * @see Order
 */
public interface OrderService {

    /**
     * Method validates incoming data and adds guest order data to the DB.
     *
     * @param order {@link Order}
     * @return the same order instance with filled {@link Order#getOrderID()} which
     *         is a primary key with auto-increment in the orders DB table
     * @throws ServiceException if validation fails or in case of catching DAOException
     */
    Order registeredUserBooking(Order order) throws ServiceException;

    /**
     * Method validates incoming data and adds user order data to the DB.
     *
     * @param order {@link Order}
     * @param user {@link User}
     * @return the same order instance with filled {@link Order#getOrderID()} which
     *         is a primary key with auto-increment in the orders DB table
     * @throws ServiceException if validation fails or in case of catching DAOException.
     */
    Order unregisteredUserBooking(Order order, User user) throws ServiceException;

    /**
     * Validates incoming data and gets booking statistics for the specified user.
     *
     * @param userID identifier of the specified user
     * @return list of orders associated with a specified user
     * @throws ServiceException if validation fails or in case of catching DAOException.
     * @see User
     */
    List<Order> userBookingStatistics(int userID) throws ServiceException;

    /**
     * Validates incoming data and gets price of the specified order made by specified user.
     *
     * @param userID identifier of the specified user
     * @param orderID identifier of the specified order
     * @return price value associated with a specified order
     * @throws ServiceException if validation fails or in case of catching DAOException.
     * @see User
     */
    double orderPrice(int userID, int orderID) throws ServiceException;

    /**
     * Method validates incoming data and changes specified record in the DB
     * according to the order identifier.
     *
     * @param order {@link Order}
     * @throws ServiceException if validation fails or in case of catching DAOException.
     */
    void editOrder(Order order) throws ServiceException;

}
