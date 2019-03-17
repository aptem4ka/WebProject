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

public class OrderServiceImpl implements OrderService {
    private ValidatorManager validatorManager=ValidatorManager.getInstance();
    private OrderDAO orderDAO=DaoFactory.getInstance().getOrderDAO();

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
                || order.getResFrom().after(order.getResTo())){
            throw new ServiceException("Incorrect date error");
        }

        try {
            return orderDAO.unregisteredUserBooking(order,user);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

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
