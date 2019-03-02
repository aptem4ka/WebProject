package com.epam.hotel.dao;

import com.epam.hotel.entity.Room;
import com.epam.hotel.entity.room_info.AllocationType;
import com.epam.hotel.entity.room_info.RoomType;
import com.epam.hotel.exception.DAOException;

import java.util.List;
import java.util.Set;

public interface RoomDAO {

    List<RoomType> roomTypes()throws DAOException;

    List<AllocationType> allocationsForType(RoomType type) throws DAOException;

    List<AllocationType> allocationsIgnoreType() throws DAOException;

    Set<String> allRoomImages() throws DAOException;

    List<String> roomImagesByType(RoomType type) throws DAOException;

    List<String> roomPreviews() throws DAOException;

    String priceRange(RoomType type) throws DAOException;

    List<Room> roomSearchResult(Room room) throws DAOException;

}
