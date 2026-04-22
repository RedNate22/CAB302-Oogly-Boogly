package com.mathcat.mathcat;

import java.util.ArrayList;

// Tracks currently saved users (for running session only)
// TODO: refactor to pull list of users from db once setup rather than pulling from session
public class userSession {
    // List of all the stored users
    public static ArrayList<User> users = new ArrayList<>();

    // The current user
    public static User currentUser;
}
