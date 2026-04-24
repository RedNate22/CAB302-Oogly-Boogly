package com.mathcat.mathcat.dao;

import com.mathcat.mathcat.session.UserSession;
import com.mathcat.mathcat.models.User;

public class UserDAO {
    // Login methods
    public boolean fieldsEmpty(String username, String password) {
        return (username.isEmpty() || password.isEmpty());
    }

    public boolean noUsersExist() {
        return userSession.users.isEmpty();
    }

    public User userMatch(User matchedUser, String username) {
        for (User user : userSession.users) {
            if (user.getUsername().equals(username)) {
                matchedUser = user;
                break; // user found; stop searching
            }
        }
        return matchedUser;
    }

    public boolean noUserMatchFound(User matchedUser) {
        return (matchedUser == null);
    }

    public boolean userPasswordMatch(User matchedUser, String password) {
        return (password.equals(matchedUser.getPassword()));
    }

    public void setCurrentUser(User matchedUser) {
        userSession.currentUser = matchedUser;
    }

    /*
     * // Create Account methods
     */
    public boolean fieldsEmpty(String username, String email, String password) {
        return (username.isEmpty() || email.isEmpty() || password.isEmpty());
    }

    // Ensures email contains @ and a domain
    public boolean validEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    public boolean validPassword(String password) {
        return password.matches("^(?=.*[A-Z])" + // at least 1 uppercase
                "(?=.*[a-z])" + // at least 1 lowercase
                "(?=.*\\d)" + // at least 1 number
                "(?=.*[^A-Za-z0-9])" + // at least 1 special character
                ".{10,}$" // at least 10 characters long
        );
    }

    public boolean userExists(String username, String email) {
        boolean exists = false;

        for (User user : userSession.users) {
            if (user.getUsername().equals(username) || user.getEmail().equals(email)) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    public void newUser(String username, String email, String password) {
        userSession.currentUser = new User(username, email, password);

        userSession.users.add(userSession.currentUser);
    }
}
