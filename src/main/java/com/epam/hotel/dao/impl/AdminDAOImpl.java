package com.epam.hotel.dao.impl;

import com.epam.hotel.dao.AdminDAO;
import com.epam.hotel.dao.DaoFactory;
import com.epam.hotel.dao.ParentDao;
import com.epam.hotel.dao.util.SQLConstants;
import com.epam.hotel.dao.util.SqlQuery;
import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.Review;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.DAOException;
import com.epam.hotel.web.util.pagination.Pagination;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This {@link AdminDAO} implementation realizes method associated with admin.
 *
 * @author Artsem Lashuk
 */
public class AdminDAOImpl extends ParentDao implements AdminDAO {
    private final static Logger logger = LogManager.getLogger(AdminDAOImpl.class);

    /**
     * This method gets orders which reservation hasn't come yet.
     *
     * @param pagination {@link Pagination}
     * @return list of active orders
     * @throws DAOException if DB query executes with errors
     * @see Order
     */
    @Override
    public List<Order> activeOrderList(Pagination pagination) throws DAOException {
        Connection connection = getConnection();
        List<Order> orderList = new ArrayList<>();
        ResultSet resultSet = null;

        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.ACTIVE_ORDERS)) {
            ps.setDate(1, new java.sql.Date(new Date().getTime()));
            ps.setInt(2,pagination.getStartPos());
            ps.setInt(3, pagination.getOffset());
            resultSet = ps.executeQuery();

            addOrdersToList(resultSet, orderList);

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            releaseConnection(connection);
        }
        return orderList;
    }


    /**
     * This method gets orders which reservation has already come.
     *
     * @param pagination {@link Pagination}
     * @return list of orders which need confirmation
     * @throws DAOException if DB query executes with errors
     * @see Order
     */
    @Override
    public List<Order> needConfirmationOrderList(Pagination pagination) throws DAOException {
        Connection connection = getConnection();
        List<Order> orderList = new ArrayList<>();
        ResultSet resultSet = null;

        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.ORDERS_HISTORY)) {
            ps.setDate(1, new java.sql.Date(new Date().getTime()));
            ps.setInt(2,pagination.getStartPos());
            ps.setInt(3, pagination.getOffset());
            resultSet = ps.executeQuery();

            addOrdersToList(resultSet, orderList);

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            releaseConnection(connection);
        }
        return orderList;

    }

    /**
     * Change order status in the DB record.
     *
     * @param order {@link Order} that contains order ID, new status and a comment
     * @throws DAOException if DB query executes with errors
     */
    @Override
    public void updateOrderStatus(Order order) throws DAOException {
        Connection connection = getConnection();
        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.UPDATE_ORDER_STATUS)) {

            ps.setString(1, order.getStatus().toString());
            ps.setString(2, order.getComment());
            ps.setInt(3, order.getOrderID());

            if (ps.executeUpdate() != 1) {
                logger.warn("Updating error");
                throw new DAOException("Updating order status error");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            releaseConnection(connection);
        }

    }

    /**
     * Get user profile by specified order identifier.
     *
     * @param orderID identifier of the order associated with user
     * @return {@link User} instance with filled fields
     * @throws DAOException if DB query executes with errors
     */
    @Override
    public User searchUserByOrder(int orderID) throws DAOException {
        Connection connection = getConnection();
        User user = new User();
        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.REGISTERED_USER_BY_ORDER);
             PreparedStatement ps1 = connection.prepareStatement(SqlQuery.GUEST_BY_ORDER)){
            ps.setInt(1, orderID);
            ResultSet resultSet = ps.executeQuery();

        if (resultSet.next()){
            if (resultSet.getInt(SQLConstants.USER_ID)==0){
                ps1.setInt(1, orderID);
                resultSet = ps1.executeQuery();
                resultSet.next();
                user.setName(resultSet.getString(SQLConstants.NAME));
                user.setSurname(resultSet.getString(SQLConstants.SURNAME));
                user.setPhone(resultSet.getString(SQLConstants.PHONE));
            }else {
                user.setUserID(resultSet.getInt(SQLConstants.USER_ID));
                user.setName(resultSet.getString(SQLConstants.NAME));
                user.setSurname(resultSet.getString(SQLConstants.SURNAME));
                user.setEmail(resultSet.getString(SQLConstants.EMAIL));
                user.setPhone(resultSet.getString(SQLConstants.PHONE));
                int discount = DaoFactory.getInstance().getUserDAO().userDiscount(user.getUserID());
                user.setDiscount(discount);
            }
        }else {
            return null;
        }
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }
        return user;
    }

    /**
     * Get number of orders with specified status
     *
     * @param status {@link Order.Status}
     * @return number of orders
     * @throws DAOException if DB query executes with errors
     */
    @Override
    public int ordersQtyByStatus(Order.Status status) throws DAOException {
        Connection connection = getConnection();

        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.COUNT_ORDERS_BY_STATUS)){
            ps.setString(1,status.toString());
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return resultSet.getInt(SQLConstants.COUNT);

        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }

    }

    /**
     * Get orders by user or guest full name.
     *
     * @param name user or guest first name
     * @param surname user or guest surname
     * @param paginator {@link Pagination}
     * @return list of orders associated with specified full name
     * @throws DAOException if DB query executes with errors
     */
    @Override
    public List<Order> searchOrderByFullName(String name, String surname, Pagination paginator) throws DAOException {
        Connection connection = getConnection();
        List<Order> orderList = new ArrayList<>();
        Order order = null;
        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.SEARCH_ORDER_BY_FULLNAME)){
            ps.setString(1, "%"+name+"%");
            ps.setString(2, "%"+surname+"%");
            ps.setInt(3, paginator.getStartPos());
            ps.setInt(4, paginator.getOffset());
            ps.setString(5, "%"+name+"%");
            ps.setString(6, "%"+surname+"%");
            ps.setInt(7, paginator.getStartPos());
            ps.setInt(8, paginator.getOffset());

            ResultSet resultSet = ps.executeQuery();

            addOrdersToList(resultSet, orderList);

        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }

        return orderList;
    }

    /**
     *  Get orders by user or guest phone number.
     *
     * @param phone phone number associated with specified user or guest
     * @param paginator {@link Pagination}
     * @return list of orders associated with specified phone number
     * @throws DAOException if DB query executes with errors
     */
    @Override
    public List<Order> searchOrderByPhone(String phone, Pagination paginator) throws DAOException {
        Connection connection = getConnection();
        List<Order> orderList = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.SEARCH_ORDER_BY_PHONE)){
            ps.setString(1, phone);
            ps.setInt(2, paginator.getStartPos());
            ps.setInt(3, paginator.getOffset());
            ps.setString(4, phone);
            ps.setInt(5, paginator.getStartPos());
            ps.setInt(6, paginator.getOffset());
            ResultSet resultSet = ps.executeQuery();

            addOrdersToList(resultSet, orderList);

        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }
        return orderList;
    }

    /**
     * Get user profile by specified user identifier.
     *
     * @param userID user identifier
     * @return {@link User} instance with filled fields
     * @throws DAOException if DB query executes with errors
     */
    @Override
    public User searchUserByID(int userID) throws DAOException {
        Connection connection = getConnection();
        User user = new User();

        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.SEARCH_USER_BY_ID)){
            ps.setInt(1, userID);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()){
                user.setUserID(userID);
                user.setName(resultSet.getString(SQLConstants.NAME));
                user.setSurname(resultSet.getString(SQLConstants.SURNAME));
                user.setEmail(resultSet.getString(SQLConstants.EMAIL));
                user.setPhone(resultSet.getString(SQLConstants.PHONE));
                int discount = DaoFactory.getInstance().getUserDAO().userDiscount(user.getUserID());
                user.setDiscount(discount);
                return user;
            }else {
                return null;
            }

        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }
    }

    /**
     * Change review status in the DB record
     *
     * @param review {@link Review} that contains review ID, new status, and admin answer
     *
     * @throws DAOException if DB query executes with errors
     */
    @Override
    public void updateReviewStatus(Review review) throws DAOException {
        Connection connection = getConnection();

        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.UPDATE_REVIEW_STATUS)){
            ps.setString(1, review.getStatus().toString());
            ps.setString(2, review.getAnswer());
            ps.setInt(3, review.getReviewID());

            if (ps.executeUpdate()!=1){
                throw new DAOException("update review status error");
            }

        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }
    }


    /**
     * Get number of orders which reservation date has already come.
     *
     * @return number  of orders
     * @throws DAOException if DB query executes with errors
     */
    @Override
    public int needConfirmationOrders() throws DAOException {
        Connection connection = getConnection();
        int count;

        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.WAITING_FOR_CONFIRM_ORDERS)){
            ps.setDate(1, new java.sql.Date(new Date().getTime()));
            ResultSet resultSet = ps.executeQuery();

            resultSet.next();

            count = resultSet.getInt(1);
        }catch (SQLException e){
            throw new DAOException(e);
        } finally {
            releaseConnection(connection);
        }
        return count;
    }


    /**
     * This method adds orders from result set to the list
     *
     * @param resultSet {@link ResultSet}
     * @param orderList list of orders
     * @throws SQLException if Result set handling occurs with errors
     */
    private void addOrdersToList(ResultSet resultSet, List<Order> orderList) throws SQLException{

        while (resultSet.next()){
            Order order = new Order();
            order.setUserID(resultSet.getInt(SQLConstants.USER_ID));
            order.setOrderID(resultSet.getInt(SQLConstants.ORDER_ID));
            order.setRoomID(resultSet.getInt(SQLConstants.ROOM_ID));
            order.setResFrom(new Date(resultSet.getDate(SQLConstants.RESERVED_FROM).getTime()));
            order.setResTo(new Date(resultSet.getDate(SQLConstants.RESERVED_TO).getTime()));
            order.setStatus(Order.Status.valueOf(resultSet.getString(SQLConstants.STATUS).toUpperCase()));
            orderList.add(order);
        }
    }
}
