package com.epam.hotel.dao;

import com.epam.hotel.entity.Room;
import com.epam.hotel.entity.room_info.RoomType;
import com.epam.hotel.exception.DAOException;

import java.util.List;
import java.util.Set;

public interface RoomDAO {

    List<RoomType> getRoomTypes()throws DAOException;

    Set<String> getAllRoomImages() throws DAOException;

    List<String> getTypeRoomImages(RoomType type) throws DAOException;

    List<String> getRoomPreviews() throws DAOException;

    String getPriceRange(RoomType type) throws DAOException;

    List<Room> getRoomsByType(Room room) throws DAOException;

    List<Room> getRoomsIgnoreType(Room room) throws DAOException;

}
