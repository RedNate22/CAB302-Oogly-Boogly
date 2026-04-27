package com.mathcat.mathcat.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

import com.mathcat.mathcat.session.userSession;

/**
 * Controller class responsible for user interactions with the UI in the "home-view" screen.
 * Does not handle persistence.
 */
public class homeController {
    // This section is just so that the current user's name is displayed when entering
    // @FXML
    // private Label usernameLabel;

    // @FXML
    // private Label emailLabel;

    // @FXML
    // private Label petName; 

    // @FXML
    
    // public void initialize() {
    //     usernameLabel.setText(userSession.currentUser.getUsername());
    //     emailLabel.setText(userSession.currentUser.getEmail());
    //     // below "getPetName" needs to be created for this to work
    //     // petName.setText(userSession.currentUser.getPetName());
    // }

    /** 
     * Handles logout logic for MathCat in the Home screen,
     * returns user to initial screen.
     * @param event gets the window/stage for the home screen
     * @throws IOException if listed screen does not exist
     */
    public void onLogoutConfirm(ActionEvent event) throws IOException {

        userSession.currentUser = null;

        Parent root =
                FXMLLoader.load(getClass().getResource("/com/mathcat/mathcat/initial-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle("MathCat");
        stage.getScene().setRoot(root);
    }

    /** 
     * Handles play screen logic for MathCat in the home screen.
     * @param event gets the window/stage for the main screen
     * @throws IOException if listed screen does not exist
     */
    public void onPressPlay(ActionEvent event) throws IOException {
        Parent root =
            FXMLLoader.load(getClass().getResource("/com/mathcat/mathcat/play-view.fxml"));
            
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle("MathCat");
        stage.getScene().setRoot(root);
    }
}
