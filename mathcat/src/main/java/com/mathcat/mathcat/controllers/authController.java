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

import com.mathcat.mathcat.session.userSession;
import com.mathcat.mathcat.models.User;
import com.mathcat.mathcat.dao.UserDAO;

public class authController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField emailField;

    @FXML
    private Label error;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    // When pressing Login inside the login page
    // Checks the fields aren't empty, an account exists, and validity of details. Scans over the
    // user array to identify these.
    // If details match temp values, log-in is successful, switches to home page with current user
    // set.
    public void onLoginConfirm(ActionEvent event) throws IOException {

        String username = usernameField.getText();
        String password = passwordField.getText();

        // check text fields are not empty
        if (userDAO.fieldsEmpty(username, password)) {
            error.setText("Please fill out all fields");
            return;
        }

        if (userDAO.noUsersExist()) {
            error.setText("No Accounts Exist. Please create an account.");
            return;
        }

        User matchedUser = null;

        User MatchedUser = userDAO.userMatch(matchedUser, username);

        if (userDAO.noUserMatchFound(MatchedUser)) {
            error.setText("This account does not exist.");
            return;
        }

        if (!userDAO.userPasswordMatch(MatchedUser, password)) {
            error.setText("Password is incorrect. Please try again");
            return;
        }

        // Only gets here if all above checks pass
        // System.out.println("Login Successful; Matching details");
        userDAO.setCurrentUser(MatchedUser);

        Parent root =
                FXMLLoader.load(getClass().getResource("/com/mathcat/mathcat/home-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 400);
        stage.setTitle("Home");
        stage.setScene(scene);
        stage.show();
    }

    // When pressing Create Account inside the create account page
    // Check for no null inputs, password and email meet requirements, and does a check to see if
    // details already exist now using object list
    // Given all nescessary requirements, username and password and email values take values
    // inputted into text fields.
    // Current user is set to this user, whilst also being added to the users list
    // Then switches to "Home" page
    public void onCreateAccountConfirm(ActionEvent event) throws IOException {

        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (userDAO.fieldsEmpty(username, email, password)) {
            error.setText("Ensure all details are filled out");
            return;
        }

        if (!userDAO.validEmail(email)) {
            error.setText("Please enter a valid email");
            return;
        }

        if (!userDAO.validPassword(password)) {
            error.setText(
                    "Ensure password length is atleast 10 characters long and contains atleast 1 special character, 1 uppercase character, 1 lowercase character and 1 number");
            return;
        }

        if (!userDAO.userExists(username, email)) {
            userDAO.newUser(username, email, password);

            Parent root =
                    FXMLLoader.load(getClass().getResource("/com/mathcat/mathcat/home-view.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 700, 400);
            stage.setTitle("Home");
            stage.setScene(scene);
            stage.show();
        } else {
            error.setText("Username or email already exists");
        }
    }

    // Returns to the starter page
    public void onReturn(ActionEvent event) throws IOException {
        Parent root =
                FXMLLoader.load(getClass().getResource("/com/mathcat/mathcat/hello-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle("Start");
        stage.getScene().setRoot(root);
    }
}