package com.epam.hotel.dao.impl;

import com.epam.hotel.dao.ParentDao;
import com.epam.hotel.dao.RoomDAO;
import com.epam.hotel.dao.util.SQLConstants;
import com.epam.hotel.dao.util.SqlQuery;
import com.epam.hotel.entity.Room;
import com.epam.hotel.entity.room_info.AllocationType;
import com.epam.hotel.entity.room_info.RoomType;
import com.epam.hotel.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoomDAOImpl extends ParentDao implements RoomDAO {

    @Override
    public List<RoomType> roomTypes() throws DAOException {
        Connection connection = getConnection();
        Statement statement=null;
        ResultSet resultSet=null;
        List<RoomType> types=new ArrayList<>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SqlQuery.ROOM_TYPES_QUERY);
            while (resultSet.next()){
                types.add(RoomType.valueOf(resultSet.getString(SQLConstants.ROOM_TYPE).toUpperCase()));
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }

        return types;
    }

    @Override
    public List<AllocationType> allocationsForType(RoomType type) throws DAOException {
        Connection connection=getConnection();
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        List<AllocationType> allocationList=new ArrayList<>();
        try {
            preparedStatement=connection.prepareStatement(SqlQuery.ALLOCATIONS_FOR_TYPE);
            preparedStatement.setString(1, type.toString());
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                allocationList.add(AllocationType.valueOf(resultSet.getString("allocation").toUpperCase()));
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }
        return allocationList;
    }

    @Override
    public List<AllocationType> allocationsIgnoreType() throws DAOException {
        return null;
    }

    @Override
    public Set<String> allRoomImages() throws DAOException{
        Connection connection = getConnection();
        Statement statement=null;
        ResultSet resultSet=null;
        Set<String> images=new HashSet<>();
        try {
            statement=connection.createStatement();
            resultSet=statement.executeQuery(SqlQuery.ALL_ROOM_IMAGES_QUERY);
            while (resultSet.next()){
                images.add(resultSet.getString(SQLConstants.IMAGE_LINK));
            }

        }catch (SQLException e){
            throw new DAOException(e);
        }
        return images;
    }

    @Override
    public List<String> roomImagesByType(RoomType type) throws DAOException{
        Connection connection = getConnection();
        ResultSet resultSet=null;
        List<String> images=new ArrayList<>();

        try {
            PreparedStatement preparedStatement=connection.prepareStatement(SqlQuery.ROOM_IMAGES_FOR_TYPE);
            preparedStatement.setString(1,type.toString());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                images.add(resultSet.getString(SQLConstants.IMAGE_LINK));
            }

        }catch (SQLException e){
            throw new DAOException(e);
        }

        return images;
    }


    @Override
    public List<String> roomPreviews() throws DAOException{
        Connection connection = getConnection();
        Statement statement=null;
        ResultSet resultSet=null;
        List<String> previews=new ArrayList<>();
        try {
            statement=connection.createStatement();
            resultSet=statement.executeQuery(SqlQuery.ALL_ROOM_PREVIEWS);
            while (resultSet.next()){
                previews.add(resultSet.getString(SQLConstants.IMAGE_LINK));
            }

        }catch (SQLException e){
            throw new DAOException(e);
        }
        return previews;
    }


    @Override
    public String priceRange(RoomType type) throws DAOException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        String priceRange=null;
        try {
            preparedStatement=connection.prepareStatement(SqlQuery.MIN_AND_MAX_PRICE);
            preparedStatement.setString(1, type.toString());
            resultSet=preparedStatement.executeQuery();
            resultSet.next();

            String min=resultSet.getString(SQLConstants.MIN);
            priceRange=min+" - "+resultSet.getString(SQLConstants.MAX);

        }catch (SQLException e){
            throw new DAOException(e);
        }
        return priceRange;
    }

    @Override
    public List<Room> roomSearchResult(Room room) throws DAOException {

        Connection connection=getConnection();
        try {

            if (room.getType() == null) {
                return roomsIgnoreType(connection.prepareStatement(SqlQuery.FIND_ROOM_IGNORE_TYPE), room);
            } else {
                return roomsByType(connection.prepareStatement(SqlQuery.FIND_ROOM_BY_TYPE), room);
            }

        }catch (SQLException e){
            throw new DAOException(e);
        }
    }



    public List<Room> roomsByType(PreparedStatement preparedStatement, Room room) throws DAOException {
        ResultSet resultSet=null;
        List<Room> roomList=new ArrayList<>();
        java.sql.Date resFrom = new java.sql.Date(room.getResFrom().getTime());
        java.sql.Date resTo = new java.sql.Date(room.getResTo().getTime());

        try {
            preparedStatement.setString(1, room.getType().toString());
            preparedStatement.setString(2, room.getAllocation().toString());
            preparedStatement.setDate(3, resFrom);
            preparedStatement.setDate(4, resFrom);
            preparedStatement.setDate(5, resTo);
            preparedStatement.setDate(6, resTo);
            preparedStatement.setDate(7, resFrom);
            preparedStatement.setDate(8, resTo);
            resultSet=preparedStatement.executeQuery();

            if (resultSet.next()){
                room.setRoomID(resultSet.getInt(SQLConstants.ROOM_ID));
                room.setPrice(resultSet.getDouble(SQLConstants.ROOM_PRICE));
                roomList.add(room);
            }

        }catch (SQLException e){
            throw new DAOException(e);
        }
        return roomList;
    }


    public List<Room> roomsIgnoreType(PreparedStatement preparedStatement, Room room) throws DAOException {

        ResultSet resultSet=null;
        List<Room> roomList=new ArrayList<>();
        java.sql.Date resFrom = new java.sql.Date(room.getResFrom().getTime());
        java.sql.Date resTo = new java.sql.Date(room.getResTo().getTime());

        try {
            preparedStatement.setString(1, room.getAllocation().toString());
            preparedStatement.setDate(2, resFrom);
            preparedStatement.setDate(3, resFrom);
            preparedStatement.setDate(4, resTo);
            preparedStatement.setDate(5, resTo);
            preparedStatement.setDate(6, resFrom);
            preparedStatement.setDate(7, resTo);
            resultSet=preparedStatement.executeQuery();

            while(resultSet.next()){
                room=new Room();
                room.setRoomID(resultSet.getInt(SQLConstants.ROOM_ID));
                room.setPrice(resultSet.getDouble(SQLConstants.ROOM_PRICE));
                room.setType(RoomType.valueOf(resultSet.getString(SQLConstants.ROOM_TYPE).toUpperCase()));
                room.setAllocation(AllocationType.valueOf(resultSet.getString(SQLConstants.ROOM_ALLOCATION).toUpperCase()));
                roomList.add(room);
            }

        }catch (SQLException e){
            throw new DAOException(e);
        }

        return roomList;
    }


}
