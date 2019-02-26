package com.epam.hotel.dao.impl;

import com.epam.hotel.dao.ParentDao;
import com.epam.hotel.dao.UserDAO;
import com.epam.hotel.dao.util.SQLConstants;
import com.epam.hotel.dao.util.SqlQuery;
import com.epam.hotel.entity.User;
import com.epam.hotel.exception.DAOException;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDAOImpl extends ParentDao implements UserDAO {

    @Override
    public User loginUser(User user) throws DAOException{
      try { Connection connection = getConnection();
       PreparedStatement statement=connection.prepareStatement(SqlQuery.LOGIN);

       statement.setString(1, user.getEmail());


        ResultSet resultSet=statement.executeQuery();
        if (!resultSet.next()){
            user.setValid(false);
        }
        else if (BCrypt.checkpw(user.getPassword(),resultSet.getString(SQLConstants.PASSWORD))){

            user.setUserID(resultSet.getInt(SQLConstants.USER_ID));
            user.setEmail(resultSet.getString(SQLConstants.EMAIL));
            user.setRole(resultSet.getString(SQLConstants.ROLE));
            user.setName(resultSet.getString(SQLConstants.NAME));
            user.setSurname(resultSet.getString(SQLConstants.SURNAME));
            user.setValid(true);
        }
      }catch (SQLException e){
          throw new DAOException(e);
      }
        return user;
    }


    @Override
    public boolean checkEmail(String email) throws DAOException{
        Connection connection=null;
        ResultSet resultSet=null;
        PreparedStatement preparedStatement=null;

        try {
            connection=getConnection();
            preparedStatement=connection.prepareStatement(SqlQuery.CHECK_EMAIL_QUERY);
            preparedStatement.setString(1,email);

            resultSet=preparedStatement.executeQuery();

            return !resultSet.next();
        }catch (SQLException e){
            throw new DAOException(e);
        }
    }


    @Override
    public void registerUser(User user) throws DAOException{
       try {
           Connection connection = getConnection();
           PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.REGISTER_QUERY);
           preparedStatement.setString(1, user.getName());
           preparedStatement.setString(2, user.getSurname());
           preparedStatement.setString(3, user.getEmail());
           preparedStatement.setString(4, user.getPhone());
           String pass=BCrypt.hashpw(user.getPassword(),BCrypt.gensalt());
           preparedStatement.setString(5, pass);
           preparedStatement.execute();

       }catch (SQLException e){
           throw new DAOException(e);
       }

    }


}
