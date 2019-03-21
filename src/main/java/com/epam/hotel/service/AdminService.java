package com.epam.hotel.service;

import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.Review;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.web.util.pagination.Pagination;

import java.util.List;

/**
 * This interface defines methods for admin activities;
 *
 * @author Artsem Lashuk
 */
public interface AdminService {

    /**
     * The method validates incoming data and gets list of orders with APPLIED status
     * which booking date hasn't come yet.
     *
     * @param pagination {@link Pagination}
     * @return list of orders satisfying search criteria
     * @throws ServiceException if validation fails or in case of catching DAOException
     * @see Order.Status
     */
    List<Order> activeOrderList(Pagination pagination) throws ServiceException;

    /**
     * The method validates incoming data and gets list of orders with APPLIED status
     * which booking date has come.
     *
     * @param pagination {@link Pagination}
     * @return list of orders satisfying search criteria
     * @throws ServiceException if validation fails or in case of catching DAOException
     * @see Order.Status
     */
    List<Order> needConfirmationOrderList(Pagination pagination) throws ServiceException;

    /**
     * The method validates incoming data and gets number of orders with a specified status.
     *
     * @param status {@link Order.Status}
     * @return number of orders satisfying search criteria
     * @throws ServiceException if validation fails or in case of catching DAOException
     */
    int ordersQtyByStatus(Order.Status status) throws ServiceException;

    /**
     * The method validates incoming data and changes specified order status in orders DB table.
     *
     * @param order {@link Order}
     * @throws ServiceException if validation fails or in case of catching DAOException
     * @see Order.Status
     */
    void updateOrderStatus(Order order) throws ServiceException;

    /**
     * The method validates incoming data and changes specified review status in orders DB table.
     *
     * @param review {@link Review}
     * @throws ServiceException if validation fails or in case of catching DAOException
     * @see Review.Status
     */
    void updateReviewStatus(Review review) throws ServiceException;

    /**
     * The method validates incoming data and searching user in the DB by specified order ID.
     *
     * @param orderID {@link Order} unique identifier
     * @return user satisfying search criteria
     * @throws ServiceException if validation fails or in case of catching DAOException
     * @see User
     */
    User searchUserByOrder(int orderID) throws ServiceException;

    /**
     * The method validates incoming data and searching user in the DB by specified ID.
     *
     * @param userID {@link User} unique identifier
     * @return user satisfying search criteria
     * @throws ServiceException if validation fails or in case of catching DAOException
     */
    User searchUserByID(int userID) throws ServiceException;

    /**
     * The method validates incoming data and searching order by user or guest nage and surname.
     *
     * @param name user or guest name
     * @param surname user or guest surname
     * @param paginator {@link Pagination}
     * @return list of orders satisfying search criteria
     * @throws ServiceException if validation fails or in case of catching DAOException
     * @see Order
     */
    List<Order> searchOrderByName(String name, String surname, Pagination paginator) throws ServiceException;

    /**
     * The method validates incoming data and searching order by phone.
     *
     * @param phone user or guest phone
     * @param paginator {@link Pagination}
     * @return list of orders satisfying search criteria
     * @throws ServiceException if validation fails or in case of catching DAOException
     * @see Order
     */
    List<Order> searchOrderByPhone(String phone, Pagination paginator) throws ServiceException;

    /**
     * The method gets number of orders with status APPLIED which booking date has come.
     *
     * @return number of orders waiting for confirmation
     * @throws ServiceException if validation fails or in case of catching DAOException
     * @see Order.Status
     */
    int needConfirmationOrders() throws ServiceException;

}
