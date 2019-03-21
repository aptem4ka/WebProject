package com.epam.hotel.service;

import com.epam.hotel.service.impl.*;

/**
 * @author Artsem Lashuk
 */
public class ServiceFactory {
    private static final ServiceFactory instance=new ServiceFactory();

    private final UserService userService = new UserServiceImpl();
    private final RoomService roomService = new RoomServiceImpl();
    private final OrderService orderService = new OrderServiceImpl();
    private final AdminService adminService = new AdminServiceImpl();
    private final ReviewService reviewService = new ReviewServiceImpl();


    private ServiceFactory(){
    }

    /**
     * @return {@link ServiceFactory} implementation instance.
     */
    public static ServiceFactory getInstance() {
        return instance;
    }

    /**
     * @return {@link UserService} implementation instance.
     */
    public UserService getUserService(){
        return userService;
    }

    /**
     * @return {@link RoomService} implementation instance.
     */
    public RoomService getRoomService() {
        return roomService;
    }

    /**
     * @return {@link OrderService} implementation instance.
     */
    public OrderService getOrderService() {
        return orderService;
    }

    /**
     * @return {@link AdminService} implementation instance.
     */
    public AdminService getAdminService() {
        return adminService;
    }

    /**
     * @return {@link ReviewService} implementation instance.
     */
    public ReviewService getReviewService() {
        return reviewService;
    }
}
