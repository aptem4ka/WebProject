package com.epam.hotel.entity;

import com.epam.hotel.entity.room_info.AllocationType;
import com.epam.hotel.entity.room_info.RoomType;

import java.util.Date;

public class Room {
    private int roomID;
    private RoomType type;
    private AllocationType allocation;
    private Date resFrom;
    private Date resTo;
    private double price;
    private int children;
    private boolean available;

    public Room() {
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public AllocationType getAllocation() {
        return allocation;
    }

    public void setAllocation(AllocationType allocation) {
        this.allocation = allocation;
    }

    public Date getResFrom() {
        return resFrom;
    }

    public void setResFrom(Date resFrom) {
        this.resFrom = resFrom;
    }

    public Date getResTo() {
        return resTo;
    }

    public void setResTo(Date resTo) {
        this.resTo = resTo;
    }

    public double getPrice() {
        int factor=(int)price/5;

        return price+(factor*children);
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }
}
