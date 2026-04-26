package com.mathcat.mathcat.dao;

import com.mathcat.mathcat.database.DatabaseManager;
import com.mathcat.mathcat.models.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for User database operations.
 * Handles only raw SQL queries — no business logic.
 * Business rules should be handled by UserService.
 */
public class UserDAO {

    /**
     * Inserts a new user into the database.
     * @param user the user to save
     */
    public static void insert(User user) throws SQLException {
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.executeUpdate();
        }
    }

    /**
     * Finds a user by their username.
     * @param username the username to search for
     * @return the User if found, null otherwise
     */
    public static User findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }
        }
        return null;
    }

    /**
     * Finds a user by their email address.
     * @param email the email to search for
     * @return the User if found, null otherwise
     */
    public static User findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }
        }
        return null;
    }

    /**
     * Returns all users from the database.
     * @return list of all users
     */
    public static List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Statement stmt = DatabaseManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(new User(
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password")
                ));
            }
        }
        return users;
    }

    /**
     * Deletes a user by their username.
     * @param username the username of the user to delete
     */
    public static void deleteByUsername(String username) throws SQLException {
        String sql = "DELETE FROM users WHERE username = ?";
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
    }
}