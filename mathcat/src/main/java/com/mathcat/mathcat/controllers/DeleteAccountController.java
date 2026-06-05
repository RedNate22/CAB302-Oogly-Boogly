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

    /**
     * Handles the logic for deleting the current user's account and informing them this action
     * has been completed. Following this, the user is navigated to the initial screen.
     *
     * @param event deletes the user's account upon confirmation (pressing the button)
     * @throws IOException if listed screen does not exist
     */
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
