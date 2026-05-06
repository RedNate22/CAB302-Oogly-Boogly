package com.mathcat.mathcat.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import com.mathcat.mathcat.dao.CatDAO;
import com.mathcat.mathcat.dao.UserDAO;
import com.mathcat.mathcat.models.Cat;
import com.mathcat.mathcat.services.CatService;

/**
 * Controller for the Create Pet screen.
 * Handles pet creation and saves to both in-memory CatDAO and SQLite database.
 */
public class CreatePetController {

    @FXML private TextField userPetName;
    @FXML private Label error;

    @FXML
    private ImageView viewCurrentPetImage;
    Image orangeCat = new Image(getClass().getResourceAsStream("/com/mathcat/mathcat/assets/images/sprites/cats/orange-normal.png"));
    Image siameseCat = new Image(getClass().getResourceAsStream("/com/mathcat/mathcat/assets/images/sprites/cats/siamese-normal.png"));
    Image tuxedoCat = new Image(getClass().getResourceAsStream("/com/mathcat/mathcat/assets/images/sprites/cats/tuxedo-normal.png"));

    /**
     * Handles pet creation — validates name, saves to database and navigates to home screen.
     * @param event the button click event
     * @throws IOException if the home screen cannot be loaded
     */
    public void onConfirmPetDetails(ActionEvent event) throws IOException {
        String name = userPetName.getText().trim();

        if (!CatService.isValidCatName(name)) {
            error.setText("Pet name must be 1-10 letters only, no spaces or numbers.");
            return;
        }

        Cat cat = new Cat(name);
        cat.setUserId(UserDAO.currentUser.getId());
        CatDAO.save(cat);

        Parent root = FXMLLoader.load(
                getClass().getResource("/com/mathcat/mathcat/home-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("MathCat");
        stage.getScene().setRoot(root);
    }

    /**
     * Handles logout — clears current user and returns to initial screen.
     * @param event the button click event
     * @throws IOException if the initial screen cannot be loaded
     */
    public void onLogoutConfirm(ActionEvent event) throws IOException {
        UserDAO.currentUser = null;

        Parent root = FXMLLoader.load(
                getClass().getResource("/com/mathcat/mathcat/initial-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("MathCat");
        stage.getScene().setRoot(root);
    }

    /**
     * Handles logic of changing the image of the
     * current cat appearance to the orange sprite
     */
    public void onClickOrangeCat() {
        viewCurrentPetImage.setImage(orangeCat);
    }

    /**     
     * Handles logic of changing the image of the
     * current cat appearance to the siamese sprite
     */
    public void onClickSiameseCat() {
        viewCurrentPetImage.setImage(siameseCat);
    }

    /**
     * Handles logic of changing the image of the
     * current cat appearance to the tuxedo sprite
     */
    public void onClickTuxedoCat() {
        viewCurrentPetImage.setImage(tuxedoCat);
    }
}

