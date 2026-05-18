package com.mathcat.mathcat.controllers;

import com.mathcat.mathcat.dao.CatDAO;
import com.mathcat.mathcat.dao.UserDAO;
import com.mathcat.mathcat.models.Cat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller class responsible for user interactions with the UI in the "home-view" screen.
 * Does not handle persistence.
 */
public class homeController {
    private static final Logger log = LoggerFactory.getLogger(homeController.class);

    @FXML
    private Label petNameLabel;

    /**
     * Loads the current user's cat name into the stats label on screen load.
     */
    @FXML
    public void initialize() {
        Cat cat = CatDAO.load(UserDAO.currentUser.getId());
        if (cat != null) {
            petNameLabel.setText(cat.getCatName() + "'s Stats");
        }
    }

    /**
     * Handles logout logic for MathCat in the Home screen, returns user to initial screen.
     * @param event gets the window/stage for the home screen
     * @throws IOException if listed screen does not exist
     */
    public void onLogoutConfirm(ActionEvent event) throws IOException {
        log.info("user logged out: {}", UserDAO.currentUser.getUsername());
        UserDAO.currentUser = null;
        Parent root = FXMLLoader.load(
                getClass().getResource("/com/mathcat/mathcat/initial-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("MathCat");
        stage.getScene().setRoot(root);
    }

    /**
     * Handles play screen logic for MathCat in the home screen.
     * @param event gets the window/stage for the main screen
     * @throws IOException if listed screen does not exist
     */
    public void onPressPlay(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(
                getClass().getResource("/com/mathcat/mathcat/play-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("MathCat");
        stage.getScene().setRoot(root);
    }
}