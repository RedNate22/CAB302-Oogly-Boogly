package com.mathcat.mathcat.controllers;

import com.mathcat.mathcat.dao.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.animation.PauseTransition;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class DeleteAccountController {

    @FXML
    private Label error;

    @FXML
    public void onDeleteAccountConfirm(ActionEvent event) throws IOException {
        try {
            String username = UserDAO.currentUser.getUsername();
            UserDAO.deleteByUsername(username);
            error.setText("Your account has successfully been deleted.");

            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(e -> {
                try {
                    NavigationUtil.logout(event);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            delay.play();

        } catch (Exception e) {
            error.setText("Could not delete account. Please try again.");
        }
    }
}
