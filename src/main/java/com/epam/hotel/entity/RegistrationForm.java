package com.epam.hotel.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * This entity is used to store information from registration form.
 *
 * @author Artsem Lashuk
 */
public class RegistrationForm implements Serializable {
    private static final long serialVersionUID = 1032896608084890089L;

    /**
     * user name from the input form.
     */
    private String name;

    /**
     * user surname from the input form.
     */
    private String surname;

    /**
     * user password from the input form.
     */
    private String password;

    /**
     * repeated password from the input form.
     */
    private String confirmPassword;

    /**
     * user phone from the input form.
     */
    private String phone;

    /**
     * user e-mail from the input form.
     */
    private String email;

    public RegistrationForm() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationForm that = (RegistrationForm) o;
        return name.equals(that.name) &&
                surname.equals(that.surname) &&
                password.equals(that.password) &&
                confirmPassword.equals(that.confirmPassword) &&
                phone.equals(that.phone) &&
                email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, password, confirmPassword, phone, email);
    }

    @Override
    public String toString() {
        return "RegistrationForm{" +
                "name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", confirmPassword='" + getConfirmPassword() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}
