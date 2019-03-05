package com.epam.hotel.dao.impl;

import com.epam.hotel.dao.ParentDao;
import com.epam.hotel.dao.UserDAO;
import com.epam.hotel.dao.util.SQLConstants;
import com.epam.hotel.dao.util.SqlQuery;
import com.epam.hotel.entity.RegistrationForm;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.DAOException;
import com.mysql.cj.protocol.Resultset;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDAOImpl extends ParentDao implements UserDAO {

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

        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.USER_APPLIED_ORDERS)){
            ps.setInt(1, userID);
            resultSet=ps.executeQuery();
            resultSet.next();
            discount = resultSet.getInt("count");

        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }

        return discount;
    }
}
