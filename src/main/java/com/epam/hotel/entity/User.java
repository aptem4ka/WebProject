package com.epam.hotel.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * This entity is used to store user information.
 *
 * @author Artsem Lashuk
 */
public class User implements Serializable {
    private static final long serialVersionUID = 5431448090527565071L;

    /**
     * User unique identifier.
     */
    private int userID;

    /**
     * User password.
     */
    private String password;

    /**
     * User e-mail.
     */
    private String email;

    /**
     * User name.
     */
    private String name;

    /**
     * User surname.
     */
    private String surname;

    /**
     * User phone.
     */
    private String phone;

    /**
     * User personal discount.
     */
    private int discount;

    /**
     * Account role.
     */
    private String role;

    /**
     * This field is used for signing in. It sets to the true if signing in successful.
     */
    private boolean valid;

    public User() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userID == user.userID &&
                valid == user.valid &&
                password.equals(user.password) &&
                email.equals(user.email) &&
                name.equals(user.name) &&
                surname.equals(user.surname) &&
                phone.equals(user.phone) &&
                role.equals(user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, password, email, name, surname, phone, role, valid);
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + getUserID() +
                ", password='" + getPassword() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", role='" + getRole() + '\'' +
                ", valid=" + isValid() +
                '}';
    }

}
