package com.mathcat.mathcat.controllers;

import com.mathcat.mathcat.dao.UserDAO;
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
    private Label idLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    // Initialise current user values
    public void initialize() {
        idLabel.setText(String.valueOf(UserDAO.currentUser.getId()));
        usernameLabel.setText(UserDAO.currentUser.getUsername());
        emailLabel.setText(UserDAO.currentUser.getEmail());
    }

    // Return to start page and logs user out
    public void onLogout(ActionEvent event) throws IOException {

        UserDAO.currentUser = null;

        Parent root =
                FXMLLoader.load(getClass().getResource("/com/mathcat/mathcat/hello-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle("Start");
        stage.getScene().setRoot(root);
    }
}
