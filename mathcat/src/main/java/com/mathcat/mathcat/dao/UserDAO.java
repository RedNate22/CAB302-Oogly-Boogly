package com.mathcat.mathcat.dao;

import com.mathcat.mathcat.database.DatabaseManager;
import com.mathcat.mathcat.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for User database operations. Handles only raw SQL queries — no business
 * logic. Business rules should be handled by UserService.
 */
public final class UserDAO {
    private static final Logger log = LoggerFactory.getLogger(UserDAO.class);

    private UserDAO() {}

    /** The currently authenticated user. Null when no user is logged in. */
    public static User currentUser;

    /**
     * Sets the currently authenticated user.
     *
     * @param user the logged-in user, or null to clear the session
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
        log.debug("current user set to: {}", user != null ? user.getUsername() : "null");
    }

    /**
     * Inserts a new user into the database.
     *
     * @param user the user to save
     * @throws SQLException if the insert fails (e.g. duplicate username or email)
     */
    public static void insert(User user) throws SQLException {
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.executeUpdate();
            log.debug("inserted user: {}", user.getUsername());
        }
    }

    /**
     * Finds a user by their username.
     *
     * @param username the username to search for
     * @return the User if found, null otherwise
     * @throws SQLException if the query fails
     */
    public static User findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getString("username"), rs.getString("email"),
                        rs.getString("password"));
                user.setId(rs.getInt("id"));
                return user;
            }
        }
        return null;
    }

    /**
     * Finds a user by their email address.
     *
     * @param email the email to search for
     * @return the User if found, null otherwise
     * @throws SQLException if the query fails
     */
    public static User findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getString("username"), rs.getString("email"),
                        rs.getString("password"));
                user.setId(rs.getInt("id"));
                return user;
            }
        }
        return null;
    }

    /**
     * Returns all users from the database.
     *
     * @return list of all users
     * @throws SQLException if the query fails
     */
    public static List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Statement stmt = DatabaseManager.getConnection().createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(new User(rs.getString("username"), rs.getString("email"),
                        rs.getString("password")));
            }
        }
        return users;
    }

    /**
     * Deletes a user by their username.
     *
     * @param username the username of the user to delete
     * @throws SQLException if the delete fails
     */
    public static void deleteByUsername(String username) throws SQLException {
        String sql = "DELETE FROM users WHERE username = ?";
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
            log.debug("deleted user: {}", username);
        }
    }
}
