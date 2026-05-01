package com.mathcat.mathcat.services;

/**
 * Validates user input for login and account creation.
 */
public final class UserService {

    // Prevent instantiation
    private UserService() {}

    /**
     * Returns true if either field is empty.
     *
     * @param username the username or email input
     * @param password the password input
     * @return true if either field is empty
     */
    public static boolean fieldsEmpty(String username, String password) {
        return (username.isEmpty() || password.isEmpty());
    }

    /**
     * Returns true if any of the three fields are empty.
     *
     * @param username the username input
     * @param email the email input
     * @param password the password input
     * @return true if any field is empty
     */
    public static boolean fieldsEmpty(String username, String email, String password) {
        return (username.isEmpty() || email.isEmpty() || password.isEmpty());
    }

    /**
     * Returns true if the username is 3–20 alphanumeric characters (underscores allowed, no spaces).
     *
     * @param username the username to validate
     * @return true if valid
     */
    public static boolean validUsername(String username) {
        return username.matches("^[a-zA-Z0-9_]{3,20}$");
    }

    /**
     * Returns true if the email contains an @ symbol and a valid domain.
     *
     * @param email the email to validate
     * @return true if valid
     */
    public static boolean validEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    /**
     * Returns true if the password is at least 10 characters and contains at least one uppercase
     * letter, one lowercase letter, one digit, and one special character.
     *
     * @param password the password to validate
     * @return true if valid
     */
    public static boolean validPassword(String password) {
        return password.matches("^(?=.*[A-Z])" + // at least 1 uppercase
                "(?=.*[a-z])" + // at least 1 lowercase
                "(?=.*\\d)" + // at least 1 number
                "(?=.*[^A-Za-z0-9])" + // at least 1 special character
                ".{10,}$" // at least 10 characters long
        );
    }
}
