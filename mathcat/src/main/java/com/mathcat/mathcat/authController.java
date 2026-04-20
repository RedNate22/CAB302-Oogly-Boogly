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
    // Check an account exists, and validity of details. Scans over the user array to identify these.
    // If details match temp values, log-in is successful, switches to home page with current user set.
    public void onLoginConfirm(ActionEvent event) throws IOException {
        String enteredUsername = usernameField.getText();
        String enteredPassword = passwordField.getText();
        // Check that users is not empty and then check the inputted account exists
        if (userSession.users != null){
            boolean accountexists = false;
            for (User user : userSession.users) {
                if (user.getUsername().equals(usernameField.getText())) {
                    accountexists = true;
                    if (enteredUsername.equals(user.getUsername()) && enteredPassword.equals(user.getPassword())){
                        System.out.println("Login Successful; Matching details");

                        userSession.currentUser = user;

                        Parent root = FXMLLoader.load(getClass().getResource("home-view.fxml"));
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                        Scene scene = new Scene(root,700, 400);
                        stage.setTitle("Home");
                        stage.setScene(scene);
                        stage.show();
                    }
                    else {System.out.println("Invalid Details");}
                }

            }
            if (!accountexists){System.out.println("Username does not exist");}

        }
        else {System.out.println("No accounts exist");}
    }

    // when pressing Create Account inside the create account page
    // Check for no null inputs, password length is sufficient, and does a check to see if details already exist now using object list
    // Given all nescessary requirements, username and password and email values take values inputted into text fields.
    // Current user is set to this user, whilst also being added to the users list
    // Then switches to "Home" page
    public void onCreateAccountConfirm(ActionEvent event) throws IOException {
        if (usernameField != null || passwordField != null || emailField != null){
            String email = emailField.getText();
            String password = passwordField.getText();

            // Ensures email contains @ symbol and a domain
            boolean emailisValid = email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
            // Ensures password matches all the given criteria
            boolean passwordisValid = password.matches(
                    "^(?=.*[A-Z])" +     // at least 1 uppercase
                            "(?=.*[a-z])" +     // at least 1 lowercase
                            "(?=.*\\d)" +       // at least 1 number
                            "(?=.*[^A-Za-z0-9])" + // at least 1 special character
                            ".{10,}$"           // at least 10 characters long
            );
            if (emailisValid){
                if (passwordisValid){
                    // Add a user if the users list is empty otherwise check the inputted user doesn't already
                    // exist and then add them
                    if (userSession.users == null){
                        userSession.currentUser = new User(
                                usernameField.getText(),
                                emailField.getText(),
                                passwordField.getText()
                        );
                        userSession.users.add(userSession.currentUser);

                        Parent root = FXMLLoader.load(getClass().getResource("home-view.fxml"));
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                        Scene scene = new Scene(root,700, 400);
                        stage.setTitle("Home");
                        stage.setScene(scene);
                        stage.show();
                    }
                    else {
                        boolean exists = false;

                        for (User user : userSession.users) {
                            if (user.getUsername().equals(usernameField.getText()) ||
                                    user.getEmail().equals(emailField.getText())) {

                                exists = true;
                                break;
                            }
                        }

                        if (exists) {
                            System.out.println("Username or email already exists");
                        } else {
                            userSession.currentUser = new User(
                                    usernameField.getText(),
                                    emailField.getText(),
                                    passwordField.getText()
                            );

                            userSession.users.add(userSession.currentUser);

                            Parent root = FXMLLoader.load(getClass().getResource("home-view.fxml"));
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                            Scene scene = new Scene(root,700, 400);
                            stage.setTitle("Home");
                            stage.setScene(scene);
                            stage.show();
                        }
                        //Checking output of the list
                        for (User user : userSession.users) {
                            System.out.println(user.getUsername());
                            System.out.println(user.getEmail());
                            System.out.println(user.getPassword());
                        }

                        // this is to just check that the username and password are storing
                        // System.out.println("Username:" + userSession.currentUser.getUsername());
                        // System.out.println("Email:" + userSession.currentUser.getEmail());
                        // System.out.println("Password:" + userSession.currentUser.getPassword());
                    }
                }
                else {System.out.println("Ensure password length is atleast 10 characters long and " +
                        "contains atleast 1 special character, 1 uppercase character, 1 lowercase character and 1 number");}
            }
            else {System.out.println("Please enter a valid email");}


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
