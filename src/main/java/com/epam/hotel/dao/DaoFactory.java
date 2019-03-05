package com.epam.hotel.dao;

import com.epam.hotel.dao.impl.AdminDAOImpl;
import com.epam.hotel.dao.impl.OrderDAOImpl;
import com.epam.hotel.dao.impl.RoomDAOImpl;
import com.epam.hotel.dao.impl.UserDAOImpl;

public class DaoFactory {
    private static final DaoFactory instance=new DaoFactory();

    UserDAO userDAO =new UserDAOImpl();
    RoomDAO roomDAO=new RoomDAOImpl();
    OrderDAO orderDAO=new OrderDAOImpl();
    AdminDAO adminDAO=new AdminDAOImpl();

    private DaoFactory(){

    }

    public static DaoFactory getInstance() {
        return instance;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public RoomDAO getRoomDAO() {
        return roomDAO;
    }

    public OrderDAO getOrderDAO() {
        return orderDAO;
    }

    public AdminDAO getAdminDAO() {
        return adminDAO;
    }
}
