package com.epam.hotel.service.impl;

import com.epam.hotel.dao.DaoFactory;
import com.epam.hotel.entity.Room;
import com.epam.hotel.entity.room_info.RoomType;
import com.epam.hotel.exception.DAOException;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.RoomService;
import java.util.*;

public class RoomServiceImpl implements RoomService {

    @Override
    public List<RoomType> getRoomTypes() throws ServiceException {

        try {
            return DaoFactory.getInstance().getRoomDAO().getRoomTypes();
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }

    @Override
    public Set<String> getAllRoomImages() throws ServiceException {
        try {
            return DaoFactory.getInstance().getRoomDAO().getAllRoomImages();
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public List<String> getRoomTypeImages(RoomType type) throws ServiceException {
        if (type==null){
            throw new ServiceException();
        }
        try {
            return DaoFactory.getInstance().getRoomDAO().getTypeRoomImages(type);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<RoomType,String> getRoomPreviews() throws ServiceException {
        Map<RoomType,String> previews=new HashMap<>();
        List<RoomType> types=getRoomTypes();
        List<String> previewLinks;
        try {
            previewLinks=DaoFactory.getInstance().getRoomDAO().getRoomPreviews();
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
    public String getPriceRange(RoomType type) throws ServiceException {
        if (type==null){
            throw new ServiceException();
        }

        try {
            return DaoFactory.getInstance().getRoomDAO().getPriceRange(type);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Room> getRoomsByRequest(Room room) throws ServiceException {
        if (room.getAllocation()==null){
            throw new ServiceException();
        }
        if (room.getResFrom().after(room.getResTo()) ||room.getResFrom().before(new Date())){
            throw new ServiceException();
        }

        try {
            if (room.getType()==null){
                return DaoFactory.getInstance().getRoomDAO().getRoomsIgnoreType(room);
            }else {
                return DaoFactory.getInstance().getRoomDAO().getRoomsByType(room);
            }
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }
}
