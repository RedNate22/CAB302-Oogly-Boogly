package com.mathcat.mathcat.dao;

import com.mathcat.mathcat.models.User;

import java.util.ArrayList;

public class UserDAO {
    // Stores the list of registered users, the user that is currently being utilised and the number of ID to be assigned to new users
    public static ArrayList<User> users = new ArrayList<>();
    public static User currentUser;
    public static int Id = 1;

    /*
     * Login page methods
     */

    // Checks if any users accounts have been created yet
    public boolean NoUsersExist() {
        return UserDAO.users.isEmpty();
    }

    // Looks for user in users to see if it exists
    public User UserMatch(User matchedUser, String username) {
        for (User user : UserDAO.users) {
            if (user.getUsername().equals(username)) {
                matchedUser = user;
                break; // user found; stop searching
            }
        }
        return matchedUser;
    }

    // A user was not found if matchedUser is null
    public boolean NoUserMatchFound(User matchedUser) {
        return (matchedUser == null);
    }

    // Username has a match, but check if the password matches too
    public boolean UserPasswordMatch(User matchedUser, String password) {
        return (password.equals(matchedUser.getPassword()));
    }

    public void SetCurrentUser(User matchedUser) {
        UserDAO.currentUser = matchedUser;
    }

    /*
     * Create Account page methods
     */
    // Checks if the user exists in the users array. Ignores case sensitivity and only identifies matching characters
    public boolean UserExists(String username, String email) {
        boolean exists = false;

        for (User user : UserDAO.users) {
            if (user.getUsername().equalsIgnoreCase(username) || user.getEmail().equals(email)) {
                exists = true;
                break;
            }
        }
        return exists;
    }
    // Creates new user with inputted details, user id is set within the creation of User.
    public void NewUser(String username, String email, String password) {
        UserDAO.currentUser = new User(username, email, password);
        UserDAO.Id++;

        UserDAO.users.add(UserDAO.currentUser);
    }
}
