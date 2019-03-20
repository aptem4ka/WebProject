package com.epam.hotel.dao;

import com.epam.hotel.entity.Room;
import com.epam.hotel.exception.DAOException;
import com.epam.hotel.web.util.pagination.Pagination;

import java.util.List;
import java.util.Set;

public interface RoomDAO {

    List<Room.RoomType> roomTypes()throws DAOException;

    List<Room.AllocationType> allocationsForType(Room.RoomType type) throws DAOException;

    List<Room.AllocationType> allocationsIgnoreType() throws DAOException;

    Set<String> allRoomImages() throws DAOException;

    List<String> roomImagesByType(Room.RoomType type) throws DAOException;

    List<String> roomPreviews() throws DAOException;

    String priceRange(Room.RoomType type) throws DAOException;

    List<Room> roomSearchResult(Room room, Pagination pagination) throws DAOException;

    List<Room> changeOrderSearchResult(Room room, int orderID) throws DAOException;

    Room roomInfoByRoomID(int roomID) throws DAOException;


}
