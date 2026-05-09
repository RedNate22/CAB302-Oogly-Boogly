package com.mathcat.mathcat.controllers;

import com.mathcat.mathcat.dao.CatDAO;
import com.mathcat.mathcat.dao.UserDAO;
import com.mathcat.mathcat.models.Cat;
import com.mathcat.mathcat.services.CatScheduler;
import com.mathcat.mathcat.services.CatService;

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

/**
 * Controller class responsible for user interactions with the UI in the "home-view" screen. Does
 * not handle persistence.
 */
public class homeController {

    @FXML
    private Label petNameLabel;

    private ProgressBar happinessProgressBar = new ProgressBar(0);
    private ProgressBar hungerProgressBar = new ProgressBar(0);
    private ProgressBar energyProgressBar = new ProgressBar(0);

    @FXML
    private Image image;

    /**
     * Loads the current user's cat name into the stats label on screen load.
     */
    @FXML
    public void initialize() {
        Cat cat = CatDAO.load(UserDAO.currentUser.getId());

        CatService.applyOfflineDecay(cat);
        CatScheduler.getInstance().start(cat); // begin live stats

        Double catHappiness = CatService.displayHappiness(cat);
        Double catHunger = CatService.displayHunger(cat);
        Double catEnergy = CatService.displayEnergy(cat);

        if (cat != null) {
            petNameLabel.setText(cat.getCatName() + "'s Stats");
            happinessProgressBar.setProgress(catHappiness / 100);
            hungerProgressBar.setProgress(catHunger / 100);
            energyProgressBar.setProgress(catEnergy / 100);
        }

        // System.out.println(catHappiness);
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
        stage.setTitle("MathCat");
        stage.getScene().setRoot(root);
    }

    public void openInventoryModal(ActionEvent event) throws IOException {
        Stage homeStage = getRoot(event);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(homeController.class.getResource("/com/mathcat/mathcat/inventory-modal-view.fxml"));
        loader.load();
        InventoryModalController addDataController = loader.getController();
        addDataController.setMainController(this);

        Parent root = loader.getRoot();
        Stage modalStage = new Stage();

        modalStage.initOwner(homeStage);
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setResizable(false);

        Scene scene = new Scene(root);
        modalStage.setScene(scene);
        modalStage.setTitle("Inventory Modal");
        modalStage.show();
    }
}


