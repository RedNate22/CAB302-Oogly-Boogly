package com.mathcat.mathcat.controllers;

import com.mathcat.mathcat.dao.CatDAO;
import com.mathcat.mathcat.dao.UserDAO;
import com.mathcat.mathcat.models.Cat;
import com.mathcat.mathcat.models.User;
import com.mathcat.mathcat.services.UserService;
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
import org.w3c.dom.Text;

import java.sql.SQLException;

public class ChangeCredentialsController {
    @FXML
    private TextField usernameField;
    @FXML private TextField emailField;

    @FXML private PasswordField currentPasswordField;
    @FXML private PasswordField newPasswordField1;
    @FXML private PasswordField newPasswordField2;

    @FXML private TextField currentPasswordTextField;
    @FXML private TextField newPasswordTextField1;
    @FXML private TextField newPasswordTextField2;

    @FXML private Label error;

    @FXML
    public void initialize() {
        currentPasswordField.textProperty().bindBidirectional(currentPasswordTextField.textProperty());
        newPasswordField1.textProperty().bindBidirectional(newPasswordTextField1.textProperty());
        newPasswordField2.textProperty().bindBidirectional(newPasswordTextField2.textProperty());
    }

    @FXML
    private void togglePassword1() {

        boolean showing = currentPasswordTextField.isVisible();

        currentPasswordTextField.setVisible(!showing);
        currentPasswordTextField.setManaged(!showing);

        currentPasswordField.setVisible(showing);
        currentPasswordField.setManaged(showing);
    }

    @FXML
    private void togglePassword2() {

        boolean showing = newPasswordTextField1.isVisible();

        newPasswordTextField1.setVisible(!showing);
        newPasswordTextField1.setManaged(!showing);

        newPasswordField1.setVisible(showing);
        newPasswordField1.setManaged(showing);
    }

    @FXML
    private void togglePassword3() {

        boolean showing = newPasswordTextField2.isVisible();

        newPasswordTextField2.setVisible(!showing);
        newPasswordTextField2.setManaged(!showing);

        newPasswordField2.setVisible(showing);
        newPasswordField2.setManaged(showing);
    }

    @FXML
    public void onUsernameChange(ActionEvent event) {
        String username = usernameField.getText().trim();

        if (username.isEmpty()) {
            error.setText("Please enter a username");
        }

        if (!UserService.validUsername(username)) {
            error.setText("Ensure username contains 3-20 alphanumeric characters (underscores allowed) and has no spaces");
            return;
        }

        try {
            boolean exists = UserDAO.findByUsername(username) != null;
            if (!exists) {
                UserDAO.currentUser.setUsername(username);

            } else {
                error.setText("Username already exists");
            }
        } catch(SQLException e){
            error.setText("Database error. Please try again.");
        }
    }

    @FXML
    public void onEmailChange(ActionEvent event) {
        // Check email doesn't already exist and is valid
        String email = emailField.getText().trim();

        if (email.isEmpty()) {
            error.setText("Please enter a username");
        }

        if (!UserService.validEmail(email)) {
            error.setText("Please enter a valid email");
            return;
        }

        try {
            boolean exists = UserDAO.findByEmail(email) != null;
            if (!exists) {
                UserDAO.currentUser.setEmail(email);

            } else {
                error.setText("Email already exists");
            }
        } catch(SQLException e){
            error.setText("Database error. Please try again.");
        }
    }

    @FXML
    public void onPasswordChange(ActionEvent event) {
        // Check password is valid, old password matches details and new password typed both types the same
        // Check email doesn't already exist and is valid
        String currentPassword = currentPasswordField.getText().trim();
        String newPassword1 = newPasswordField1.getText().trim();
        String newPassword2 = newPasswordField2.getText().trim();

        if (currentPassword.isEmpty() || newPassword1.isEmpty() || newPassword2.isEmpty()) {
            error.setText("Please ensure all fields are filled out");
            return;
        }

        if (!currentPassword.equals(UserDAO.currentUser.getPassword())) {
            error.setText("Current password is Incorrect");
            return;
        }

        if(!newPassword1.equals(newPassword2)) {
            error.setText("The new password entries do not match");
            return;
        }

        if (newPassword1.equals(UserDAO.currentUser.getPassword())) {
            error.setText("The new password cannot match the current password");
            return;
        }

        if (!UserService.validPassword(newPassword1)) {
            error.setText("Ensure password length is at least 10 characters long and contains at least 1 special character, 1 uppercase, 1 lowercase and 1 number");
            return;
        }

        UserDAO.currentUser.setPassword(newPassword1);

    }
}
