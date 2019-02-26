package com.epam.hotel.dao;

import com.epam.hotel.dao.impl.RoomDAOImpl;
import com.epam.hotel.dao.impl.UserDAOImpl;

public class DaoFactory {
    private static final DaoFactory instance=new DaoFactory();

    UserDAO1 userDAO1=new UserDAOImpl();
    RoomDAO roomDAO=new RoomDAOImpl();

    private DaoFactory(){

    }

    public static DaoFactory getInstance() {
        return instance;
    }

    public UserDAO1 getUserDAO1() {
        return userDAO1;
    }

    public RoomDAO getRoomDAO() {
        return roomDAO;
    }
}
