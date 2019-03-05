package com.epam.hotel.dao.impl;

import com.epam.hotel.dao.AdminDAO;
import com.epam.hotel.dao.ParentDao;
import com.epam.hotel.dao.util.SqlQuery;
import com.epam.hotel.entity.Order;
import com.epam.hotel.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminDAOImpl extends ParentDao implements AdminDAO {

    @Override
    public List<Order> orderList() throws DAOException {
        List<Order> orderList=new ArrayList<>();
        Order order = null;
        Connection connection=getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement=connection.prepareStatement(SqlQuery.ALL_ORDERS);
            resultSet=preparedStatement.executeQuery();

            while (resultSet.next()){
                order = new Order();

                order.setOrderID(resultSet.getInt("orderID"));
                order.setUserID(resultSet.getInt("userID"));
                order.setRoomID(resultSet.getInt("roomID"));
                order.setResFrom(new Date(resultSet.getDate("resFrom").getTime()));
                order.setResTo(new Date(resultSet.getDate("resTo").getTime()));
                order.setStatus(Order.Status.valueOf(resultSet.getString("status").toUpperCase()));
                orderList.add(order);
            }

        }catch (SQLException e){
            throw new DAOException(e);
        }

        return orderList;
    }

    @Override
    public void updateOrderStatus(Order order) throws DAOException {

        Connection connection=getConnection();
        PreparedStatement preparedStatement = null;

        try {

            System.out.println("order status is "+ order.getStatus());
            System.out.println("order ID is "+order.getOrderID());
            System.out.println("comment is "+ order.getComment());
            preparedStatement = connection.prepareStatement(SqlQuery.UPDATE_ORDER_STATUS);
            preparedStatement.setString(1, order.getStatus().toString());
            preparedStatement.setString(2,order.getComment());
            preparedStatement.setInt(3,order.getOrderID());


            if (preparedStatement.executeUpdate()!=1){
                throw new DAOException("Updating order status error");
            }

        }catch (SQLException e){
            System.out.println("something wrong in DAO");
            throw new DAOException(e);
        }

    }
}
