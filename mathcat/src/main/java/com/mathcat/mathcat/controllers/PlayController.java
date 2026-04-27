package com.mathcat.mathcat.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

import com.mathcat.mathcat.session.userSession;

public class PlayController {
    /**
     * Handles return to home screen logic for MathCat in the Play screen.
     * @param event gets the window/stage for the home screen
     * @throws IOException if listed screen does not exist
     */
    public void onConfirmGoBack(ActionEvent event) throws IOException {
        Parent root =
            FXMLLoader.load(getClass().getResource("/com/mathcat/mathcat/home-view.fxml"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle("MathCat");
        stage.getScene().setRoot(root);
    }

    /**
     * Handles logout logic for MathCat in the Create Pet screen,
     * returns user to initial screen.
     * @param event gets the window/stage for the main screen
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
}
