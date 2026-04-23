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

public class authController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField emailField;

    @FXML
    private Label error;

    @FXML
    // When pressing Login inside the login page
    // Checks the fields aren't empty, an account exists, and validity of details. Scans over the
    // user array to identify these.
    // If details match temp values, log-in is successful, switches to home page with current user
    // set.
    public void onLoginConfirm(ActionEvent event) throws IOException {

        String enteredUsername = usernameField.getText();
        String enteredPassword = passwordField.getText();

        // check text fields are not empty
        if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
            error.setText("Please fill out all fields");
            return;
        }

        if (userSession.users.isEmpty()) {
            error.setText("No Accounts Exist. Please create an account.");
            return;
        }

        User matchedUser = null;
        for (User user : userSession.users) {
            if (user.getUsername().equals(enteredUsername)) {
                matchedUser = user;
                break; // user found; stop searching
            }
        }

        if (matchedUser == null) {
            error.setText("This account does not exist.");
            return;
        }

        if (!enteredPassword.equals(matchedUser.getPassword())) {
            error.setText("Password is incorrect. Please try again");
            return;
        }

        // Only gets here if all above checks pass
        // System.out.println("Login Successful; Matching details");
        userSession.currentUser = matchedUser;

        Parent root =
                FXMLLoader.load(getClass().getResource("/com/mathcat/mathcat/home-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 400);
        stage.setTitle("MathCat");
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

        String email = emailField.getText();
        String password = passwordField.getText();

        // Ensures email contains @ symbol and a domain
        boolean emailisValid = email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        // Ensures password matches all the given criteria
        boolean passwordisValid = password.matches("^(?=.*[A-Z])" + // at least 1 uppercase
                "(?=.*[a-z])" + // at least 1 lowercase
                "(?=.*\\d)" + // at least 1 number
                "(?=.*[^A-Za-z0-9])" + // at least 1 special character
                ".{10,}$" // at least 10 characters long
        );

        if (usernameField.getLength() == 0 || passwordField.getLength() == 0
                || emailField.getLength() == 0) {
            error.setText("Ensure all details are filled out");
            return;
        }

        if (!emailisValid) {
            error.setText("Please enter a valid email");
            return;
        }

        if (!passwordisValid) {
            error.setText(
                    "Ensure password length is atleast 10 characters long and contains atleast 1 special character, 1 uppercase character, 1 lowercase character and 1 number");
            return;
        }

        boolean exists = false;

        for (User user : userSession.users) {
            if (user.getUsername().equals(usernameField.getText())
                    || user.getEmail().equals(emailField.getText())) {

                exists = true;
                break;
            }
        }

        if (!exists) {
            userSession.currentUser = new User(usernameField.getText(), emailField.getText(),
                    passwordField.getText());

            userSession.users.add(userSession.currentUser);

            Parent root = FXMLLoader.load(getClass().getResource("/com/mathcat/mathcat/createpet-view.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 700, 400);
            stage.setTitle("MathCat");
            stage.setScene(scene);
            stage.show();
        } else {
            error.setText("Username or email already exists");
            return;
        }
    }

    // Returns to the starter page
    public void onReturn(ActionEvent event) throws IOException {
        Parent root =
                FXMLLoader.load(getClass().getResource("/com/mathcat/mathcat/hello-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle("MathCat");
        stage.getScene().setRoot(root);
    }
}
