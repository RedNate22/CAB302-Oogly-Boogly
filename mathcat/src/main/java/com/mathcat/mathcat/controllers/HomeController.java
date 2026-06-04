package com.mathcat.mathcat.controllers;

import com.mathcat.mathcat.dao.CatDAO;
import com.mathcat.mathcat.dao.UserDAO;
import com.mathcat.mathcat.models.Cat;
import com.mathcat.mathcat.models.SpriteConstants;
import com.mathcat.mathcat.services.CatScheduler;
import com.mathcat.mathcat.services.CatService;
import com.mathcat.mathcat.services.LevelSystem;
import com.mathcat.mathcat.services.SpriteService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javafx.application.Platform;

/**
 * Controller class responsible for user interactions with the UI in the "home-view" screen. Does
 * not handle persistence.
 */
public class HomeController {
    private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

    /** Creates a new homeController. */
    public HomeController() {}

    private Cat cat;

    @FXML
    private Label petNameLabel;

    @FXML
    private Label happinessLabel;
    @FXML
    private Label hungerLabel;
    @FXML
    private Label energyLabel;
    @FXML
    private Label levelProgressLabel;

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
    @FXML
    private ProgressBar levelProgressBar;

    /**
     * Loads the current user's cat name into the stats label on screen load.
     * If the user has no cat
     * (e.g. they closed the app before finishing pet creation), redirects to the create pet screen.
     */
    @FXML
    public void initialize() {

        if (UserDAO.currentUser == null) {
            LOG.warn("home screen reached with no logged-in user");
            return;
        }
        this.cat = CatDAO.load(UserDAO.currentUser.getId());

        if (cat != null) {
            CatService.applyOfflineDecay(cat);
            CatScheduler.getInstance().start(cat);
            CatScheduler.getInstance().setOnTick(() -> refreshStats(cat));

            petNameLabel.setText(cat.getCatName() + "'s Stats");
            viewCurrentPetImage.setImage(SpriteService.load(cat.getCatSprite()));
            viewCurrentAccessoryImage.setImage(SpriteService.load(cat.getCatAccessory()));
            refreshStats(cat);
        } else {
            Platform.runLater(() -> {
                try {
                    if (petNameLabel.getScene() == null) {
                        return; // scene may not be attached yet during initialize()
                    }
                    Parent root = FXMLLoader.load(getClass().getResource("/com/mathcat/mathcat/createpet-view.fxml"));
                    Stage stage = (Stage) petNameLabel.getScene().getWindow();
                    stage.getScene().setRoot(root);
                } catch (IOException e) {
                    LOG.error("failed to redirect to create pet screen", e);
                }
            });
        }
    }

    private void refreshStats(Cat cat) {
        double happiness = CatService.displayHappiness(cat);
        double hunger = CatService.displayHunger(cat);
        double energy = CatService.displayEnergy(cat);
        double level = CatService.displayLevel(cat);
        double xp = CatService.displayXP(cat);
        double nextLevelXP = LevelSystem.getXpToNextLevel(level);
        happinessProgressBar.setProgress(happiness / 100);
        hungerProgressBar.setProgress(hunger / 100);
        energyProgressBar.setProgress(energy / 100);
        levelProgressBar.setProgress(xp/nextLevelXP);

        happinessLabel.setText(String.format("%.0f", happiness));
        hungerLabel.setText(String.format("%.0f", hunger));
        energyLabel.setText(String.format("%.0f", energy));
        levelProgressLabel.setText(String.format("Level %.0f", level));
    }

    /**
     * Handles logout logic for MathCat in the Home screen, returns user to initial screen.
     *
     * @param event gets the window/stage for the home screen
     */
    public void onLogoutConfirm(ActionEvent event) {
        NavigationUtil.logout(event);
    }

    /**
     * Handles play screen logic for MathCat in the home screen.
     *
     * @param event gets the window/stage for the main screen
     */
    public void onPressPlay(ActionEvent event) {
        NavigationUtil.navigateTo(event, "/com/mathcat/mathcat/play-view.fxml");
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

                inventoryStage.toFront();
                inventoryStage.requestFocus();
            });

            inventoryStage.showAndWait();
            cat = CatDAO.load(UserDAO.currentUser.getId());
            refreshStats(cat);

            String finalChoice = invModalController.getCurrentSelectedPath();

            if (finalChoice != null && cat != null) {
                cat.setCatAccessory(finalChoice);
                CatDAO.save(cat);
                LOG.info("accessory updated for cat: {}", cat.getCatName());

            } else {
                LOG.debug("no accessory selected in inventory modal");
            }

        } catch (IOException e) {
            LOG.error("failed to load inventory modal", e);
            CatDAO.save(cat);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


