package com.mathcat.mathcat.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

import com.mathcat.mathcat.dao.CatDAO;
import com.mathcat.mathcat.dao.UserDAO;
import com.mathcat.mathcat.models.Cat;
import com.mathcat.mathcat.services.QuestionService;
import com.mathcat.mathcat.models.IQuestion;

/**
 * Controller class responsible for user interactions with the UI in the "play-view" screen. Does
 * not handle persistence.
 */
public class PlayController {

    @FXML
    private Label petNameLabel;

    @FXML
    private Label questionLabel;

    private final QuestionService questionService = new QuestionService();
    private IQuestion currentQuestion;
    private Cat cat; // needs to be scoped here to be accessible by onSubmit()

    @FXML
    public void initialize() {
        cat = CatDAO.load(UserDAO.currentUser.getId());
        if (cat != null) {
            petNameLabel.setText(cat.getCatName() + "'s Stats");
            currentQuestion = questionService.nextQuestion(cat.getLevel());
            questionLabel.setText(currentQuestion.getText());
        }
    }

    public void onSubmit(ActionEvent event) {
        // compare input to question.getAnswer()
        // if correct, call questionService.nextQuestion(cat.getLevel()) for next question
        // update questionLabel with new question text
    }

    /**
     * Handles return to home screen logic for MathCat in the Play screen.
     * 
     * @param event gets the window/stage for the home screen
     * @throws IOException if listed screen does not exist
     */
    public void onConfirmGoBack(ActionEvent event) throws IOException {
        Parent root =
                FXMLLoader.load(getClass().getResource("/com/mathcat/mathcat/home-view.fxml"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle("MathCat");
        stage.getScene().setRoot(root);
    }

    /**
     * Handles logout logic for MathCat in the Create Pet screen, returns user to initial screen.
     * 
     * @param event gets the window/stage for the main screen
     * @throws IOException if listed screen does not exist
     */
    public void onLogoutConfirm(ActionEvent event) throws IOException {

        UserDAO.currentUser = null;

        Parent root =
                FXMLLoader.load(getClass().getResource("/com/mathcat/mathcat/initial-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle("MathCat");
        stage.getScene().setRoot(root);
    }

    public void onPressPlay(ActionEvent event) throws IOException {
        Parent root =
                FXMLLoader.load(getClass().getResource("/com/mathcat/mathcat/play-view.fxml"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle("MathCat");
        stage.getScene().setRoot(root);
    }
}
