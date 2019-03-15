package com.epam.hotel.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Order implements Serializable{
    private static final long serialVersionUID = -1228973035781116234L;

    private int orderID;
    private int userID;
    private int roomID;
    private Date resFrom;
    private Date resTo;
    private Status status = Status.APPLIED;
    private double totalPrice;
    private String comment;

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderID == order.orderID &&
                userID == order.userID &&
                roomID == order.roomID &&
                resFrom.equals(order.resFrom) &&
                resTo.equals(order.resTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderID, userID, roomID, resFrom, resTo);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + getOrderID() +
                ", userID=" + getUserID() +
                ", roomID=" + getRoomID() +
                ", resFrom=" + getResFrom() +
                ", resTo=" + getResTo() +
                '}';
    }

    public enum Status{
        APPLIED, ACTIVE, CANCELLED, COMPLETED;
    }

}
