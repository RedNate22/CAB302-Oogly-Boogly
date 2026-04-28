package com.mathcat.mathcat.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import com.mathcat.mathcat.services.UserService;
import com.mathcat.mathcat.dao.UserDAO;
import com.mathcat.mathcat.models.User;

/**
 * Handles UI events for the login and account creation screens.
 */
public class AuthController {

    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private TextField emailField;
    @FXML private Label error;

    @FXML
    public void onLoginConfirm(ActionEvent event) throws IOException {
        String username_email = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (UserService.FieldsEmpty(username_email, password)) {
            error.setText("Please fill out all fields");
            return;
        }

        User matchedUser;
        try {
            matchedUser = UserDAO.findByEmail(username_email);
            if (matchedUser == null) {
                try {
                    matchedUser = UserDAO.findByUsername(username_email);
                } catch (SQLException e) {
                    error.setText("Database error. Please try again.");
                    return;
                }
            }
        } catch (SQLException e) {
            error.setText("Database error. Please try again.");
            return;
        }

        if (matchedUser == null) {
            error.setText("This account does not exist.");
            return;
        }

        if (!matchedUser.getPassword().equals(password)) {
            error.setText("Password is incorrect. Please try again");
            return;
        }

        UserDAO.setCurrentUser(matchedUser);

        Parent root = FXMLLoader.load(getClass().getResource("/com/mathcat/mathcat/home-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 400);
        stage.setTitle("MathCat");
        stage.setScene(scene);
        stage.show();
    }

    public void onCreateAccountConfirm(ActionEvent event) throws IOException {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (UserService.FieldsEmpty(username, email, password)) {
            error.setText("Ensure all details are filled out");
            return;
        }

        if (!UserService.ValidUsername(username)) {
            error.setText("Ensure username contains 3-20 alphanumeric characters (underscores allowed) and has no spaces");
            return;
        }

        if (!UserService.ValidEmail(email)) {
            error.setText("Please enter a valid email");
            return;
        }

        if (!UserService.ValidPassword(password)) {
            error.setText("Ensure password length is at least 10 characters long and contains at least 1 special character, 1 uppercase, 1 lowercase and 1 number");
            return;
        }

        try {
            boolean exists = UserDAO.findByUsername(username) != null
                    || UserDAO.findByEmail(email) != null;
            if (!exists) {
                UserDAO.insert(new User(username, email, password));
                UserDAO.setCurrentUser(UserDAO.findByUsername(username));

                Parent root = FXMLLoader.load(getClass().getResource("/com/mathcat/mathcat/createpet-view.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 700, 400);
                stage.setTitle("MathCat");
                stage.setScene(scene);
                stage.show();
            } else {
                error.setText("Username or email already exists");
            }
        } catch (SQLException e) {
            error.setText("Database error. Please try again.");
        }
    }

    public void onReturn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/mathcat/mathcat/initial-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("MathCat");
        stage.getScene().setRoot(root);
    }
}