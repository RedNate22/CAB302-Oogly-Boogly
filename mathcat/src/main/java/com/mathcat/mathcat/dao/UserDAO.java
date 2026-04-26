package com.mathcat.mathcat.dao;

import com.mathcat.mathcat.models.User;
import java.util.ArrayList;

/**
 * Handles user lookup, creation, and session state using an in-memory store. Will be refactored to
 * use SQLite when the database layer is implemented.
 */
public class UserDAO {
    // Stores the list of registered users, the user that is currently being utilised and the number
    // of ID to be assigned to new users
    private static ArrayList<User> users = new ArrayList<>();
    public static User currentUser;
    public static int nextId = 1;

    /**
     * @return true if no user accounts have been created yet
     */
    public static boolean noUsersExist() {
        return UserDAO.users.isEmpty();
    }

    /**
     * Searches for a user by username.
     * 
     * @param username the username to search for
     * @return the matching User, or null if not found
     */
    public static User userMatch(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * @param matchedUser the result of a user lookup
     * @return true if no matching user was found
     */
    public static boolean noUserMatchFound(User matchedUser) {
        return (matchedUser == null);
    }

    /**
     * @param matchedUser the user to check the password against
     * @param password the password to verify
     * @return true if the password matches
     */
    public static boolean userPasswordMatch(User matchedUser, String password) {
        return (password.equals(matchedUser.getPassword()));
    }

    /**
     * Sets the currently logged in user.
     * 
     * @param user the user to set as current
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * @return the currently logged in user
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Checks if a user with the given username or email already exists.
     * 
     * @param username the username to check
     * @param email the email to check
     * @return true if a matching user exists
     */
    public static boolean userExists(String username, String email) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username) || user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a new user, assigns an ID, and sets them as the current user.
     * 
     * @param username the new user's username
     * @param email the new user's email
     * @param password the new user's password
     */
    public static void newUser(String username, String email, String password) {
        User user = new User(username, email, password);
        user.setId(nextId++);
        currentUser = user;
        users.add(user);
    }
}
