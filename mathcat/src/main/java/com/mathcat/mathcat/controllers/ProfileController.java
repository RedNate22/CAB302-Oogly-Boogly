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
    private Label passwordLabel;

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
    public void initialize() {
        Cat cat = CatDAO.load(UserDAO.currentUser.getId());
        refreshStats(cat);
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

        usernameLabel.setText("Username: " + username);
        emailLabel.setText("Email: " + email);
        passwordLabel.setText("Password: " + password);
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
}
