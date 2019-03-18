package com.epam.hotel.dao.impl;

import com.epam.hotel.dao.ParentDao;
import com.epam.hotel.dao.UserDAO;
import com.epam.hotel.dao.util.SQLConstants;
import com.epam.hotel.dao.util.SqlQuery;
import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.RegistrationForm;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.DAOException;
import com.epam.hotel.web.util.pagination.Pagination;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class UserDAOImpl extends ParentDao implements UserDAO {
    private final static Logger logger = LogManager.getLogger(UserDAOImpl.class);

    @Override
    public User loginUser(User user) throws DAOException{
        Connection connection = getConnection();

        try(PreparedStatement ps=connection.prepareStatement(SqlQuery.LOGIN)) {

            ps.setString(1, user.getEmail());

            ResultSet resultSet=ps.executeQuery();
            if (!resultSet.next()){
                user.setValid(false);
            }
            else if (BCrypt.checkpw(user.getPassword(),resultSet.getString(SQLConstants.PASSWORD))){

                user.setUserID(resultSet.getInt(SQLConstants.USER_ID));
                user.setEmail(resultSet.getString(SQLConstants.EMAIL));
                user.setRole(resultSet.getString(SQLConstants.ROLE));
                user.setName(resultSet.getString(SQLConstants.NAME));
                user.setSurname(resultSet.getString(SQLConstants.SURNAME));
                user.setPhone(resultSet.getString(SQLConstants.PHONE));
                user.setValid(true);
            }
      }catch (SQLException e){
          throw new DAOException(e);
      }finally {
            releaseConnection(connection);
        }
        return user;
    }


    @Override
    public boolean checkEmail(String email) throws DAOException{
        Connection connection=getConnection();
        ResultSet resultSet=null;
        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.CHECK_EMAIL_QUERY)){
            ps.setString(1,email);
            resultSet=ps.executeQuery();
            return !resultSet.next();
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }
    }


    @Override
    public void registerUser(RegistrationForm form) throws DAOException{
        Connection connection = getConnection();

        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.REGISTER_QUERY)){
           ps.setString(1, form.getName());
           ps.setString(2, form.getSurname());
           ps.setString(3, form.getEmail());
           ps.setString(4, form.getPhone());
           String pass=BCrypt.hashpw(form.getPassword(),BCrypt.gensalt());
           ps.setString(5, pass);
           ps.execute();

       }catch (SQLException e){
           throw new DAOException(e);
       }finally {
            releaseConnection(connection);
        }

    }

    @Override
    public int userDiscount(int userID) throws DAOException {
        Connection connection=getConnection();
        ResultSet resultSet = null;
        int discount = 0;

        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.USER_COMPLETED_ORDERS)){
            ps.setInt(1, userID);
            resultSet=ps.executeQuery();
            resultSet.next();
            discount = resultSet.getInt(SQLConstants.COUNT);

        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }
        if (discount>10){
            return 10;
        }else {
            return discount;
        }

    }

    @Override
    public List<Order> activeOrderList(Pagination pagination, int userID) throws DAOException {

        Connection connection = getConnection();
        List<Order> orderList = new ArrayList<>();
        Order order = null;
        ResultSet resultSet = null;

        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.USER_ACTIVE_ORDERS)) {
            ps.setInt(1, userID);
            ps.setInt(2, pagination.getStartPos());
            ps.setInt(3, pagination.getOffset());

            resultSet = ps.executeQuery();

            addOrderToList(resultSet, orderList);
        } catch (SQLException e) {
            logger.warn(e);
            throw new DAOException(e);
        } finally {
            releaseConnection(connection);
        }
        return orderList;
    }

    @Override
    public List<Order> orderHistoryList(Pagination pagination, int userID) throws DAOException {

        Connection connection = getConnection();
        List<Order> orderList = new ArrayList<>();
        Order order = null;
        ResultSet resultSet = null;

        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.USER_ORDERS_HISTORY)) {
            ps.setInt(1, userID);
            ps.setInt(2, pagination.getStartPos());
            ps.setInt(3, pagination.getOffset());

            resultSet = ps.executeQuery();

            addOrderToList(resultSet, orderList);

        } catch (SQLException e) {
            logger.warn(e);
            throw new DAOException(e);
        } finally {
            releaseConnection(connection);
        }
        return orderList;

    }

    private void addOrderToList(ResultSet resultSet, List<Order> orderList) throws SQLException{

        while (resultSet.next()) {
            Order order = new Order();

            order.setOrderID(resultSet.getInt(SQLConstants.ORDER_ID));
            order.setRoomID(resultSet.getInt(SQLConstants.ROOM_ID));
            order.setResFrom(new Date(resultSet.getDate(SQLConstants.RESERVED_FROM).getTime()));
            order.setResTo(new Date(resultSet.getDate(SQLConstants.RESERVED_TO).getTime()));
            order.setStatus(Order.Status.valueOf(resultSet.getString(SQLConstants.STATUS).toUpperCase()));
            order.setComment(resultSet.getString(SQLConstants.COMMENT));

            orderList.add(order);
        }
    }
}
