package com.mathcat.mathcat.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/** Controller for the initial screen, handling navigation to login and account creation. */
public class HelloController {

    /** Creates a new HelloController. */
    public HelloController() {}

    /**
     * Navigates to the login screen.
     *
     * @param event the button click event
     * @throws IOException if the login screen FXML cannot be loaded
     */
    @FXML
    public void onLoginClick(ActionEvent event) throws IOException {
        Parent root =
                FXMLLoader.load(getClass().getResource("/com/mathcat/mathcat/login-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 700, 500);
        scene.getStylesheets().add(NavigationUtil.STYLESHEET);
        stage.setTitle("MathCat");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Navigates to the create account screen.
     *
     * @param event the button click event
     * @throws IOException if the create account screen FXML cannot be loaded
     */
    @FXML
    public void onCreateClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader
                .load(getClass().getResource("/com/mathcat/mathcat/createaccount-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 700, 500);
        scene.getStylesheets().add(NavigationUtil.STYLESHEET);
        stage.setTitle("MathCat");
        stage.setScene(scene);
        stage.show();
    }
}
