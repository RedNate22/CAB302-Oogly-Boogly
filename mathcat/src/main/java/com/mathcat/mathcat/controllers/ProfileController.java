package com.mathcat.mathcat.controllers;

import com.mathcat.mathcat.models.User;
import com.mathcat.mathcat.services.*;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

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
     * Handles logic for presenting user with a screen allowing for them to change their username
     *
     * @param event gets the window/stage for the Change Username screen
     * @throws IOException if listed screen does not exist
     */
    @FXML
    public void onChangeUsername(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/mathcat/mathcat/changeusername-view.fxml")
        );

        Parent root = loader.load();

        Stage changeUsernameStage = new Stage();
        changeUsernameStage.setTitle("Change Username");

        // Makes it a popup modal
        changeUsernameStage.initModality(Modality.APPLICATION_MODAL);

        // Makes the popup belong to the current window
        Stage profileStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        changeUsernameStage.initOwner(profileStage);

        Scene scene = new Scene(root, 500, 400);
        scene.getStylesheets().add(NavigationUtil.STYLESHEET);

        changeUsernameStage.setScene(scene);
        changeUsernameStage.showAndWait();
    }

    /**
     * Handles logic for presenting user with a screen allowing for them to change their email
     *
     * @param event gets the window/stage for the Change Email screen
     * @throws IOException if listed screen does not exist
     */
    @FXML
    public void onChangeEmail(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/mathcat/mathcat/changeemail-view.fxml")
        );

        Parent root = loader.load();

        Stage changeEmailStage = new Stage();
        changeEmailStage.setTitle("Change Email");

        // Makes it a popup modal
        changeEmailStage.initModality(Modality.APPLICATION_MODAL);

        // Makes the popup belong to the current window
        Stage profileStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        changeEmailStage.initOwner(profileStage);

        Scene scene = new Scene(root, 500, 400);
        scene.getStylesheets().add(NavigationUtil.STYLESHEET);

        changeEmailStage.setScene(scene);
        changeEmailStage.showAndWait();
    }

    /**
     * Handles logic for presenting user with a screen allowing for them to change their password
     *
     * @param event gets the window/stage for the Change Password screen
     * @throws IOException if listed screen does not exist
     */
    @FXML
    public void onChangePassword(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/mathcat/mathcat/changepassword-view.fxml")
        );

        Parent root = loader.load();

        Stage changePasswordStage = new Stage();
        changePasswordStage.setTitle("Change Password");

        // Makes it a popup modal
        changePasswordStage.initModality(Modality.APPLICATION_MODAL);

        // Makes the popup belong to the current window
        Stage profileStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        changePasswordStage.initOwner(profileStage);

        Scene scene = new Scene(root, 500, 400);
        scene.getStylesheets().add(NavigationUtil.STYLESHEET);

        changePasswordStage.setScene(scene);
        changePasswordStage.showAndWait();
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
     * Handles the logic for accessing the delete account screen
     *
     * @param event gets the window/stage for the delete account screen
     * @throws IOException if listed screen does not exist
     */
    public void onDeleteAccount(ActionEvent event) throws IOException {
            Parent root =
                    FXMLLoader.load(getClass().getResource("/com/mathcat/mathcat/deleteaccount-view.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 700, 500);
            scene.getStylesheets().add(NavigationUtil.STYLESHEET);
            stage.setTitle("Delete Account");
            stage.setScene(scene);

    }
}
