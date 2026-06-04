package com.mathcat.mathcat.controllers;

import java.io.IOException;

import com.mathcat.mathcat.dao.UserDAO;
import com.mathcat.mathcat.services.CatScheduler;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Shared navigation utilities for MathCat controllers.
 *
 * Centralises screen transitions that are identical across multiple controllers to avoid
 * duplicating logic. Currently handles logout, which requires consistent teardown: stopping
 * {@link com.mathcat.mathcat.services.CatScheduler} and clearing the user session before returning
 * to the initial screen.
 */
public final class NavigationUtil {
    // Static utility rather than a base controller class: JavaFX controllers are instantiated by
    // FXMLLoader via reflection, making inheritance fragile. @FXML injection, initialize(), and
    // constructor constraints all interact poorly with superclasses.

    private static final Logger LOG = LoggerFactory.getLogger(NavigationUtil.class);

    public static final String STYLESHEET = NavigationUtil.class
            .getResource("/com/mathcat/mathcat/styling/styles.css").toExternalForm();

    private NavigationUtil() {}

    /**
     * Performs a clean logout: stops the cat scheduler, clears the current user session, and
     * navigates back to the initial screen.
     *
     * @param event the button click event used to resolve the current stage
     * @throws IOException if the initial screen FXML cannot be loaded
     */
    public static void logout(ActionEvent event) {
        CatScheduler.getInstance().stop();
        if (UserDAO.currentUser != null) {
            LOG.info("user logged out: {}", UserDAO.currentUser.getUsername());
        }
        UserDAO.currentUser = null;

        try {
            Parent root = FXMLLoader.load(
                    NavigationUtil.class.getResource("/com/mathcat/mathcat/initial-view.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MathCat");
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            LOG.error("failed to load initial screen during logout", e);
        }
    }

    /**
     * Navigates to the given FXML screen, replacing the current scene.
     *
     * @param event the button click event used to resolve the current stage
     * @param fxml the classpath-absolute path to the FXML resource
     */
    public static void navigateTo(ActionEvent event, String fxml) {
        try {
            Parent root = FXMLLoader.load(NavigationUtil.class.getResource(fxml));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 700, 500);
            scene.getStylesheets().add(STYLESHEET);
            stage.setTitle("MathCat");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            LOG.error("failed to load screen: {}", fxml, e);
        }
    }
}
