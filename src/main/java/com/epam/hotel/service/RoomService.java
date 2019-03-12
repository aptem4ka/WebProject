package com.epam.hotel.service;

import com.epam.hotel.entity.Room;
import com.epam.hotel.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RoomService {

    List<Room.RoomType> roomTypes() throws ServiceException;

    List<Room.AllocationType> allocationsForType(Room.RoomType type) throws ServiceException;

    List<Room.AllocationType> allocationsIgnoreType() throws ServiceException;

    Set<String> allRoomImages() throws ServiceException;

    List<String> roomTypeImages(Room.RoomType type) throws ServiceException;

    Map<Room.RoomType,String> roomPreviews() throws ServiceException;

    String priceRange(Room.RoomType type) throws ServiceException;

    List<Room> roomsByRequest(Room room) throws ServiceException;

    Room roomInfoByRoomID(int roomID) throws ServiceException;

    List<Room> changeOrderSearchResult(Room room, int orderID) throws ServiceException;
}
