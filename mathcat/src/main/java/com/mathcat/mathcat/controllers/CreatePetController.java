package com.mathcat.mathcat.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;

import com.mathcat.mathcat.dao.CatDAO;
import com.mathcat.mathcat.dao.UserDAO;
import com.mathcat.mathcat.models.Cat;
import com.mathcat.mathcat.models.SpriteConstants;
import com.mathcat.mathcat.services.CatService;
import com.mathcat.mathcat.services.SpriteService;

/**
 * Controller for the Create Pet screen. Handles pet creation and saves to both in-memory CatDAO and
 * SQLite database.
 */
public class CreatePetController {

    @FXML
    private TextField userPetName;
    @FXML
    private Label error;

    @FXML
    private ImageView viewCurrentPetImage;

    @FXML
    private ImageView viewCurrentAccessoryImage;

    private String selectedSpritePath = SpriteConstants.ORANGE_CAT;
    private String selectedAccessorySpritePath = SpriteConstants.NO_ACCESSORY_SELECTED;

    /**
     * Handles logic of changing the image of the current cat appearance to the orange sprite
     */
    public void onClickOrangeCat() {
        selectedSpritePath = SpriteConstants.ORANGE_CAT;

        viewCurrentPetImage.setImage(SpriteService.load(selectedSpritePath));
    }

    /**
     * Handles logic of changing the image of the current cat appearance to the siamese sprite
     */
    public void onClickSiameseCat() {
        selectedSpritePath = SpriteConstants.SIAMESE_CAT;

        viewCurrentPetImage.setImage(SpriteService.load(selectedSpritePath));
    }

    /**
     * Handles logic of changing the image of the current cat appearance to the tuxedo sprite
     */
    public void onClickTuxedoCat() {
        selectedSpritePath = SpriteConstants.TUXEDO_CAT;

        viewCurrentPetImage.setImage(SpriteService.load(selectedSpritePath));
    }

    /**
     * Handles logic of changing the image of the current cat appearance to the top hat accessory sprite
     */
    public void onClickCowboyHat() {
        selectedAccessorySpritePath = SpriteConstants.COWBOY_HAT;

        viewCurrentAccessoryImage.setImage(SpriteService.load(selectedAccessorySpritePath));
    }

    /**
     * Handles logic of changing the image of the current cat appearance to the red bow tie accessory sprite
     */
    public void onClickBowtieHat() {
        selectedAccessorySpritePath = SpriteConstants.RED_BOWTIE_HAT;

        viewCurrentAccessoryImage.setImage(SpriteService.load(selectedAccessorySpritePath));
    }

    /**
     * Handles logic of clearing accessory image of current cat appearance
     */
    public void onClickClearAccessory() {
        selectedAccessorySpritePath = SpriteConstants.NO_ACCESSORY_SELECTED;

        viewCurrentAccessoryImage.setImage(SpriteService.load(selectedAccessorySpritePath));
    }

    @FXML private Label confirmationMessage;

    // Initially sets the confirmation message once
    public void initialize() {
        setConfirmationMessage();
    }

    /**
     * Handles timed confirmation message — Indicates to user that account creation was successful.
     */
    public void setConfirmationMessage() {
        confirmationMessage.setVisible(true);

        PauseTransition pause = new PauseTransition(Duration.seconds(3));

        pause.setOnFinished((ActionEvent event) -> {
            confirmationMessage.setVisible(false);
        });
        pause.play();
    }

    /**
     * Handles pet creation — validates name, saves to database and navigates to home screen.
     * 
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
        cat.setCatSprite(selectedSpritePath);
        cat.setCatAccessory(selectedAccessorySpritePath);
        CatDAO.save(cat);

        Parent root =
                FXMLLoader.load(getClass().getResource("/com/mathcat/mathcat/home-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 500);

        stage.setTitle("MathCat");
        stage.setScene(scene);
    }

    /**
     * Handles logout — clears current user and returns to initial screen.
     * 
     * @param event the button click event
     * @throws IOException if the initial screen cannot be loaded
     */
    public void onLogoutConfirm(ActionEvent event) throws IOException {
        NavigationUtil.logout(event);
    }
}

