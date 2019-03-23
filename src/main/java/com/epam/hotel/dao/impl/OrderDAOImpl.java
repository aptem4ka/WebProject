package com.epam.hotel.dao.impl;

import com.epam.hotel.dao.OrderDAO;
import com.epam.hotel.dao.ParentDao;
import com.epam.hotel.dao.util.SQLConstants;
import com.epam.hotel.dao.util.SqlQuery;
import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.DAOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This {@link OrderDAO} implementation realizes activities associated with orders.
 *
 * @author Artsem Lashuk
 * @see Order
 */
public class OrderDAOImpl extends ParentDao implements OrderDAO {

    /**
     * This method adds user order to the DB table.
     *
     * @param order order data with specified user ID
     * @return order data with unique identifier which is a primary
     *         key in the DB table.
     * @throws DAOException if DB query executes with errors
     */
    @Override
    public Order registeredUserBooking(Order order) throws DAOException {
        Connection connection=getConnection();
        addOrder(order,connection);
        releaseConnection(connection);
        return order;
    }

    /**
     * This method adds guest order to the DB table.
     *
     * @param order order data
     * @param user this {@link User} instance contains clients name,
     *             surname and phone
     * @return order data with unique identifier which is a primary
     *         key in the DB table.
     * @throws DAOException if DB query executes with errors
     */
    @Override
    public Order unregisteredUserBooking(Order order, User user)throws DAOException{
        Connection connection=getConnection();

        if (addOrder(order,connection) && addUnregisteredUser(user, order.getOrderID(),connection)){
            releaseConnection(connection);
            return order;
        }else {
            releaseConnection(connection);
            throw new DAOException("Failed to add order");
        }
    }

    /**
     * This method adds an order to the DB and gets it's auto-generated identifier.
     *
     * @param order contains order params
     * @param connection {@link Connection}
     * @return true if adding order succeeded
     * @throws DAOException if DB query executes with errors
     */
    private boolean addOrder(Order order, Connection connection) throws DAOException {

        java.sql.Date resFrom=new java.sql.Date(order.getResFrom().getTime());
        java.sql.Date resTo=new java.sql.Date(order.getResTo().getTime());

        try(PreparedStatement ps=connection.prepareStatement(SqlQuery.ADD_ORDER, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1,order.getUserID());
            ps.setInt(2,order.getRoomID());
            ps.setDate(3, resFrom);
            ps.setDate(4,resTo);
            ps.setDouble(5, order.getTotalPrice());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()){
                order.setOrderID(rs.getInt(1));
                return true;
            }else {
                return false;
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }
    }

    /**
     * This method adds guest data to the DB table in case of booking by
     * unregistered user.
     *
     * @param user {@link User} with name, surname and phone
     * @param orderID order identifier associated with specified guest
     * @param connection {@link Connection}
     * @return true if adding a record is succeeded
     * @throws DAOException if DB query executes with errors
     */
    private boolean addUnregisteredUser(User user, int orderID, Connection connection)throws DAOException{

        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.ADD_UNREGISTERED_USER)){
            ps.setInt(1, orderID);
            ps.setString(2,user.getName());
            ps.setString(3,user.getSurname());
            ps.setString(4,user.getPhone());

            return ps.executeUpdate()==1;
        }catch (SQLException e){
            throw new DAOException(e);
        }
    }

    /**
     * This method gets specified order price from the DB. This order is used
     * to calculate total price in case of changing order.
     *
     * @param userID specified user identifier
     * @param orderID specified order identifier
     * @return price value
     * @throws DAOException if DB query executes with errors
     */
    @Override
    public double orderPrice(int userID, int orderID) throws DAOException {
        Connection connection = getConnection();

        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.ACTUAL_ORDER_PRICE)){
            ps.setInt(1, userID);
            ps.setInt(2, orderID);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return resultSet.getDouble(SQLConstants.ROOM_PRICE);

        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }
    }

    /**
     * This method edits an order record in the DB.
     *
     * @param order {@link Order}
     * @throws DAOException if DB query executes with errors
     */
    @Override
    public void editOrder(Order order) throws DAOException {
        Connection connection = getConnection();

        java.sql.Date resFrom=new java.sql.Date(order.getResFrom().getTime());
        java.sql.Date resTo=new java.sql.Date(order.getResTo().getTime());

        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.UPDATE_ORDER)){
            ps.setInt(1, order.getRoomID());
            ps.setDate(2, resFrom);
            ps.setDate(3, resTo);
            ps.setDouble(4, order.getTotalPrice());
            ps.setInt(5, order.getOrderID());

            if (ps.executeUpdate()!=1){
                throw new DAOException("Order edit error");
            }

        }catch (SQLException e){
            throw new DAOException(e);
        }
    }

    /**
     * Gets booking statistics for the specified user.
     *
     * @param userID unique user identifier
     * @return list of orders associated with specified user
     * @throws DAOException if DB query executes with errors
     */
    @Override
    public List<Order> userBookingStatistics(int userID) throws DAOException {
        Connection connection = getConnection();
        ResultSet resultSet = null;
        List<Order> orderList = new ArrayList<>();
        Order order;

        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.USER_ORDERS_STATISTICS)){
            ps.setInt(1, userID);
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                order=new Order();
                order.setOrderID(resultSet.getInt(SQLConstants.ORDER_ID));
                order.setResFrom(new java.util.Date(resultSet.getDate(SQLConstants.RESERVED_FROM).getTime()));
                order.setResTo(new java.util.Date(resultSet.getDate(SQLConstants.RESERVED_TO).getTime()));
                order.setStatus(Order.Status.valueOf(resultSet.getString(SQLConstants.STATUS).toUpperCase()));
                order.setComment(resultSet.getString(SQLConstants.COMMENT));
                orderList.add(order);
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }
        return orderList;
    }
}
