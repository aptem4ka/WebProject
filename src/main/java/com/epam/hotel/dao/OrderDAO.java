package com.epam.hotel.dao;

import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.Room;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.DAOException;

import java.util.List;

/**
 * This interface defines activities associated with orders.
 *
 * @author Artsem Lashuk
 * @see Order
 */
public interface OrderDAO {

    /**
     * This method adds guest order to the DB table.
     *
     * @param order order data
     * @param user this {@link User} instance contains clients name,
     *             surname and phone
     * @return order data with unique identifier which is a primary
     *         key in the DB table.
     * @throws DAOException if DB query executes with errors
     */
    Order unregisteredUserBooking(Order order, User user)throws DAOException;

    /**
     * This method adds user order to the DB table.
     *
     * @param order order data with specified user ID
     * @return order data with unique identifier which is a primary
     *         key in the DB table.
     * @throws DAOException if DB query executes with errors
     */
    Order registeredUserBooking(Order order) throws DAOException;

    /**
     * Gets booking statistics for the specified user.
     *
     * @param userID unique user identifier
     * @return list of orders associated with specified user
     * @throws DAOException if DB query executes with errors
     */
    List<Order> userBookingStatistics(int userID) throws DAOException;

    /**
     * This method gets specified order price from the DB. This order is used
     * to calculate total price in case of changing order.
     *
     * @param userID specified user identifier
     * @param orderID specified order identifier
     * @return price value
     * @throws DAOException if DB query executes with errors
     */
    double orderPrice(int userID, int orderID) throws DAOException;

    /**
     * This method edits an order record in the DB.
     *
     * @param order {@link Order}
     * @throws DAOException if DB query executes with errors
     */
    void editOrder(Order order) throws DAOException;

}
