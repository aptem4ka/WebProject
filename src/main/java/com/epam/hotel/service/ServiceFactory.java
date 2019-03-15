package com.epam.hotel.service;

import com.epam.hotel.service.impl.*;

public class ServiceFactory {
    private static final ServiceFactory instance=new ServiceFactory();

    private final UserService userService = new UserServiceImpl();
    private final RoomService roomService = new RoomServiceImpl();
    private final OrderService orderService = new OrderServiceImpl();
    private final AdminService adminService = new AdminServiceImpl();
    private final ReviewService reviewService = new ReviewServiceImpl();


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

    public OrderService getOrderService() {
        return orderService;
    }

    public AdminService getAdminService() {
        return adminService;
    }

    public ReviewService getReviewService() { return reviewService;
    }
}
