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

/**
 * Controller for the play screen.
 * Handles math questions and delegates AI hint chat to ChatController.
 */
public class PlayController {

    @FXML private Label petNameLabel;
    @FXML private Label mathQuestionLabel;

    // ChatController is automatically injected by JavaFX when chat-view.fxml is included
    // Use chatController to pass the current question to the AI hint system
    @FXML private ChatController chatController;

    //Replace this placeholder with the actual question from the math question system
    // To set a question from outside this controller, call setQuestion(String question)

    private String currentQuestion = "What is 2 + 2?";

    @FXML
    public void initialize() {
        Cat cat = CatDAO.load(UserDAO.currentUser.getId());
        if (cat != null) {
            petNameLabel.setText(cat.getCatName() + "'s Stats");
        }

        mathQuestionLabel.setText(currentQuestion);

        // Pass the current question to the chat so AI knows what problem the student is on
        if (chatController != null) {
            chatController.setQuestion(currentQuestion);
            //chatController.setAnswer(String.valueOf(question.getAnswer()));

        }
    }

    /**
     * Sets the current math question displayed to the student and resets the AI chat history.
     * Call this method when loading the play screen with a new question from the math question system.
     * The AI will use this question as context when generating hints.
     * @param question the math question string to display and pass to the AI
     */
    public void setQuestion(String question) {
        this.currentQuestion = question;
        mathQuestionLabel.setText(question);
        if (chatController != null) {
            chatController.setQuestion(question);

        }
    }

    /**
     * Handles return to home screen.
     */
    public void onConfirmGoBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(
                getClass().getResource("/com/mathcat/mathcat/home-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("MathCat");
        stage.getScene().setRoot(root);
    }

    /**
     * Handles logout.
     */
    public void onLogoutConfirm(ActionEvent event) throws IOException {
        UserDAO.currentUser = null;
        Parent root = FXMLLoader.load(
                getClass().getResource("/com/mathcat/mathcat/initial-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("MathCat");
        stage.getScene().setRoot(root);
    }

    public void onPressPlay(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(
                getClass().getResource("/com/mathcat/mathcat/play-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("MathCat");
        stage.getScene().setRoot(root);
    }
}