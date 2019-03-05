package com.epam.hotel.dao.impl;

import com.epam.hotel.dao.OrderDAO;
import com.epam.hotel.dao.ParentDao;
import com.epam.hotel.dao.util.SqlQuery;
import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.DAOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl extends ParentDao implements OrderDAO {


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
                order.setOrderID(resultSet.getInt("orderID"));
                order.setResFrom(new java.util.Date(resultSet.getDate("resFrom").getTime()));
                order.setResTo(new java.util.Date(resultSet.getDate("resTo").getTime()));
                order.setStatus(Order.Status.valueOf(resultSet.getString("status").toUpperCase()));
                order.setComment(resultSet.getString("comment"));
                orderList.add(order);
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }
        return orderList;
    }

    @Override
    public Order registeredUserBooking(Order order) throws DAOException {
        Connection connection=getConnection();
        addOrder(order,connection);
        return order;
    }

    @Override
    public Order unregisteredUserBooking(Order order, User user)throws DAOException{
        Connection connection=getConnection();

        if (addOrder(order,connection) && addUnregisteredUser(user, order.getOrderID(),connection)){
            releaseConnection(connection);
            return order;
        }else {
            releaseConnection(connection);
            throw new DAOException("Adding order fail");
        }
    }


    private boolean addOrder(Order order, Connection connection) throws DAOException {

        java.sql.Date resFrom=new java.sql.Date(order.getResFrom().getTime());
        java.sql.Date resTo=new java.sql.Date(order.getResTo().getTime());

        try(PreparedStatement ps=connection.prepareStatement(SqlQuery.ADD_ORDER, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1,order.getUserID());
            ps.setInt(2,order.getRoomID());
            ps.setDate(3, resFrom);
            ps.setDate(4,resTo);
            ps.execute();

            ResultSet rs=ps.getGeneratedKeys();
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
}
