package com.epam.hotel.dao.impl;

import com.epam.hotel.dao.ParentDao;
import com.epam.hotel.dao.UserDAO1;
import com.epam.hotel.dao.util.EncryptMD5;
import com.epam.hotel.entity.User;
import com.epam.hotel.entity.role.AccountRole;
import com.epam.hotel.exception.DAOException;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDAOImpl extends ParentDao implements UserDAO1 {
    private static final String VALIDATE_USER_QUERY = "SELECT * FROM users WHERE  email=? AND password=?;";
    private static final String LOGIN = "SELECT * FROM users WHERE email=?;";
    private static final String CHECK_EMAIL_QUERY = "SELECT * FROM users WHERE email=?;";
    private static final String REGISTER_QUERY = "INSERT INTO users (name,surname,email,phone,password) values (?,?,?,?,?);";

    @Override
    public User loginUser(User user) throws DAOException{
      try { Connection connection = getConnection();
       PreparedStatement statement=connection.prepareStatement(LOGIN);

       statement.setString(1, user.getEmail());
      // statement.setString(2, EncryptMD5.encrypt(user.getPassword()));
        //  System.out.println(EncryptMD5.encrypt(user.getPassword()));

        ResultSet resultSet=statement.executeQuery();
        if (!resultSet.next()){
            user.setValid(false);
        }
        else if (BCrypt.checkpw(user.getPassword(),resultSet.getString("password"))){

            user.setUserID(resultSet.getInt("userID"));
            user.setEmail(resultSet.getString("email"));
            user.setRole(resultSet.getString("role"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setValid(true);
        }
      }catch (SQLException e){
          throw new DAOException("Login user DAO error", e);
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
        preparedStatement=connection.prepareStatement(CHECK_EMAIL_QUERY);
        preparedStatement.setString(1,email);

        resultSet=preparedStatement.executeQuery();

        return !resultSet.next();
        }catch (SQLException e){
            System.out.println("temp exception in userDAO checkEmail");
            throw new DAOException("Error while checking email",e);
        }
    }


    @Override
    public void registerUser(User user) throws DAOException{
       try {
           Connection connection = getConnection();
           PreparedStatement preparedStatement = connection.prepareStatement(REGISTER_QUERY);
           preparedStatement.setString(1, user.getName());
           preparedStatement.setString(2, user.getSurname());
           preparedStatement.setString(3, user.getEmail());
           preparedStatement.setString(4, user.getPhone());
           String pass=BCrypt.hashpw(user.getPassword(),BCrypt.gensalt());
           preparedStatement.setString(5, pass);
           preparedStatement.execute();



       }catch (SQLException e){
           throw new DAOException("Register user DAO error", e);
       }

    }





}
