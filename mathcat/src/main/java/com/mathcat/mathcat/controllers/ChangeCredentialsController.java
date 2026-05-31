package com.mathcat.mathcat.controllers;

import com.mathcat.mathcat.dao.UserDAO;
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
                // UserDAO.insert(new User(username, email, password));
                // UserDAO.setCurrentUser(UserDAO.findByUsername(username));

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
    }

    @FXML
    public void onPasswordChange(ActionEvent event) {
        // Check password is valid, old password matches details and new password typed both types the same
    }
}
