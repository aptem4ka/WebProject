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

/**
 * This {@link UserDAO} implementation realizes DB activities associated with user.
 *
 * @author Artsem Lashuk
 * @see User
 */
public class UserDAOImpl extends ParentDao implements UserDAO {
    private final static Logger logger = LogManager.getLogger(UserDAOImpl.class);

    /**
     * This method check user data in the DB table and return user with
     * filled field {@link User#isValid()}
     *
     * @param user user that tries to sign in
     * @return user with {@link User#isValid()} true if signing in succeeded
     * @throws DAOException if DB query executes with errors
     */
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

    /**
     * This method check through DB if the e-mail is unique.
     *
     * @param email user e-mail
     * @return true if entered e-mail is unique
     * @throws DAOException if DB query executes with errors
     */
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

    /**
     * This method make a record in the DB with userdata.
     *
     * @param form {@link RegistrationForm}
     * @throws DAOException if DB query executes with errors
     */
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

    /**
     * This method searches through the DB the number of the specified user completed orders.
     * Discount is equal to the number of completed orders but not greater than 10%.
     * @param userID unique identifier of the user
     * @return discount value
     * @throws DAOException if DB query executes with errors
     */
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

    /**
     * This method searches throw the DB orders with status APPLIED made by specified user.
     *
     * @param pagination {@link Pagination}
     * @param userID unique identifier of the user
     * @return list of {@link Order} with status APPLIED
     * @throws DAOException if DB query executes with errors
     * @see Order.Status
     */
    @Override
    public List<Order> activeOrderList(Pagination pagination, int userID) throws DAOException {

        Connection connection = getConnection();
        List<Order> orderList = new ArrayList<>();

        ResultSet resultSet = null;

        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.USER_ACTIVE_ORDERS)) {
            ps.setInt(1, userID);
            ps.setInt(2, pagination.getStartPos());
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
     * This method searches throw the DB orders with status COMPLETED or CANCELLED
     * made by specified user.
     *
     * @param pagination {@link Pagination}
     * @param userID unique identifier of the user
     * @return list of {@link Order} with status CANCELLED or COMPLETED
     * @throws DAOException if DB query executes with errors
     * @see Order.Status
     */
    @Override
    public List<Order> orderHistoryList(Pagination pagination, int userID) throws DAOException {

        Connection connection = getConnection();
        List<Order> orderList = new ArrayList<>();
        ResultSet resultSet = null;

        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.USER_ORDERS_HISTORY)) {
            ps.setInt(1, userID);
            ps.setInt(2, pagination.getStartPos());
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
     * Additional method for adding order to list.
     *
     * @param resultSet the result of SQL query
     * @param orderList list of orders from the sql query
     * @throws SQLException if {@link ResultSet} handling occurs with errors.
     */
    private void addOrdersToList(ResultSet resultSet, List<Order> orderList) throws SQLException{

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
