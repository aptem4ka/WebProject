package com.epam.hotel.dao;

import com.epam.hotel.dao.impl.*;

public class DaoFactory {
    private static final DaoFactory instance=new DaoFactory();

    private UserDAO userDAO = new UserDAOImpl();
    private RoomDAO roomDAO = new RoomDAOImpl();
    private OrderDAO orderDAO = new OrderDAOImpl();
    private AdminDAO adminDAO = new AdminDAOImpl();
    private ReviewDAO reviewDAO = new ReviewDAOImpl();

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

    public ReviewDAO getReviewDAO() { return reviewDAO;
    }
}
