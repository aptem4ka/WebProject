package com.epam.hotel.service;

import com.epam.hotel.service.impl.RoomServiceImpl;
import com.epam.hotel.service.impl.UserServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instance=new ServiceFactory();

    private final UserService userService=new UserServiceImpl();
    private final RoomService roomService=new RoomServiceImpl();


    private ServiceFactory(){
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    public UserService getUserService(){
        return userService;
    }

    public RoomService getRoomService() {
        return roomService;
    }
}
