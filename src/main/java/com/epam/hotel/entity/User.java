package com.epam.hotel.entity;

import com.epam.hotel.entity.role.AccountRole;

import java.util.Objects;


public class User {
    private int userID;
    private String password;
    private String email;
    private String name;
    private String surname;
    private String phone;
    private String role;
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
