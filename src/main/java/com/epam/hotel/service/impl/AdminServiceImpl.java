package com.epam.hotel.service.impl;

import com.epam.hotel.dao.AdminDAO;
import com.epam.hotel.dao.DaoFactory;
import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.DAOException;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.AdminService;
import com.epam.hotel.web.util.pagination.Pagination;

import java.util.List;

public class AdminServiceImpl implements AdminService {

    private AdminDAO adminDAO = DaoFactory.getInstance().getAdminDAO();

    @Override
    public List<Order> activeOrderList(Pagination pagination) throws ServiceException {
        if (pagination == null){
            throw new ServiceException("Null paginator error");
        }

        try {
            return adminDAO.activeOrderList(pagination);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> needConfirmationOrderList(Pagination pagination) throws ServiceException {
        if (pagination == null){
            throw new ServiceException("Null paginator error");
        }

        try {
            return adminDAO.needConfirmationOrderList(pagination);
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

    @Override
    public User searchUserByOrder(int orderID) throws ServiceException {
        if (orderID<=0){
            throw new ServiceException("Incorrect orderID");
        }
        try {
            return adminDAO.searchUserByOrder(orderID);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public int ordersQtyByStatus(Order.Status status) throws ServiceException {
        try {
            return adminDAO.ordersQtyByStatus(status);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }
}
