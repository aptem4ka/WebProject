package com.epam.hotel.dao.impl;

import com.epam.hotel.dao.AdminDAO;
import com.epam.hotel.dao.DaoFactory;
import com.epam.hotel.dao.ParentDao;
import com.epam.hotel.dao.util.SQLConstants;
import com.epam.hotel.dao.util.SqlQuery;
import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AdminDAOImpl extends ParentDao implements AdminDAO {
    private final static Logger logger = LogManager.getLogger(AdminDAOImpl.class);

    @Override
    public List<Order> activeOrderList() throws DAOException {
        Connection connection = getConnection();
        List<Order> orderList = new ArrayList<>();
        Order order = null;
        ResultSet resultSet = null;

        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.ACTIVE_ORDERS)) {

            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                order = new Order();

                order.setOrderID(resultSet.getInt(SQLConstants.ORDER_ID));
                order.setUserID(resultSet.getInt(SQLConstants.USER_ID));
                order.setRoomID(resultSet.getInt(SQLConstants.ROOM_ID));
                order.setResFrom(new Date(resultSet.getDate(SQLConstants.RESERVED_FROM).getTime()));
                order.setResTo(new Date(resultSet.getDate(SQLConstants.RESERVED_TO).getTime()));
                order.setStatus(Order.Status.valueOf(resultSet.getString(SQLConstants.STATUS).toUpperCase()));
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
        }
        }catch (SQLException e){
            logger.warn(e);
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }
        return user;
    }
}
