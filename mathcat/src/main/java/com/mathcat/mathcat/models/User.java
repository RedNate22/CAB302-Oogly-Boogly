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

    /**
     * Returns the user's username.
     *
     * @return the user's unique display name
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user's username.
     *
     * @param username the new display name
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the user's email address.
     *
     * @return the user's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     *
     * @param email the new email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the user's password.
     *
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the user's database ID.
     *
     * @return the user's unique database ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the user's database ID.
     *
     * @param id the ID assigned by the database on insert
     */
    public void setId(int id) {
        this.id = id;
    }
}
