package com.mathcat.mathcat.models;

import com.mathcat.mathcat.dao.UserDAO;

public class User {
    private String username;
    private String email;
    private String password;
    private int id;

    // Sets user ID to count stored in UserDAO
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.id = UserDAO.Id;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() { return id; }
}
