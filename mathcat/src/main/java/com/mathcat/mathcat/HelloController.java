package com.mathcat.mathcat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML

    // Clicking the login button on the homescreen results being in taken to the login scene
    public void onLoginClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root,700, 400);
        stage.setTitle("MathCat");
        stage.setScene(scene);
        stage.show();
    }

    // Clicking the Create Account button on the home screen results in being taken to the create account scene
    public void onCreateClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("createaccount-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root,700, 400);
        stage.setTitle("MathCat");
        stage.setScene(scene);
        stage.show();
    }
}
