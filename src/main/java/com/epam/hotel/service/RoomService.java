package com.epam.hotel.service;

import com.epam.hotel.entity.Room;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.web.util.pagination.Pagination;


import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This interface defines methods for activities with rooms.
 *
 * @author Artsem Lashuk
 * @see Room
 */
public interface RoomService {

    /**
     * Get all available room types from DB.
     *
     * @return list of available room types.
     * @throws ServiceException if validation fails or in case of catching DAOException.
     * @see Room.RoomType
     */
    List<Room.RoomType> roomTypes() throws ServiceException;

    /**
     * Validate incoming data and get all available allocations for specified room type.
     *
     * @param type {@link Room.RoomType}
     * @return list of allocation types for the specified room type.
     * @throws ServiceException if validation fails or in case of catching DAOException.
     * @see Room.AllocationType
     */
    List<Room.AllocationType> allocationsForType(Room.RoomType type) throws ServiceException;

    /**
     * Validate incoming data and get all available allocations for all rooms.
     *
     * @return list of allocation types for all rooms.
     * @throws ServiceException if validation fails or in case of catching DAOException.
     * @see Room.AllocationType
     */
    List<Room.AllocationType> allocationsIgnoreType() throws ServiceException;

    /**
     * Get all room images from DB except preview images.
     *
     * @return set of image URLs.
     * @throws ServiceException if validation fails or in case of catching DAOException.
     */
    Set<String> allRoomImages() throws ServiceException;

    /**
     * Validate incoming data and get from DB all images for specified room type
     * except previews.
     *
     * @param type {@link Room.RoomType}
     * @return list if image URLs
     * @throws ServiceException if validation fails or in case of catching DAOException.
     */
    List<String> roomTypeImages(Room.RoomType type) throws ServiceException;

    /**
     * Get pairs {@link Room.RoomType} - String where string is an image URL
     * associated with specified room type.
     *
     * @return map of room previews URl associated with room types
     * @throws ServiceException if validation fails or in case of catching DAOException.
     */
    Map<Room.RoomType,String> roomPreviews() throws ServiceException;

    /**
     * Validate input data and get price range for specified room type
     * in format "min price" - "max price".
     *
     * @param type {@link Room.RoomType}
     * @return min and max price with a hyphen as a delimiter
     * @throws ServiceException if validation fails or in case of catching DAOException.
     */
    String priceRange(Room.RoomType type) throws ServiceException;

    /**
     * Validate incoming data and get rooms that satisfying search conditions which
     * saved in room instance.
     * Pagination is used to limit amount of data that will be received from the DB.
     *
     * @param room {@link Room} instance that contains search conditions
     * @param pagination {@link Pagination}
     * @return list of rooms satisfying search conditions
     * @throws ServiceException if validation fails or in case of catching DAOException.
     */
    List<Room> roomsByRequest(Room room, Pagination pagination) throws ServiceException;

    /**
     * Validate incoming data and get Room instance with filled fields.
     *
     * @param roomID identifier of the specified room
     * @return Room instance with filled fields
     * @throws ServiceException if validation fails or in case of catching DAOException.
     * @see Room
     */
    Room roomInfoByRoomID(int roomID) throws ServiceException;

    /**
     * Validate incoming date and and get list of rooms according to search criteria
     * considering old order data that user wants to change.
     *
     * @param room {@link Room} instance with filled fields
     * @param orderID identifier of order that user wants to edit
     * @return list of rooms that satisfying search criteria
     * @throws ServiceException if validation fails or in case of catching DAOException.
     * @see com.epam.hotel.entity.Order
     */
    List<Room> changeOrderSearchResult(Room room, int orderID) throws ServiceException;
}
