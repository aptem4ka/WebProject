package com.epam.hotel.dao;

import com.epam.hotel.dao.impl.*;

/**
 * @author Artsem Lashuk
 */
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

    /**
     * @return {@link UserDAO} implementation instance
     */
    public UserDAO getUserDAO() {
        return userDAO;
    }

    /**
     * @return {@link RoomDAO} implementation instance
     */
    public RoomDAO getRoomDAO() {
        return roomDAO;
    }

    /**
     * @return {@link OrderDAO} implementation instance
     */
    public OrderDAO getOrderDAO() {
        return orderDAO;
    }

    /**
     * @return {@link AdminDAO} implementation instance
     */
    public AdminDAO getAdminDAO() {
        return adminDAO;
    }

    /**
     * @return {@link ReviewDAO} implementation instance
     */
    public ReviewDAO getReviewDAO() {
        return reviewDAO;
    }
}
