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
import javafx.application.Platform;

/**
 * Controller class responsible for user interactions with the UI in the "home-view" screen. Does
 * not handle persistence.
 */
public final class HomeController {
    private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

    /** Creates a new homeController. */
    public HomeController() {}

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

        else {
            Platform.runLater(() -> {
                try {
                    if (petNameLabel.getScene() == null) return; // scene may not be attached yet during initialize()
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

        Scene scene = new Scene(root,700, 500);
        
        stage.setTitle("MathCat");
        stage.setScene(scene);

    }

    // public void openInventoryModal(ActionEvent event) throws IOException {
    //     Stage homeStage = getRoot(event);

    //     FXMLLoader loader = new FXMLLoader();
    //     loader.setLocation(homeController.class.getResource("/com/mathcat/mathcat/inventory-modal-view.fxml"));
    //     loader.load();
    //     InventoryModalController addDataController = loader.getController();
    //     addDataController.setMainController(this);

    //     Parent root = loader.getRoot();
    //     Stage modalStage = new Stage();

    //     modalStage.initOwner(homeStage);
    //     modalStage.initModality(Modality.APPLICATION_MODAL);
    //     modalStage.setResizable(false);

    //     Scene scene = new Scene(root);
    //     modalStage.setScene(scene);
    //     modalStage.setTitle("Inventory Modal");
    //     modalStage.show();
    // }
}


