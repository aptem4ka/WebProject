package com.epam.hotel.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * This entity is used to store information about rooms.
 *
 * @author Artsem Lashuk
 */
public class Room implements Serializable {
    private static final long serialVersionUID = 6302590932017315156L;

    /**
     * Room unique identifier
     */
    private int roomID;

    /**
     * Room type.
     */
    private RoomType type;

    /**
     * Room allocation.
     */
    private AllocationType allocation;

    /**
     * Reservation from.
     */
    private Date resFrom;

    /**
     * Reservation to.
     */
    private Date resTo;

    /**
     * Room price.
     */
    private double price;

    /**
     * Defines additional baby conts in the room.
     * This field affects price.
     */
    private int children;

    /**
     * Defines if room available for booking.
     */
    private boolean available;

    /**
     * Room window view.
     */
    private WindowView windowView;

    /**
     * Room floor.
     */
    private int floor;

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
        this.resFrom.setHours(12);
    }

    public Date getResTo() {
        return resTo;
    }

    public void setResTo(Date resTo) {
        this.resTo = resTo;
        this.resTo.setHours(12);
    }

    public double getPrice() {
        int factor=(int)price/10;

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

    public WindowView getWindowView() {
        return windowView;
    }

    public void setWindowView(WindowView windowView) {
        this.windowView = windowView;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomID == room.roomID &&
                Double.compare(room.price, price) == 0 &&
                children == room.children &&
                available == room.available &&
                type == room.type &&
                allocation == room.allocation &&
                resFrom.equals(room.resFrom) &&
                resTo.equals(room.resTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomID, type, allocation, resFrom, resTo, price, children, available);
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomID=" + getRoomID() +
                ", type=" + getType() +
                ", allocation=" + getAllocation() +
                ", resFrom=" + getResFrom() +
                ", resTo=" + getResTo() +
                ", price=" + getPrice() +
                ", children=" + getChildren() +
                ", available=" + isAvailable() +
                '}';
    }

    /**
     * This enum defines available room allocations.
     */
    public enum AllocationType {
        SNGL, DBL, TWN, TRPL, QDPL;

    }

    /**
     * This enum defines available room types.
     */
    public enum RoomType {
        STD, STUDIO, FAMSTUDIO, LUX, BUSINESS;
    }

    /**
     * This enum defines available window views.
     */
    public enum WindowView {
        CV, PV, IV
    }
}
