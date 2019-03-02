package com.epam.hotel.service;

import com.epam.hotel.entity.Room;
import com.epam.hotel.entity.room_info.AllocationType;
import com.epam.hotel.entity.room_info.RoomType;
import com.epam.hotel.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RoomService {

    List<RoomType> roomTypes() throws ServiceException;

    List<AllocationType> allocationsForType(RoomType type) throws ServiceException;

    List<AllocationType> allocationsIgnoreType() throws ServiceException;

    Set<String> allRoomImages() throws ServiceException;

    List<String> roomTypeImages(RoomType type) throws ServiceException;

    Map<RoomType,String> roomPreviews() throws ServiceException;

    String priceRange(RoomType type) throws ServiceException;

    List<Room> roomsByRequest(Room room) throws ServiceException;
}
