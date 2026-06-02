package com.mathcat.mathcat.controllers;

import com.mathcat.mathcat.models.User;
import com.mathcat.mathcat.services.*;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

import com.mathcat.mathcat.dao.CatDAO;
import com.mathcat.mathcat.dao.UserDAO;
import com.mathcat.mathcat.models.Cat;

public class ProfileController {
    @FXML
    private Label usernameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private PasswordField passwordLabel;

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
    private ProgressBar happinessProgressBar;
    @FXML
    private ProgressBar hungerProgressBar;
    @FXML
    private ProgressBar energyProgressBar;
    @FXML
    private ProgressBar levelProgressBar;

    @FXML
    private ImageView viewCurrentPetImage;
    @FXML
    private ImageView viewCurrentAccessoryImage;

    /**
     * Retrieves all the information on the user and their cat that they should be aware of, and
     * consistently retrieves this information to identify updates
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
        String username = UserDAO.currentUser.getUsername();
        String email = UserDAO.currentUser.getEmail();
        String password = UserDAO.currentUser.getPassword();

        String catName = cat.getCatName();
        double happiness = CatService.displayHappiness(cat);
        double hunger = CatService.displayHunger(cat);
        double energy = CatService.displayEnergy(cat);
        double level = CatService.displayLevel(cat);
        double xp = CatService.displayXP(cat);
        double nextLevelXP = LevelSystem.getXpToNextLevel(level);

        usernameLabel.setText(username);
        emailLabel.setText(email);
        passwordLabel.setText(password);
        petNameLabel.setText(catName + "'s Stats");
        happinessLabel.setText(String.format("%.0f", happiness));
        hungerLabel.setText(String.format("%.0f", hunger));
        energyLabel.setText(String.format("%.0f", energy));
        levelProgressLabel.setText(String.format("Level %.0f", level));

        happinessProgressBar.setProgress(happiness / 100);
        hungerProgressBar.setProgress(hunger / 100);
        energyProgressBar.setProgress(energy / 100);
        levelProgressBar.setProgress(xp/nextLevelXP);
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
}
