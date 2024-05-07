package com.simpleform.simpleform.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "user_table")

public class UserModel {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
 private   Long id;
 private   String firstName;
private    String lastName;

private    String password;

 private   String emailId;
 private    String  address;
    @Transient // Exclude from database mapping
    private String confirmPassword;

    private String resetToken;

    public UserModel() {}

    public UserModel(String firstName, String lastName, String emailId, String password, String address) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.password = password;
        this.address = address;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmailId() {
        return emailId;
    }
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}

