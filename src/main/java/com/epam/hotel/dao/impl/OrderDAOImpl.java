package com.epam.hotel.dao.impl;

import com.epam.hotel.dao.OrderDAO;
import com.epam.hotel.dao.ParentDao;
import com.epam.hotel.dao.util.SQLConstants;
import com.epam.hotel.dao.util.SqlQuery;
import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.DAOException;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl extends ParentDao implements OrderDAO {


    @Override
    public List<Order> userBookingStatistics(int userID) throws DAOException {
        Connection connection = getConnection();
        ResultSet resultSet = null;
        List<Order> orderList = new ArrayList<>();
        Order order;

        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-DD");

        try {
            PreparedStatement preparedStatement=connection.prepareStatement(SqlQuery.USER_ORDERS_STATISTICS);
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();
            order=new Order();
            while (resultSet.next()) {
                order.setOrderID(resultSet.getInt("orderID"));
                order.setResFrom(dateFormat.parse(resultSet.getString("resFrom")));
                order.setResTo(dateFormat.parse(resultSet.getString("resTo")));
                order.setStatus(Order.Status.valueOf(resultSet.getString("status").toUpperCase()));
                orderList.add(order);
            }

        }catch (SQLException| ParseException e){
            throw new DAOException(e);
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
                return order;
            }else {
                throw new DAOException("Adding order fail");
            }
    }


    private boolean addOrder(Order order, Connection connection) throws DAOException {

        PreparedStatement preparedStatement=null;
        java.sql.Date resFrom=new java.sql.Date(order.getResFrom().getTime());
        java.sql.Date resTo=new java.sql.Date(order.getResTo().getTime());
        boolean executeSuccess = true;

        try {
            preparedStatement=connection.prepareStatement(SqlQuery.ADD_ORDER, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,order.getUserID());
            preparedStatement.setInt(2,order.getRoomID());
            preparedStatement.setDate(3, resFrom);
            preparedStatement.setDate(4,resTo);
            preparedStatement.execute();

            ResultSet rs=preparedStatement.getGeneratedKeys();
            if (rs.next()){
                order.setOrderID(rs.getInt(1));
            }else {
                executeSuccess = false;
            }
            preparedStatement.close();
            System.out.println("add ordersuccess is "+ executeSuccess);
            return executeSuccess;

        }catch (SQLException e){
            throw new DAOException(e);
        }
    }

    private boolean addUnregisteredUser(User user, int orderID, Connection connection)throws DAOException{
        PreparedStatement preparedStatement = null;

        try {
            System.out.println("preparing statemend in unreg user");
            preparedStatement=connection.prepareStatement(SqlQuery.ADD_UNREGISTERED_USER);
            preparedStatement.setInt(1, orderID);
            preparedStatement.setString(2,user.getName());
            preparedStatement.setString(3,user.getSurname());
            preparedStatement.setString(4,user.getPhone());

            if (preparedStatement.executeUpdate()==1){
                return true;
            } else {
                return false;
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }
    }
}
