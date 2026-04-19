package com.mathcat.mathcat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class authController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField emailField;

    @FXML
    // When pressing Login inside the login page
    // Check an account exists, and validity of details
    // If details match temp values, log-in is successful, switches to home page
    public void onLoginConfirm(ActionEvent event) throws IOException {
        String enteredUsername = usernameField.getText();
        String enteredPassword = passwordField.getText();

        if (userSession.username != null){
            if (enteredUsername.equals(userSession.username) && enteredPassword.equals(userSession.password)){
                System.out.println("Login Successful; Matching details");

                Parent root = FXMLLoader.load(getClass().getResource("home-view.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                Scene scene = new Scene(root,700, 400);
                stage.setTitle("Home");
                stage.setScene(scene);
                stage.show();
            }
            else {System.out.println("Invalid Details");}
        }
        else {System.out.println("No accounts exist");}
    }

    // when pressing Create Account inside the create account page
    // Check for no null inputs, password length is sufficient, and does a check to see if details already exist (using temp value)
    // Given all nescessary requirements, username and password and email values take values inputted into text fields
    // Then switches to "Home" page
    public void onCreateAccountConfirm(ActionEvent event) throws IOException {
        if (usernameField != null || passwordField != null || emailField != null){
            if (passwordField.getText().length() < 8){
                System.out.println("Ensure password length is atleast 8 characters long");
            }
            else if (usernameField.getText().equals(userSession.username) || emailField.getText().equals(userSession.email)){
                System.out.println("Username or email already exists");
            }
            else {
                userSession.username = usernameField.getText();
                userSession.email = emailField.getText();
                userSession.password = passwordField.getText();

                // this is to just check that the username and password are storing
                System.out.println("Username:" + userSession.username);
                System.out.println("Email:" + userSession.email);
                System.out.println("Password:" + userSession.password);


                Parent root = FXMLLoader.load(getClass().getResource("home-view.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                Scene scene = new Scene(root,700, 400);
                stage.setTitle("Home");
                stage.setScene(scene);
                stage.show();
            }
        }
        else {System.out.println("Ensure all details are filled out");}

    }
    // Returns to the starter page
    public void onReturn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle("Start");
        stage.getScene().setRoot(root);
    }

}
