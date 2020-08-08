package com.example.sushmita.saloon.model;

public class User {
    private String userId;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String customerOrOwner;

    public User() {
    }

    public User(String userId, String userName, String userEmail, String userPassword, String customerOrOwner) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.customerOrOwner = customerOrOwner;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getCustomerOrOwner() {
        return customerOrOwner;
    }
}
