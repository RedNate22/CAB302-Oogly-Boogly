package com.mathcat.mathcat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class homeController {
    // This section is just so that the current user's name is displayed when entering
    @FXML
    private Label usernameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    // Initialise current user values
    public void initialize(){
        usernameLabel.setText(userSession.currentUser.getUsername());
        emailLabel.setText(userSession.currentUser.getEmail());
    }

    // Return to start page
    public void onLogout(ActionEvent event) throws IOException {

        userSession.currentUser = null;

        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle("Start");
        stage.getScene().setRoot(root);
    }
}
