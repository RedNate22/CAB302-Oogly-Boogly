package com.mathcat.mathcat.controllers;

import java.io.IOException;

import com.mathcat.mathcat.dao.UserDAO;
import com.mathcat.mathcat.services.CatScheduler;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;


/**
 * Shared navigation utilities for MathCat controllers.
 *
 * Centralises screen transitions that are identical across multiple controllers to avoid
 * duplicating logic. Currently handles logout, which requires consistent teardown: stopping
 * {@link com.mathcat.mathcat.services.CatScheduler} and clearing the user session before returning
 * to the initial screen.
 */
public class NavigationUtil {
    // Static utility rather than a base controller class: JavaFX controllers are instantiated by
    // FXMLLoader via reflection, making inheritance fragile. @FXML injection, initialize(), and
    // constructor constraints all interact poorly with superclasses.

    private NavigationUtil() {}

    /**
     * Performs a clean logout: stops the cat scheduler, clears the current user session, and
     * navigates back to the initial screen.
     *
     * @param event the button click event used to resolve the current stage
     * @throws IOException if the initial screen FXML cannot be loaded
     */
    public static void logout(ActionEvent event) throws IOException {
        CatScheduler.getInstance().stop();
        UserDAO.currentUser = null;

        Parent root = FXMLLoader
                .load(NavigationUtil.class.getResource("/com/mathcat/mathcat/initial-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("MathCat");
        stage.getScene().setRoot(root);
    }
}
