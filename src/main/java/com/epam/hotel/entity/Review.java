package com.epam.hotel.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Review implements Serializable {
    private static final long serialVersionUID = 8840948383634522642L;

    private int reviewID;
    private int userID;
    private String name;
    private String phone;
    private Date added;
    private int rating;
    private String comment;
    private Status status;
    private String answer;

    public int getReviewID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getAdded() {
        return added;
    }

    public void setAdded(Date added) {
        this.added = added;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return reviewID == review.reviewID &&
                userID == review.userID &&
                rating == review.rating &&
                name.equals(review.name) &&
                phone.equals(review.phone) &&
                added.equals(review.added) &&
                comment.equals(review.comment) &&
                status == review.status &&
                Objects.equals(answer, review.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewID, userID, name, phone, added, rating, comment, status, answer);
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewID=" + getReviewID() +
                ", userID=" + getUserID() +
                ", name='" + getName() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", added=" + getAdded() +
                ", rating=" + getRating() +
                ", comment='" + getComment() + '\'' +
                ", status=" + getStatus() +
                ", answer='" + getAnswer() + '\'' +
                '}';
    }

    public enum Status{
        WAITING, POSTED, CANCELLED;
    }

}
