package com.mathcat.mathcat.services;

/**
 * Validates user input for login and account creation.
 */
public class UserService {

    // FieldsEmpty methods check no fields are empty during the login or account creation process
    public static boolean FieldsEmpty(String username, String password) {
        return (username.isEmpty() || password.isEmpty());
    }

    public static boolean FieldsEmpty(String username, String email, String password) {
        return (username.isEmpty() || email.isEmpty() || password.isEmpty());
    }

    // Length between 3-20 characters, no spaces and alphanumeric allowed inclusive of underscores
    public static boolean ValidUsername(String username) {
        return username.matches("^[a-zA-Z0-9_]{3,20}$");
    }

    // Ensures email contains @ and a domain
    public static boolean ValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    public static boolean ValidPassword(String password) {
        return password.matches("^(?=.*[A-Z])" + // at least 1 uppercase
                "(?=.*[a-z])" + // at least 1 lowercase
                "(?=.*\\d)" + // at least 1 number
                "(?=.*[^A-Za-z0-9])" + // at least 1 special character
                ".{10,}$" // at least 10 characters long
        );
    }
}
