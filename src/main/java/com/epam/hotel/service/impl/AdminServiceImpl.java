package com.epam.hotel.service.impl;

import com.epam.hotel.dao.AdminDAO;
import com.epam.hotel.dao.DaoFactory;
import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.Review;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.DAOException;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.AdminService;
import com.epam.hotel.service.validation.ValidatorManager;
import com.epam.hotel.service.validation.ValidatorName;
import com.epam.hotel.web.util.pagination.Pagination;

import java.util.List;

/**
 * This {@link AdminService} implementation realizes admin activities.
 *
 * @author Artsem Lashuk
 */
public class AdminServiceImpl implements AdminService {
    private ValidatorManager validatorManager=ValidatorManager.getInstance();
    private AdminDAO adminDAO = DaoFactory.getInstance().getAdminDAO();

    /**
     * The method validates incoming data and gets list of orders with APPLIED status
     * which booking date hasn't come yet.
     *
     * @param pagination {@link Pagination}
     * @return list of orders satisfying search criteria
     * @throws ServiceException if validation fails or in case of catching DAOException
     * @see Order.Status
     */
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

    /**
     * The method validates incoming data and gets list of orders with APPLIED status
     * which booking date has come.
     *
     * @param pagination {@link Pagination}
     * @return list of orders satisfying search criteria
     * @throws ServiceException if validation fails or in case of catching DAOException
     * @see Order.Status
     */
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

    /**
     * The method validates incoming data and changes specified order status in orders DB table.
     *
     * @param order {@link Order}
     * @throws ServiceException if validation fails or in case of catching DAOException
     * @see Order.Status
     */
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

    /**
     * The method validates incoming data and searching user in the DB by specified order ID.
     *
     * @param orderID {@link Order} unique identifier
     * @return user satisfying search criteria
     * @throws ServiceException if validation fails or in case of catching DAOException
     * @see User
     */
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

    /**
     * The method validates incoming data and gets number of orders with a specified status.
     *
     * @param status {@link Order.Status}
     * @return number of orders satisfying search criteria
     * @throws ServiceException if validation fails or in case of catching DAOException
     */
    @Override
    public int ordersQtyByStatus(Order.Status status) throws ServiceException {
        try {
            return adminDAO.ordersQtyByStatus(status);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    /**
     * The method validates incoming data and searching order by user or guest nage and surname.
     *
     * @param name user or guest name
     * @param surname user or guest surname
     * @param paginator {@link Pagination}
     * @return list of orders satisfying search criteria
     * @throws ServiceException if validation fails or in case of catching DAOException
     * @see Order
     */
    @Override
    public List<Order> searchOrderByName(String name, String surname, Pagination paginator) throws ServiceException {
        if (paginator==null){
            throw new ServiceException("paginator is null");
        }
        try {
            if (validatorManager.getValidator(ValidatorName.NAME).isValid(name)
                    && validatorManager.getValidator(ValidatorName.NAME).isValid(surname)){
                return adminDAO.searchOrderByFullName(name, surname, paginator);
            }else {
                throw new ServiceException("incorrect name or surname");
            }

        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    /**
     * The method validates incoming data and searching order by phone.
     *
     * @param phone user or guest phone
     * @param paginator {@link Pagination}
     * @return list of orders satisfying search criteria
     * @throws ServiceException if validation fails or in case of catching DAOException
     * @see Order
     */
    @Override
    public List<Order> searchOrderByPhone(String phone, Pagination paginator) throws ServiceException {
        if (!validatorManager.getValidator(ValidatorName.PHONE).isValid(phone)){
            throw new ServiceException("Incorrect phone number");
        }

        try {
            if (paginator!=null){
                return adminDAO.searchOrderByPhone(phone, paginator);
            }else{
                throw new ServiceException("Null paginator");
            }

        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    /**
     * The method validates incoming data and searching user in the DB by specified ID.
     *
     * @param userID {@link User} unique identifier
     * @return user satisfying search criteria
     * @throws ServiceException if validation fails or in case of catching DAOException
     */
    @Override
    public User searchUserByID(int userID) throws ServiceException {
        if (userID<1){
            throw new ServiceException("Incorrect userID");
        }

        try {
            return adminDAO.searchUserByID(userID);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    /**
     * The method validates incoming data and changes specified review status in orders DB table.
     *
     * @param review {@link Review}
     * @throws ServiceException if validation fails or in case of catching DAOException
     * @see Review.Status
     */
    @Override
    public void updateReviewStatus(Review review) throws ServiceException {
        if (!validatorManager.getValidator(ValidatorName.REVIEW).isValid(review)){
            throw new ServiceException("Incorrect review");
        }

        try {
            adminDAO.updateReviewStatus(review);
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }

    /**
     * The method gets number of orders with status APPLIED which booking date has come.
     *
     * @return number of orders waiting for confirmation
     * @throws ServiceException if validation fails or in case of catching DAOException
     * @see Order.Status
     */
    @Override
    public int needConfirmationOrders() throws ServiceException {
        try {
            return adminDAO.needConfirmationOrders();
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }
}
