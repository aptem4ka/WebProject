package com.epam.hotel.service.impl;

import com.epam.hotel.dao.DaoFactory;
import com.epam.hotel.entity.Room;
import com.epam.hotel.entity.room_info.AllocationType;
import com.epam.hotel.entity.room_info.RoomType;
import com.epam.hotel.exception.DAOException;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.RoomService;
import java.util.*;

public class RoomServiceImpl implements RoomService {

    @Override
    public List<RoomType> roomTypes() throws ServiceException {

        try {
            return DaoFactory.getInstance().getRoomDAO().roomTypes();
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public List<AllocationType> allocationsForType(RoomType type) throws ServiceException {
        if (type==null){
            throw new ServiceException("Null type error");
        }

        try {
            return DaoFactory.getInstance().getRoomDAO().allocationsForType(type);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public List<AllocationType> allocationsIgnoreType() throws ServiceException {
        return null;
    }

    @Override
    public Set<String> allRoomImages() throws ServiceException {
        try {
            return DaoFactory.getInstance().getRoomDAO().allRoomImages();
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public List<String> roomTypeImages(RoomType type) throws ServiceException {
        if (type==null){
            throw new ServiceException("Type is null");
        }
        try {
            return DaoFactory.getInstance().getRoomDAO().roomImagesByType(type);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<RoomType,String> roomPreviews() throws ServiceException {
        Map<RoomType,String> previews=new HashMap<>();
        List<RoomType> types= roomTypes();
        List<String> previewLinks;
        try {
            previewLinks=DaoFactory.getInstance().getRoomDAO().roomPreviews();
        }catch (DAOException e){
            throw new ServiceException(e);
        }

        for (RoomType type:types){

            for (String string: previewLinks){

                if (string.toUpperCase().contains("/"+type.toString())){
                    previews.put(type,string);
                }
            }
        }

        return previews;
    }

    @Override
    public String priceRange(RoomType type) throws ServiceException {
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
    public List<Room> roomsByRequest(Room room) throws ServiceException {
        if (room.getAllocation()==null){
            throw new ServiceException("Allocation is null");
        }
        if (room.getResFrom().after(room.getResTo())
                || room.getResFrom().before(new Date())
                || room.getResTo().getTime()==room.getResFrom().getTime()){
            throw new ServiceException("Incorrect date");
        }

        try {
            return DaoFactory.getInstance().getRoomDAO().roomSearchResult(room);
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }
}
