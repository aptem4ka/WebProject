package com.epam.hotel.dao.impl;

import com.epam.hotel.dao.ParentDao;
import com.epam.hotel.dao.RoomDAO;
import com.epam.hotel.dao.util.SQLConstants;
import com.epam.hotel.dao.util.SqlQuery;
import com.epam.hotel.entity.Room;
import com.epam.hotel.exception.DAOException;
import com.epam.hotel.web.util.pagination.Pagination;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoomDAOImpl extends ParentDao implements RoomDAO {
    private final static Logger logger = LogManager.getLogger(RoomDAOImpl.class);

    @Override
    public List<Room.RoomType> roomTypes() throws DAOException {
        Connection connection = getConnection();
        List<Room.RoomType> types=new ArrayList<>();
        ResultSet resultSet=null;

        try(PreparedStatement ps = connection.prepareStatement(SqlQuery.ROOM_TYPES_QUERY)) {
            resultSet = ps.executeQuery();

            while (resultSet.next()){
                types.add(Room.RoomType.valueOf(resultSet.getString(SQLConstants.ROOM_TYPE).toUpperCase()));
            }

        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }
        return types;
    }

    @Override
    public List<Room.AllocationType> allocationsForType(Room.RoomType type) throws DAOException {
        Connection connection=getConnection();
        List<Room.AllocationType> allocationList=new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.ALLOCATIONS_FOR_TYPE)){
            ps.setString(1, type.toString());
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()){
                allocationList.add(Room.AllocationType.valueOf(resultSet.getString(SQLConstants.ROOM_ALLOCATION).toUpperCase()));
            }

        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }
        return allocationList;
    }

    @Override
    public List<Room.AllocationType> allocationsIgnoreType() throws DAOException {
        Connection connection=getConnection();
        List<Room.AllocationType> allocationList=new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.ALLOCATIONS_IGNORE_TYPE)){
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()){
                allocationList.add(Room.AllocationType.valueOf(resultSet.getString("allocation").toUpperCase()));
            }

        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }

        return allocationList;
    }

    @Override
    public Set<String> allRoomImages() throws DAOException{
        Connection connection = getConnection();
        ResultSet resultSet=null;
        Set<String> images=new HashSet<>();

        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.ALL_ROOM_IMAGES_QUERY)){
            resultSet = ps.executeQuery(SqlQuery.ALL_ROOM_IMAGES_QUERY);

            while (resultSet.next()){
                images.add(resultSet.getString(SQLConstants.IMAGE_LINK));
            }

        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }
        return images;
    }

    @Override
    public List<String> roomImagesByType(Room.RoomType type) throws DAOException{
        Connection connection = getConnection();
        List<String> images = new ArrayList<>();
        ResultSet resultSet = null;


        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.ROOM_IMAGES_FOR_TYPE)){
            ps.setString(1,type.toString());
            resultSet = ps.executeQuery();

            while (resultSet.next()){
                images.add(resultSet.getString(SQLConstants.IMAGE_LINK));
            }

        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }

        return images;
    }


    @Override
    public List<String> roomPreviews() throws DAOException{
        Connection connection = getConnection();
        List<String> previews=new ArrayList<>();
        ResultSet resultSet=null;

        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.ALL_ROOM_PREVIEWS)){
            resultSet=ps.executeQuery();

            while (resultSet.next()){
                previews.add(resultSet.getString(SQLConstants.IMAGE_LINK));
            }

        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }
        return previews;
    }


    @Override
    public String priceRange(Room.RoomType type) throws DAOException {
        Connection connection = getConnection();
        ResultSet resultSet=null;
        String priceRange=null;
        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.MIN_AND_MAX_PRICE)){
            ps.setString(1, type.toString());
            resultSet=ps.executeQuery();
            resultSet.next();
            String min=resultSet.getString(SQLConstants.MIN);
            priceRange=min+" - "+resultSet.getString(SQLConstants.MAX);

        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }
        return priceRange;
    }

    @Override
    public Room roomInfoByRoomID(int roomID) throws DAOException {
        Connection connection = getConnection();
        Room room = new Room();

        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.ROOM_INFO_BY_ROOMID)){
            ps.setInt(1, roomID);

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                room.setType(Room.RoomType.valueOf(resultSet.getString(SQLConstants.ROOM_TYPE).toUpperCase()));
                room.setAllocation(Room.AllocationType.valueOf(resultSet.getString(SQLConstants.ROOM_ALLOCATION).toUpperCase()));
                room.setFloor(resultSet.getInt(SQLConstants.FLOOR));
                room.setWindowView(Room.WindowView.valueOf(resultSet.getString(SQLConstants.VIEW).toUpperCase()));

                return room;
            }else {
                return null;
            }

        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }
    }

    @Override
    public List<Room> changeOrderSearchResult(Room room, int orderID) throws DAOException {
        Connection connection = getConnection();
        List<Room> roomList=new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SqlQuery.CHANGE_ORDER_IGNORE_TYPE)){
            Date resFrom = new Date(room.getResFrom().getTime());
            Date resTo = new Date(room.getResTo().getTime());

            ps.setString(1, room.getAllocation().toString());
            ps.setDate(2, resFrom);
            ps.setDate(3, resFrom);
            ps.setDate(4, resTo);
            ps.setDate(5, resTo);
            ps.setDate(6, resFrom);
            ps.setDate(7, resTo);
            ps.setInt(8, orderID);
            ResultSet resultSet=ps.executeQuery();

            while (resultSet.next()){
                addRoomToList(resultSet, roomList, room);
            }

        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }
        return roomList;

    }

    @Override
    public List<Room> roomSearchResult(Room room, Pagination pagination) throws DAOException {

        Connection connection=getConnection();
        try (PreparedStatement psIgnoreType = connection.prepareStatement(SqlQuery.FIND_ROOM_IGNORE_TYPE);
             PreparedStatement psByType = connection.prepareStatement(SqlQuery.FIND_ROOM_BY_TYPE))
        {
            if (room.getType() == null) {
                return roomsIgnoreType(psIgnoreType, room, pagination);
            } else {

                return roomsByType(psByType, room, pagination);
            }

        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            releaseConnection(connection);
        }
    }


    private List<Room> roomsByType(PreparedStatement preparedStatement, Room room, Pagination pagination) throws DAOException {
        List<Room> roomList=new ArrayList<>();
        ResultSet resultSet=null;
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
            preparedStatement.setInt(9, pagination.getStartPos());
            preparedStatement.setInt(10, pagination.getOffset());
            resultSet=preparedStatement.executeQuery();

            while (resultSet.next()){
                addRoomToList(resultSet, roomList, room);
            }

        }catch (SQLException e){
            throw new DAOException(e);
        }
        return roomList;
    }


    private List<Room> roomsIgnoreType(PreparedStatement preparedStatement, Room room, Pagination pagination) throws DAOException {

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
            preparedStatement.setInt(8, pagination.getStartPos());
            preparedStatement.setInt(9, pagination.getOffset());
            resultSet=preparedStatement.executeQuery();

            while (resultSet.next()){

                addRoomToList(resultSet, roomList, room);

            }

        }catch (SQLException e){
            throw new DAOException(e);
        }

        return roomList;
    }



    private void addRoomToList(ResultSet resultSet, List<Room> roomList, Room room) throws SQLException{
            room = new Room();
            room.setRoomID(resultSet.getInt(SQLConstants.ROOM_ID));
            room.setPrice(resultSet.getDouble(SQLConstants.ROOM_PRICE));
            room.setType(Room.RoomType.valueOf(resultSet.getString(SQLConstants.ROOM_TYPE).toUpperCase()));
            room.setAllocation(Room.AllocationType.valueOf(resultSet.getString(SQLConstants.ROOM_ALLOCATION).toUpperCase()));
            room.setWindowView(Room.WindowView.valueOf(resultSet.getString(SQLConstants.VIEW).toUpperCase()));
            room.setFloor(resultSet.getInt(SQLConstants.FLOOR));
            roomList.add(room);

    }
}
