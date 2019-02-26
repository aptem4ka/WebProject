package com.epam.hotel.dao;

import com.epam.hotel.dao.util.ConnectionManager;

import java.sql.Connection;

public abstract class ParentDao {

    public Connection getConnection(){
       Connection connection=null;
       try {
           connection = ConnectionManager.getConnection();
       }catch (Exception e){
           System.out.println("temp exception in ParentDao");
       }
       return connection;
    }
}
