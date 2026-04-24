package com.mathcat.mathcat.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

import com.mathcat.mathcat.session.UserSession;

public class homeController {
    // This section is just so that the current user's name is displayed when entering
    @FXML
    private Label usernameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    // Initialise current user values
    public void initialize() {
        usernameLabel.setText(UserSession.currentUser.GetUsername());
        emailLabel.setText(UserSession.currentUser.GetEmail());
    }

    // Return to start page
    public void onLogout(ActionEvent event) throws IOException {

        UserSession.currentUser = null;

        Parent root =
                FXMLLoader.load(getClass().getResource("/com/mathcat/mathcat/hello-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle("Start");
        stage.getScene().setRoot(root);
    }
}
