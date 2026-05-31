package com.mathcat.mathcat.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

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

    public void onUsernameChange() {}

    public void onEmailChange() {}

    public void onPasswordChange() {}
}
