package com.mathcat.mathcat.controllers;

import com.mathcat.mathcat.dao.CatDAO;
import com.mathcat.mathcat.dao.UserDAO;
import com.mathcat.mathcat.models.Cat;
import com.mathcat.mathcat.models.SpriteConstants;
import com.mathcat.mathcat.services.CatScheduler;
import com.mathcat.mathcat.services.CatService;
import com.mathcat.mathcat.services.SpriteService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
// import javafx.scene.Node;
// import javafx.scene.Parent;
// import javafx.scene.control.Label;
// import javafx.scene.control.ProgressBar;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller class responsible for user interactions with the UI in the "home-view" screen. Does
 * not handle persistence.
 */
public class homeController {
    private static final Logger log = LoggerFactory.getLogger(homeController.class);

    @FXML
    private Label petNameLabel;
    @FXML
    private Label happinessLabel;
    @FXML
    private Label hungerLabel;
    @FXML
    private Label energyLabel;

    @FXML
    private ImageView viewCurrentPetImage;
    @FXML
    private ImageView viewCurrentAccessoryImage;

    @FXML
    private ProgressBar happinessProgressBar;
    @FXML
    private ProgressBar hungerProgressBar;
    @FXML
    private ProgressBar energyProgressBar;

    /**
     * Loads the current user's cat name into the stats label on screen load.
     */
    @FXML
    public void initialize() {

        Cat cat = CatDAO.load(UserDAO.currentUser.getId());

        if (cat != null) {
            CatService.applyOfflineDecay(cat);
            CatScheduler.getInstance().start(cat);
            CatScheduler.getInstance().setOnTick(() -> refreshStats(cat));

            petNameLabel.setText(cat.getCatName() + "'s Stats");
            viewCurrentPetImage.setImage(SpriteService.load(cat.getCatSprite()));
            viewCurrentAccessoryImage.setImage(SpriteService.load(cat.getCatAccessory()));
            refreshStats(cat);
        }

    }

    private void refreshStats(Cat cat) {
        double happiness = CatService.displayHappiness(cat);
        double hunger = CatService.displayHunger(cat);
        double energy = CatService.displayEnergy(cat);
        happinessProgressBar.setProgress(happiness / 100);
        hungerProgressBar.setProgress(hunger / 100);
        energyProgressBar.setProgress(energy / 100);
        happinessLabel.setText(String.format("%.2f", happiness));
        hungerLabel.setText(String.format("%.2f", hunger));
        energyLabel.setText(String.format("%.2f", energy));
    }

    // public Double displayStats(double catHappiness) {
    // catHappiness = CatService.displayHappiness(cat);
    // return catHappiness;
    // }

    /**
     * Handles logout logic for MathCat in the Home screen, returns user to initial screen.
     * 
     * @param event gets the window/stage for the home screen
     * @throws IOException if listed screen does not exist
     */
    public void onLogoutConfirm(ActionEvent event) throws IOException {
        NavigationUtil.logout(event);
    }

    /**
     * Handles play screen logic for MathCat in the home screen.
     * 
     * @param event gets the window/stage for the main screen
     * @throws IOException if listed screen does not exist
     */
    public void onPressPlay(ActionEvent event) throws IOException {
        Parent root =
                FXMLLoader.load(getClass().getResource("/com/mathcat/mathcat/play-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 700, 500);
        scene.getStylesheets().add(NavigationUtil.STYLESHEET);
        stage.setTitle("MathCat");
        stage.setScene(scene);

    }

    /**
     * Handles inventory modal screen logic for MathCat in the home screen. 
     * Opens and sets modal as current screen, so user has to close modal before
     * further interacting with the home screen.
     * 
     * @param event gets the window/stage for the inventory modal screen
     * @throws IOException if the listed screen does not exist.
     */
    @FXML
    public void onPressInventory(ActionEvent event) {
        try {
            FXMLLoader loader =
                new FXMLLoader(getClass().getResource("/com/mathcat/mathcat/inventory-modal-view.fxml"));
            
            Parent root = 
                loader.load();

            InventoryModalController invModalController = loader.getController();

            invModalController.setOnItemSelect(imagePath -> {
                Image newAccessory = new Image(getClass().getResourceAsStream(imagePath));
                viewCurrentAccessoryImage.setImage(newAccessory);
            });

            Stage inventoryStage = new Stage();
            inventoryStage.setTitle("Inventory");

            inventoryStage.initModality(Modality.APPLICATION_MODAL);

            Stage homeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            inventoryStage.initOwner(homeStage);

            Scene inventoryScene = new Scene(root);
            inventoryScene.getStylesheets().add(NavigationUtil.STYLESHEET);
            inventoryStage.setScene(inventoryScene);

            inventoryStage.setOnShown(windowEvent -> {
                double homeX = homeStage.getX();
                double homeY = homeStage.getY();
                double homeHeight = homeStage.getHeight();
                double inventoryHeight = inventoryStage.getHeight();

                inventoryStage.setX(homeX + 10);
                inventoryStage.setY(homeY + (homeHeight - inventoryHeight) / 2);
            });

            inventoryStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


