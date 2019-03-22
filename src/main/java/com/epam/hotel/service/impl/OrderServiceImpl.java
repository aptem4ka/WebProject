package com.epam.hotel.service.impl;

import com.epam.hotel.dao.DaoFactory;
import com.epam.hotel.dao.OrderDAO;
import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.DAOException;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.OrderService;
import com.epam.hotel.service.validation.ValidatorManager;
import com.epam.hotel.service.validation.ValidatorName;

import java.util.List;

/**
 * This {@link OrderService} implementation realizes activities with orders
 *
 * @author Artsem Lashuk
 */
public class OrderServiceImpl implements OrderService {
    private ValidatorManager validatorManager=ValidatorManager.getInstance();
    private OrderDAO orderDAO=DaoFactory.getInstance().getOrderDAO();

    /**
     * Validates incoming data and gets booking statistics for the specified user.
     *
     * @param userID identifier of the specified user
     * @return list of orders associated with a specified user
     * @throws ServiceException if validation fails or in case of catching DAOException.
     * @see User
     */
    @Override
    public List<Order> userBookingStatistics(int userID) throws ServiceException {
        if (userID<=0){
            throw new ServiceException("Incorrect UserID");
        }

        try {
            return orderDAO.userBookingStatistics(userID);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    /**
     * Method validates incoming data and adds guest order data to the DB.
     *
     * @param order {@link Order}
     * @return the same order instance with filled {@link Order#getOrderID()} which
     *         is a primary key with auto-increment in the orders DB table
     * @throws ServiceException if validation fails or in case of catching DAOException
     */
    @Override
    public Order registeredUserBooking(Order order) throws ServiceException {
       if (!validatorManager.getValidator(ValidatorName.ORDER).isValid(order)){
           throw new ServiceException("Incorrect order");
       }

        try {
            return orderDAO.registeredUserBooking(order);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    /**
     * Method validates incoming data and adds user order data to the DB.
     *
     * @param order {@link Order}
     * @param user {@link User}
     * @return the same order instance with filled {@link Order#getOrderID()} which
     *         is a primary key with auto-increment in the orders DB table
     * @throws ServiceException if validation fails or in case of catching DAOException.
     */
    @Override
    public Order unregisteredUserBooking(Order order, User user) throws ServiceException {

        if(!validatorManager.getValidator(ValidatorName.NAME).isValid(user.getName())
                || !validatorManager.getValidator(ValidatorName.NAME).isValid(user.getSurname())){
            user.setValid(false);
            throw new ServiceException("Incorrect name or surname");
        }

        if (!validatorManager.getValidator(ValidatorName.PHONE).isValid(user.getPhone())){
            user.setValid(false);
            throw new ServiceException("Incorrect phone format");
        }

        if (!validatorManager.getValidator(ValidatorName.DATE).isValid(order.getResFrom())
                || !validatorManager.getValidator(ValidatorName.DATE).isValid(order.getResTo())
                || order.getResFrom().after(order.getResTo())|| order.getResFrom().equals(order.getResTo())){
            throw new ServiceException("Incorrect date error");
        }

        try {
            return orderDAO.unregisteredUserBooking(order,user);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    /**
     * Validates incoming data and gets price of the specified order made by specified user.
     *
     * @param userID identifier of the specified user
     * @param orderID identifier of the specified order
     * @return price value associated with a specified order
     * @throws ServiceException if validation fails or in case of catching DAOException.
     * @see User
     */
    @Override
    public double orderPrice(int userID, int orderID) throws ServiceException {
        if (userID<=0 || orderID<=0){
            throw new ServiceException("Incorrect order or user ID");
        }

        try {
            return orderDAO.orderPrice(userID, orderID);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    /**
     * Method validates incoming data and changes specified record in the DB
     * according to the order identifier.
     *
     * @param order {@link Order}
     * @throws ServiceException if validation fails or in case of catching DAOException.
     */
    @Override
    public void editOrder(Order order) throws ServiceException {

        if (!validatorManager.getValidator(ValidatorName.ORDER).isValid(order)){
            throw new ServiceException("Incorrect order");
        }

        try {
            orderDAO.editOrder(order);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }
}
