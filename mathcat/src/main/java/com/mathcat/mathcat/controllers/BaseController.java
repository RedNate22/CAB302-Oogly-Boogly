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
 * Abstract base class for MathCat controllers. Provides shared navigation and logout logic,
 * and exposes the application stylesheet path for use in child controllers.
 */
public abstract class BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

    public static final String STYLESHEET = BaseController.class
            .getResource("/com/mathcat/mathcat/styling/styles.css").toExternalForm();

    /**
     * Stops the scheduler, clears the current user session, and navigates to the initial screen.
     *
     * @param event the button click event used to resolve the current stage
     */
    protected void logout(ActionEvent event) {
        CatScheduler.getInstance().stop();
        if (UserDAO.currentUser != null) {
            LOG.info("user logged out: {}", UserDAO.currentUser.getUsername());
        }
        UserDAO.currentUser = null;

        try {
            Parent root = FXMLLoader.load(
                    BaseController.class.getResource("/com/mathcat/mathcat/initial-view.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MathCat");
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            LOG.error("failed to load initial screen during logout", e);
        }
    }

    /**
     * Replaces the current scene with the given FXML screen.
     *
     * @param event the button click event used to resolve the current stage
     * @param fxml  the classpath-absolute path to the FXML resource
     */
    protected void navigateTo(ActionEvent event, String fxml) {
        try {
            Parent root = FXMLLoader.load(BaseController.class.getResource(fxml));
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
