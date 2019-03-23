package com.epam.hotel.dao;

import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.Review;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.DAOException;
import com.epam.hotel.web.util.pagination.Pagination;

import java.util.List;

/**
 * This interface defines methods associated with admin.
 *
 * @author Artsem lashuk
 */
public interface AdminDAO {

    /**
     * This method gets orders which reservation hasn't come yet.
     *
     * @param pagination {@link Pagination}
     * @return list of active orders
     * @throws DAOException if DB query executes with errors
     * @see Order
     */
    List<Order> activeOrderList(Pagination pagination) throws DAOException;

    /**
     * This method gets orders which reservation has already come.
     *
     * @param pagination {@link Pagination}
     * @return list of orders which need confirmation
     * @throws DAOException if DB query executes with errors
     * @see Order
     */
    List<Order> needConfirmationOrderList(Pagination pagination) throws DAOException;

    /**
     * Get number of orders with specified status
     *
     * @param status {@link Order.Status}
     * @return number of orders
     * @throws DAOException if DB query executes with errors
     */
    int ordersQtyByStatus(Order.Status status) throws DAOException;

    /**
     * Get number of orders which reservation date has already come.
     *
     * @return number  of orders
     * @throws DAOException if DB query executes with errors
     */
    int needConfirmationOrders() throws DAOException;

    /**
     * Change order status in the DB record.
     *
     * @param order {@link Order} that contains order ID, new status and a comment
     * @throws DAOException if DB query executes with errors
     */
    void updateOrderStatus(Order order) throws DAOException;

    /**
     * Change review status in the DB record
     *
     * @param review {@link Review} that contains review ID, new status, and admin answer
     *
     * @throws DAOException if DB query executes with errors
     */
    void updateReviewStatus(Review review) throws DAOException;

    /**
     * Get user profile by specified order identifier.
     *
     * @param orderID identifier of the order associated with user
     * @return {@link User} instance with filled fields
     * @throws DAOException if DB query executes with errors
     */
    User searchUserByOrder(int orderID) throws DAOException;

    /**
     * Get user profile by specified user identifier.
     *
     * @param userID user identifier
     * @return {@link User} instance with filled fields
     * @throws DAOException if DB query executes with errors
     */
    User searchUserByID(int userID) throws DAOException;

    /**
     * Get orders by user or guest full name.
     *
     * @param name user or guest first name
     * @param surname user or guest surname
     * @param paginator {@link Pagination}
     * @return list of orders associated with specified full name
     * @throws DAOException if DB query executes with errors
     */
    List<Order> searchOrderByFullName(String name, String surname, Pagination paginator) throws DAOException;

    /**
     *  Get orders by user or guest phone number.
     *
     * @param phone phone number associated with specified user or guest
     * @param paginator {@link Pagination}
     * @return list of orders associated with specified phone number
     * @throws DAOException if DB query executes with errors
     */
    List<Order> searchOrderByPhone(String phone, Pagination paginator) throws DAOException;


}
