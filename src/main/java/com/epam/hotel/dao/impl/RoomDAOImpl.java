package com.epam.hotel.dao.impl;

import com.epam.hotel.dao.ParentDao;
import com.epam.hotel.dao.RoomDAO;
import com.epam.hotel.dao.util.ConnectionManager;
import com.epam.hotel.entity.room_info.RoomType;
import com.epam.hotel.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoomDAOImpl extends ParentDao implements RoomDAO {
    private final static String ROOM_TYPES_QUERY = "SELECT DISTINCT type FROM rooms;";
    private final static String ALL_ROOM_IMAGES_QUERY = "SELECT link FROM images WHERE preview=0;";
    private final static String ROOM_IMAGES_FOR_TYPE= "SELECT link FROM images WHERE preview=0 AND roomType=?;";
    private final static String ALL_ROOM_PREVIEWS = "SELECT link FROM images WHERE preview=1;";
    private final static String MIN_AND_MAX_PRICE = "SELECT MAX(price) AS max, MIN(price) AS min FROM rooms WHERE type=?;";

    @Override
    public List<RoomType> getRoomTypes() throws DAOException {
        Connection connection = connection=getConnection();
        Statement statement=null;
        ResultSet resultSet=null;
        List<RoomType> types=new ArrayList<>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(ROOM_TYPES_QUERY);
            while (resultSet.next()){
                types.add(RoomType.valueOf(resultSet.getString("type").toUpperCase()));
            }
            System.out.println("types");
            for (RoomType type:types){

                System.out.println(type);
            }

        }catch (SQLException e){
            throw new DAOException("Get room type DAO error", e);
        }

        return types;
    }


    @Override
    public Set<String> getAllRoomImages() throws DAOException{
        Connection connection = getConnection();
        Statement statement=null;
        ResultSet resultSet=null;
        Set<String> images=new HashSet<>();
        try {
            statement=connection.createStatement();
            resultSet=statement.executeQuery(ALL_ROOM_IMAGES_QUERY);
            while (resultSet.next()){
                images.add(resultSet.getString("link"));
            }
            for (String x:images){
                System.out.println(x);
            }

        }catch (SQLException e){
            throw new DAOException("Get all room images DAO error", e);
        }
        return images;
    }

    @Override
    public List<String> getTypeRoomImages(RoomType type) throws DAOException{
        Connection connection = getConnection();
        ResultSet resultSet=null;
        List<String> images=new ArrayList<>();

        try {
            PreparedStatement preparedStatement=connection.prepareStatement(ROOM_IMAGES_FOR_TYPE);
            preparedStatement.setString(1,type.toString());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                images.add(resultSet.getString("link"));
            }

        }catch (SQLException e){
            throw new DAOException("Get type room images DAO error", e);
        }

        return images;
    }

    @Override
    public List<String> getRoomPreviews() throws DAOException{
        Connection connection = getConnection();
        Statement statement=null;
        ResultSet resultSet=null;
        List<String> previews=new ArrayList<>();
        try {
            statement=connection.createStatement();
            resultSet=statement.executeQuery(ALL_ROOM_PREVIEWS);
            while (resultSet.next()){
                previews.add(resultSet.getString("link"));
            }

        }catch (SQLException e){
            throw new DAOException("Get room previews DAO error", e);
        }
        return previews;
    }

    @Override
    public String getPriceRange(RoomType type) throws DAOException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        String priceRange=null;
        try {
            preparedStatement=connection.prepareStatement(MIN_AND_MAX_PRICE);
            preparedStatement.setString(1, type.toString());
            resultSet=preparedStatement.executeQuery();
            resultSet.next();

            String min=resultSet.getString("min");
            priceRange=min+" - "+resultSet.getString("max");

        }catch (SQLException e){
            throw new DAOException("Get price range DAO error",e);
        }
        return priceRange;
    }
}
