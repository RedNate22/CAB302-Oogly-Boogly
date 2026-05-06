package com.mathcat.mathcat.models;

/**
 * Represents a registered user account.
 */
public class User {
    private String username;
    private String email;
    private String password;
    private int id;

    /**
     * Creates a new user with the given credentials. The {@code id} field is assigned by the
     * database on insert.
     *
     * @param username the unique display name
     * @param email the user's email address
     * @param password the user's password (stored as-is; hashing is the caller's responsibility)
     */
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
}