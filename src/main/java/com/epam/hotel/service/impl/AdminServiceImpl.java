package com.epam.hotel.service.impl;

import com.epam.hotel.dao.AdminDAO;
import com.epam.hotel.dao.DaoFactory;
import com.epam.hotel.entity.Order;
import com.epam.hotel.exception.DAOException;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.AdminService;

import java.util.List;

public class AdminServiceImpl implements AdminService {

    AdminDAO adminDAO = DaoFactory.getInstance().getAdminDAO();

    @Override
    public List<Order> orderList() throws ServiceException {
        try {
            return adminDAO.orderList();
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateOrderStatus(Order order) throws ServiceException {
        if (order.getOrderID()<=0){
            throw new ServiceException("Incorrect order ID");
        }
        if (order.getStatus() == null){
            throw new ServiceException("Update status is null");
        }

        try {
            adminDAO.updateOrderStatus(order);
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }
}
