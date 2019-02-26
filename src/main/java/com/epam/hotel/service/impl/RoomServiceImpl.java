package com.epam.hotel.service.impl;

import com.epam.hotel.dao.DaoFactory;
import com.epam.hotel.entity.Room;
import com.epam.hotel.entity.room_info.AllocationType;
import com.epam.hotel.entity.room_info.RoomType;
import com.epam.hotel.exception.DAOException;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.RoomService;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RoomServiceImpl implements RoomService {

    @Override
    public List<RoomType> getRoomTypes() throws ServiceException {

        try {
            return DaoFactory.getInstance().getRoomDAO().getRoomTypes();
        }catch (DAOException e){
            throw new ServiceException("Get room types service error", e);
        }

    }

    @Override
    public Set<String> getAllRoomImages() throws ServiceException {
        try {
            return DaoFactory.getInstance().getRoomDAO().getAllRoomImages();
        }catch (DAOException e){
            throw new ServiceException("Get all room images Service error", e);
        }
    }

    @Override
    public List<String> getRoomTypeImages(RoomType type) throws ServiceException {
        if (type==null){
            throw new ServiceException("Null room type");
        }
        try {
            return DaoFactory.getInstance().getRoomDAO().getTypeRoomImages(type);
        }catch (DAOException e){
            throw new ServiceException("Get room type images service error", e);
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
            throw new ServiceException("Get room previews service error", e);
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
            throw new ServiceException("Null room type");
        }

        try {
            return DaoFactory.getInstance().getRoomDAO().getPriceRange(type);
        }catch (DAOException e){
            throw new ServiceException("Get price range service error", e);
        }
    }

    @Override
    public List<Room> getRoomsByRequest(Room room) throws ServiceException {



        return null;
    }
}
