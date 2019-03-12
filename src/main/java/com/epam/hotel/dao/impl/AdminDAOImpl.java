package com.epam.hotel.dao.impl;

import com.epam.hotel.dao.AdminDAO;
import com.epam.hotel.dao.DaoFactory;
import com.epam.hotel.dao.ParentDao;
import com.epam.hotel.dao.util.SQLConstants;
import com.epam.hotel.dao.util.SqlQuery;
import com.epam.hotel.entity.Order;
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

public class AdminDAOImpl extends ParentDao implements AdminDAO {
    private final static Logger logger = LogManager.getLogger(AdminDAOImpl.class);

    @Override
    public List<Order> activeOrderList(Pagination pagination) throws DAOException {
        Connection connection = getConnection();
        List<Order> orderList = new ArrayList<>();
        Order order = null;
        ResultSet resultSet = null;

        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.ACTIVE_ORDERS)) {
            ps.setDate(1, new java.sql.Date(new Date().getTime()));
            ps.setInt(2,pagination.getStartPos());
            ps.setInt(3, pagination.getOffset());

            System.out.println("executing query with start pos="+pagination.getStartPos()+"and offset="+pagination.getOffset());
            resultSet = ps.executeQuery();
            System.out.println("after execution query");

            while (resultSet.next()) {
                order = new Order();

                order.setOrderID(resultSet.getInt(SQLConstants.ORDER_ID));
                order.setUserID(resultSet.getInt(SQLConstants.USER_ID));
                order.setRoomID(resultSet.getInt(SQLConstants.ROOM_ID));
                order.setResFrom(new Date(resultSet.getDate(SQLConstants.RESERVED_FROM).getTime()));
                order.setResTo(new Date(resultSet.getDate(SQLConstants.RESERVED_TO).getTime()));
                order.setStatus(Order.Status.valueOf(resultSet.getString(SQLConstants.STATUS).toUpperCase()));
                //searchUserByOrder(order.getOrderID());

                orderList.add(order);
            }
        } catch (SQLException e) {
            logger.warn(e);
            throw new DAOException(e);
        } finally {
            releaseConnection(connection);
        }
        return orderList;
    }


    @Override
    public List<Order> needConfirmationOrderList(Pagination pagination) throws DAOException {
        System.out.println("entered in needconfirmationorderlist");
        Connection connection = getConnection();
        List<Order> orderList = new ArrayList<>();
        Order order = null;
        ResultSet resultSet = null;

        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.ORDERS_HISTORY)) {
            ps.setDate(1, new java.sql.Date(new Date().getTime()));
            System.out.println(pagination.getStartPos());
            ps.setInt(2,pagination.getStartPos());
            ps.setInt(3, pagination.getOffset());

            System.out.println("executing query with start pos="+pagination.getStartPos()+"and offset="+pagination.getOffset());
            resultSet = ps.executeQuery();
            System.out.println("after execution query");

            while (resultSet.next()) {
                order = new Order();

                order.setOrderID(resultSet.getInt(SQLConstants.ORDER_ID));
                order.setUserID(resultSet.getInt(SQLConstants.USER_ID));
                order.setRoomID(resultSet.getInt(SQLConstants.ROOM_ID));
                order.setResFrom(new Date(resultSet.getDate(SQLConstants.RESERVED_FROM).getTime()));
                order.setResTo(new Date(resultSet.getDate(SQLConstants.RESERVED_TO).getTime()));
                order.setStatus(Order.Status.valueOf(resultSet.getString(SQLConstants.STATUS).toUpperCase()));
                //searchUserByOrder(order.getOrderID());

                orderList.add(order);
            }
        } catch (SQLException e) {
            logger.warn(e);
            throw new DAOException(e);
        } finally {
            releaseConnection(connection);
        }
        return orderList;

    }

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
            logger.warn(e);
            throw new DAOException(e);
        } finally {
            releaseConnection(connection);
        }

    }

    @Override
    public User searchUserByOrder(int orderID) throws DAOException {
        Connection connection = getConnection();
        User user = new User();
        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.REGISTERED_USER_BY_ORDER);
             PreparedStatement ps1 = connection.prepareStatement(SqlQuery.GUEST_BY_ORDER)){
            ps.setInt(1, orderID);
            ResultSet resultSet = ps.executeQuery();

        if (resultSet.next()){
            if (resultSet.getInt("userID")==0){
                ps1.setInt(1, orderID);
                resultSet = ps1.executeQuery();
                resultSet.next();
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setPhone(resultSet.getString("phone"));
            }else {
                user.setUserID(resultSet.getInt("userID"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone(resultSet.getString("phone"));
                int discount = DaoFactory.getInstance().getUserDAO().userDiscount(user.getUserID());
                user.setDiscount(discount);
            }
        }else {
            return null;
        }
        }catch (SQLException e){
            logger.warn(e);
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }
        return user;
    }

    @Override
    public int ordersQtyByStatus(Order.Status status) throws DAOException {
        Connection connection = getConnection();

        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.COUNT_ORDERS_BY_STATUS)){
            ps.setString(1,status.toString());
            ResultSet resultSet = ps.executeQuery();

            resultSet.next();

            return resultSet.getInt("count");

        }catch (SQLException e){
            logger.warn(e);
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }

    }
}
