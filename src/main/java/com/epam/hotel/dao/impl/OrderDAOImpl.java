package com.epam.hotel.dao.impl;

import com.epam.hotel.dao.OrderDAO;
import com.epam.hotel.dao.ParentDao;
import com.epam.hotel.dao.util.SqlQuery;
import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.DAOException;

import java.sql.*;

public class OrderDAOImpl extends ParentDao implements OrderDAO {


    @Override
    public Order registeredUserBooking(Order order) throws DAOException {
        Connection connection=getConnection();
        addOrder(order,connection);
        return order;
    }

    @Override
    public Order unregisteredUserBooking(Order order, User user)throws DAOException{
        Connection connection=getConnection();

        try {
            Savepoint savepoint = connection.setSavepoint("sp1");

            if (addOrder(order,connection) && addUnregisteredUser(user, order.getOrderID(),connection)){
                connection.releaseSavepoint(savepoint);
                return order;
            }else {
                connection.rollback(savepoint);
                throw new DAOException("Adding order fail");
            }

        }catch (SQLException e){
            throw new DAOException(e);
        }

    }


    private boolean addOrder(Order order, Connection connection) throws DAOException {

        PreparedStatement preparedStatement=null;
        java.sql.Date resFrom=new java.sql.Date(order.getResFrom().getTime());
        java.sql.Date resTo=new java.sql.Date(order.getResTo().getTime());
        boolean executeSuccess = false;

        try {
            preparedStatement=connection.prepareStatement(SqlQuery.ADD_ORDER, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,order.getUserID());
            preparedStatement.setInt(2,order.getRoomID());
            preparedStatement.setDate(3, resFrom);
            preparedStatement.setDate(4,resTo);
            executeSuccess = preparedStatement.execute();

            ResultSet rs=preparedStatement.getGeneratedKeys();
            if (rs.next()){
                order.setOrderID(rs.getInt(1));
            }
            preparedStatement.close();

            return executeSuccess;

        }catch (SQLException e){
            throw new DAOException(e);
        }
    }

    private boolean addUnregisteredUser(User user, int orderID, Connection connection)throws DAOException{
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement=connection.prepareStatement(SqlQuery.ADD_UNREGISTERED_USER);
            preparedStatement.setInt(1, orderID);
            preparedStatement.setString(2,user.getName());
            preparedStatement.setString(3,user.getSurname());
            preparedStatement.setString(4,user.getPhone());
            return preparedStatement.execute();
        }catch (SQLException e){
            throw new DAOException(e);
        }
    }
}
