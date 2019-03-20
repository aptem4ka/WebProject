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

public class RoomServiceImpl implements RoomService {
    private RoomDAO roomDAO = DaoFactory.getInstance().getRoomDAO();
    private ValidatorManager validatorManager=ValidatorManager.getInstance();

    @Override
    public List<Room.RoomType> roomTypes() throws ServiceException {

        try {
            return roomDAO.roomTypes();
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

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

    @Override
    public List<Room.AllocationType> allocationsIgnoreType() throws ServiceException {
        try {
            return roomDAO.allocationsIgnoreType();
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public Set<String> allRoomImages() throws ServiceException {
        try {
            return roomDAO.allRoomImages();
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

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
                || room.getResFrom().after(room.getResTo())){
            throw new ServiceException("Incorrect date error");
        }

        try {
            return DaoFactory.getInstance().getRoomDAO().roomSearchResult(room, pagination);
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }

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

    @Override
    public List<Room> changeOrderSearchResult(Room room, int orderID) throws ServiceException {
        if (room.getAllocation()==null){
            throw new ServiceException("Allocation is null");
        }
        if (!validatorManager.getValidator(ValidatorName.DATE).isValid(room.getResFrom())
        || !validatorManager.getValidator(ValidatorName.DATE).isValid(room.getResTo())
        || !room.getResFrom().before(room.getResTo())){
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
