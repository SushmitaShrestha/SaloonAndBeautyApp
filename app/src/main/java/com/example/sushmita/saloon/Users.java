package com.example.sushmita.saloon;

/**
 * Sushmita Shrestha -2019445
 */
public class Users {

    String id;
    String email;
    String password;

    public Users(){

    }

    public Users(String id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
