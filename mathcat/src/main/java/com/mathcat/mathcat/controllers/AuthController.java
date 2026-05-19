package com.mathcat.mathcat.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import com.mathcat.mathcat.services.UserService;
import com.mathcat.mathcat.dao.UserDAO;
import com.mathcat.mathcat.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles UI events for the login and account creation screens.
 */
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @FXML private TextField usernameField;
    @FXML private TextField emailField;

    @FXML private PasswordField passwordField;
    @FXML private TextField visiblePasswordField;

    @FXML private Label error;

    @FXML
    public void initialize() {
        // Keeps both password fields synced automatically when switching between visibility
        visiblePasswordField.textProperty().bindBidirectional(passwordField.textProperty());
    }

    @FXML
    private void togglePassword() {

        boolean showing = visiblePasswordField.isVisible();

        visiblePasswordField.setVisible(!showing);
        visiblePasswordField.setManaged(!showing);

        passwordField.setVisible(showing);
        passwordField.setManaged(showing);
    }

    /**
     * Validates credentials and navigates to the home screen on success.
     *
     * @param event the button click event
     * @throws IOException if the home screen FXML cannot be loaded
     */
    @FXML
    public void onLoginConfirm(ActionEvent event) throws IOException {
        String username_email = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (UserService.fieldsEmpty(username_email, password)) {
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
                    log.error("database error during login", e);
                    error.setText("Database error. Please try again.");
                    return;
                }
            }
        } catch (SQLException e) {
            log.error("database error during login", e);
            error.setText("Database error. Please try again.");
            return;
        }

        if (matchedUser == null) {
            log.warn("login attempt for unknown user: {}", username_email);
            error.setText("This account does not exist.");
            return;
        }

        if (!matchedUser.getPassword().equals(password)) {
            log.warn("incorrect password for user: {}", username_email);
            error.setText("Password is incorrect. Please try again");
            return;
        }

        log.info("user logged in: {}", matchedUser.getUsername());
        UserDAO.setCurrentUser(matchedUser);

        Parent root = FXMLLoader.load(getClass().getResource("/com/mathcat/mathcat/home-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 500);
        stage.setTitle("MathCat");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Validates registration fields, creates the account, and navigates to the create pet screen.
     *
     * @param event the button click event
     * @throws IOException if the create pet screen FXML cannot be loaded
     */
    @FXML
    public void onCreateAccountConfirm(ActionEvent event) throws IOException {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (UserService.fieldsEmpty(username, email, password)) {
            error.setText("Ensure all details are filled out");
            return;
        }

        if (!UserService.validUsername(username)) {
            error.setText("Ensure username contains 3-20 alphanumeric characters (underscores allowed) and has no spaces");
            return;
        }

        if (!UserService.validEmail(email)) {
            error.setText("Please enter a valid email");
            return;
        }

        if (!UserService.validPassword(password)) {
            error.setText("Ensure password length is at least 10 characters long and contains at least 1 special character, 1 uppercase, 1 lowercase and 1 number");
            return;
        }

        try {
            boolean exists = UserDAO.findByUsername(username) != null
                    || UserDAO.findByEmail(email) != null;
            if (!exists) {
                UserDAO.insert(new User(username, email, password));
                UserDAO.setCurrentUser(UserDAO.findByUsername(username));
                log.info("account created: {}", username);

                Parent root = FXMLLoader.load(getClass().getResource("/com/mathcat/mathcat/createpet-view.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 700, 500);
                stage.setTitle("MathCat");
                stage.setScene(scene);
                stage.show();
            } else {
                log.warn("account creation failed - already exists: {}", username);
                error.setText("Username or email already exists");
            }
        } catch (SQLException e) {
            log.error("database error during account creation", e);
            error.setText("Database error. Please try again.");
        }
    }

    /**
     * Returns to the initial screen without logging in.
     *
     * @param event the button click event
     * @throws IOException if the initial screen FXML cannot be loaded
     */
    @FXML
    public void onReturn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/mathcat/mathcat/initial-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("MathCat");
        stage.getScene().setRoot(root);
    }
}