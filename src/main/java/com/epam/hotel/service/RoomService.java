package com.epam.hotel.service;

import com.epam.hotel.entity.Room;
import com.epam.hotel.entity.room_info.RoomType;
import com.epam.hotel.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RoomService {

    List<RoomType> getRoomTypes() throws ServiceException;

    Set<String> getAllRoomImages() throws ServiceException;

    List<String> getRoomTypeImages(RoomType type) throws ServiceException;

    Map<RoomType,String> getRoomPreviews() throws ServiceException;

    String getPriceRange(RoomType type) throws ServiceException;

    List<Room> getRoomsByRequest(Room room) throws ServiceException;
}
