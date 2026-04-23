package com.mathcat.mathcat.session;

import java.util.ArrayList;
import com.mathcat.mathcat.models.User;

// Tracks currently saved users (for running session only)
public class userSession {
    // List of all the stored users
    public static ArrayList<User> users = new ArrayList<>();

    // The current user
    public static User currentUser;
}
