package com.mathcat.mathcat.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/** Controller for the initial screen, handling navigation to login and account creation. */
public class HelloController {

    /** Creates a new HelloController. */
    public HelloController() {}

    /**
     * Navigates to the login screen.
     *
     * @param event the button click event
     */
    @FXML
    public void onLoginClick(ActionEvent event) {
        NavigationUtil.navigateTo(event, "/com/mathcat/mathcat/login-view.fxml");
    }

    /**
     * Navigates to the create account screen.
     *
     * @param event the button click event
     */
    @FXML
    public void onCreateClick(ActionEvent event) {
        NavigationUtil.navigateTo(event, "/com/mathcat/mathcat/createaccount-view.fxml");
    }
}
