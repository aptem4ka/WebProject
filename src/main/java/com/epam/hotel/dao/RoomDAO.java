package com.epam.hotel.dao;

import com.epam.hotel.entity.Order;
import com.epam.hotel.entity.Room;
import com.epam.hotel.exception.DAOException;
import com.epam.hotel.web.util.pagination.Pagination;

import java.util.List;
import java.util.Set;

/**
 * This interface defines DB activities associated with rooms.
 *
 * @author Artsem Lashuk
 * @see Room
 */
public interface RoomDAO {

    /**
     * Find available room types from the DB.
     *
     * @return list of room types
     * @throws DAOException if DB query executes with errors
     * @see Room.RoomType
     */
    List<Room.RoomType> roomTypes()throws DAOException;

    /**
     * Find available allocations for specified room type
     *
     * @param type {@link Room.RoomType}
     * @return list of allocation types
     * @throws DAOException if DB query executes with errors
     * @see Room.AllocationType
     */
    List<Room.AllocationType> allocationsForType(Room.RoomType type) throws DAOException;

    /**
     * Find available allocation ignoring room type
     * @return list of allocation types
     * @throws DAOException if DB query executes with errors
     * @see Room.AllocationType
     */
    List<Room.AllocationType> allocationsIgnoreType() throws DAOException;

    /**
     * Find all room images from DB except preview images
     *
     * @return set of image URLs
     * @throws DAOException if DB query executes with errors
     */
    Set<String> allRoomImages() throws DAOException;

    /**
     * Find all room images from DB associated with specified room type except preview
     *
     * @param type {@link Room.RoomType}
     * @return list of image URLs
     * @throws DAOException if DB query executes with errors
     */
    List<String> roomImagesByType(Room.RoomType type) throws DAOException;

    /**
     * Find all room preview from DB images.
     *
     * @return list of image URLs
     * @throws DAOException if DB query executes with errors
     */
    List<String> roomPreviews() throws DAOException;

    /**
     * Find min and max prices from DB for specified room type.
     *
     * @param type {@link Room.RoomType}
     * @return string in format "min_price - max_price"
     * @throws DAOException if DB query executes with errors
     * @see Room.RoomType
     */
    String priceRange(Room.RoomType type) throws DAOException;

    /**
     * Find all rooms from DB satisfying search criteria which saved in
     * the incoming parameter.
     *
     * @param room {@link Room}
     * @param pagination {@link Pagination}
     * @return list of rooms satisfying search criteria
     * @throws DAOException if DB query executes with errors
     */
    List<Room> roomSearchResult(Room room, Pagination pagination) throws DAOException;

    /**
     * Find all rooms from DB satisfying search criteria except room
     * associated with {@link Order#getOrderID()}.
     *
     * @param room {@link Room}
     * @param orderID identifier of order that will be changed
     * @return list of rooms satisfying search criteria
     * @throws DAOException if DB query executes with errors
     */
    List<Room> changeOrderSearchResult(Room room, int orderID) throws DAOException;

    /**
     * Get information about room according to the room id.
     *
     * @param roomID identifier of the room
     * @return {@link Room} with filled fields
     * @throws DAOException if DB query executes with errors
     */
    Room roomInfoByRoomID(int roomID) throws DAOException;


}
