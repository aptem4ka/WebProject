package com.epam.hotel.service.impl;

import com.epam.hotel.dao.DaoFactory;
import com.epam.hotel.dao.RoomDAO;
import com.epam.hotel.entity.Room;
import com.epam.hotel.exception.DAOException;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.RoomService;
import com.epam.hotel.service.validation.ValidatorManager;
import com.epam.hotel.service.validation.ValidatorName;
import com.epam.hotel.web.util.pagination.Pagination;

import java.util.*;

/**
 * This {@link RoomService} implementation realizes activities with rooms.
 *
 * @author Artsem Lashuk
 */
public class RoomServiceImpl implements RoomService {
    private RoomDAO roomDAO = DaoFactory.getInstance().getRoomDAO();
    private ValidatorManager validatorManager=ValidatorManager.getInstance();

    /**
     * Get all available room types from DB.
     *
     * @return list of available room types.
     * @throws ServiceException if validation fails or in case of catching DAOException.
     * @see Room.RoomType
     */
    @Override
    public List<Room.RoomType> roomTypes() throws ServiceException {

        try {
            return roomDAO.roomTypes();
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    /**
     * Validate incoming data and get all available allocations for specified room type.
     *
     * @param type {@link Room.RoomType}
     * @return list of allocation types for the specified room type.
     * @throws ServiceException if validation fails or in case of catching DAOException.
     * @see Room.AllocationType
     */
    @Override
    public List<Room.AllocationType> allocationsForType(Room.RoomType type) throws ServiceException {
        if (type==null){
            throw new ServiceException("Null type error");
        }

        try {
            return roomDAO.allocationsForType(type);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    /**
     * Validate incoming data and get all available allocations for all rooms.
     *
     * @return list of allocation types for all rooms.
     * @throws ServiceException if validation fails or in case of catching DAOException.
     * @see Room.AllocationType
     */
    @Override
    public List<Room.AllocationType> allocationsIgnoreType() throws ServiceException {
        try {
            return roomDAO.allocationsIgnoreType();
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    /**
     * Get all room images from DB except preview images.
     *
     * @return set of image URLs.
     * @throws ServiceException if validation fails or in case of catching DAOException.
     */
    @Override
    public Set<String> allRoomImages() throws ServiceException {
        try {
            return roomDAO.allRoomImages();
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    /**
     * Validate incoming data and get from DB all images for specified room type
     * except previews.
     *
     * @param type {@link Room.RoomType}
     * @return list if image URLs
     * @throws ServiceException if validation fails or in case of catching DAOException.
     */
    @Override
    public List<String> roomTypeImages(Room.RoomType type) throws ServiceException {
        if (type==null){
            throw new ServiceException("Type is null");
        }
        try {
            return roomDAO.roomImagesByType(type);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    /**
     * Get pairs {@link Room.RoomType} - String where string is an image URL
     * associated with specified room type.
     *
     * @return map of room previews URl associated with room types
     * @throws ServiceException if validation fails or in case of catching DAOException.
     */
    @Override
    public Map<Room.RoomType,String> roomPreviews() throws ServiceException {
        Map<Room.RoomType,String> previews=new HashMap<>();
        List<Room.RoomType> types= roomTypes();
        List<String> previewLinks;
        try {
            previewLinks=roomDAO.roomPreviews();
        }catch (DAOException e){
            throw new ServiceException(e);
        }

        for (Room.RoomType type:types){

            for (String string: previewLinks){

                if (string.toUpperCase().contains("/"+type.toString())){
                    previews.put(type,string);
                }
            }
        }

        return previews;
    }

    /**
     * Validate input data and get price range for specified room type
     * in format "min price" - "max price".
     *
     * @param type {@link Room.RoomType}
     * @return min and max price with a hyphen as a delimiter
     * @throws ServiceException if validation fails or in case of catching DAOException.
     */
    @Override
    public String priceRange(Room.RoomType type) throws ServiceException {
        if (type==null){
            throw new ServiceException("Type is null");
        }

        try {
            return DaoFactory.getInstance().getRoomDAO().priceRange(type);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

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
    @Override
    public List<Room> roomsByRequest(Room room, Pagination pagination) throws ServiceException {
        if (room.getAllocation()==null){
            throw new ServiceException("Allocation is null");
        }
        if (pagination == null){
            throw new ServiceException("Pagination is null");
        }

        if (!validatorManager.getValidator(ValidatorName.DATE).isValid(room.getResFrom())
                || !validatorManager.getValidator(ValidatorName.DATE).isValid(room.getResTo())
                || room.getResFrom().after(room.getResTo())||room.getResFrom().equals(room.getResTo())){
            throw new ServiceException("Incorrect date error");
        }

        try {
            return DaoFactory.getInstance().getRoomDAO().roomSearchResult(room, pagination);
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }

    /**
     * Validate incoming data and get Room instance with filled fields.
     *
     * @param roomID identifier of the specified room
     * @return Room instance with filled fields
     * @throws ServiceException if validation fails or in case of catching DAOException.
     * @see Room
     */
    @Override
    public Room roomInfoByRoomID(int roomID) throws ServiceException {
        if (roomID<=0){
            throw new ServiceException("incorrect roomID");
        }

        try {
            return roomDAO.roomInfoByRoomID(roomID);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

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
    @Override
    public List<Room> changeOrderSearchResult(Room room, int orderID) throws ServiceException {
        if (room.getAllocation()==null){
            throw new ServiceException("Allocation is null");
        }

        if (!validatorManager.getValidator(ValidatorName.DATE).isValid(room.getResFrom())
        || !validatorManager.getValidator(ValidatorName.DATE).isValid(room.getResTo())
        || room.getResFrom().after(room.getResTo()) || room.getResFrom().equals(room.getResTo())){
            throw new ServiceException("Incorrect date");
        }
        if (orderID<=0){
            throw new ServiceException("Incorrect orderID");
        }

       try {
           return roomDAO.changeOrderSearchResult(room, orderID);
       }catch (DAOException e){
           throw new ServiceException(e);
       }
    }
}
