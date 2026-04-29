package com.mathcat.mathcat.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mathcat.mathcat.dao.CatDAO;
import com.mathcat.mathcat.dao.UserDAO;
import com.mathcat.mathcat.models.Cat;
import com.mathcat.mathcat.services.AIService;

/**
 * Controller for the play screen. Handles math questions and AI hint chat.
 */
public class PlayController {

    @FXML private Label petNameLabel;
    @FXML private Label mathQuestionLabel;
    @FXML private TextArea aiResponseArea;
    @FXML private TextField aiInputField;

    private AIService aiService;
    private List<String[]> conversationHistory = new ArrayList<>();
    private String currentQuestion = "What is 2 + 2?";

    @FXML
    public void initialize() {
        aiService = new AIService();

        Cat cat = CatDAO.load(UserDAO.currentUser.getId());
        if (cat != null) {
            petNameLabel.setText(cat.getCatName() + "'s Stats");
        }

        mathQuestionLabel.setText(currentQuestion);
        aiResponseArea.setText("Ask me for a hint!\n");
    }

    /**
     * Sets the current math question and resets conversation history.
     * @param question the math question to set
     */
    public void setQuestion(String question) {
        this.currentQuestion = question;
        this.conversationHistory.clear();
        mathQuestionLabel.setText(question);
        aiResponseArea.setText("Ask me for a hint!\n");
    }

    /**
     * Handles the AI hint request when the user presses Enter in the input field.
     */
    @FXML
    private void onAiSendClicked() {
        String message = aiInputField.getText().trim();
        if (message.isEmpty()) return;

        aiInputField.clear();
        aiInputField.setDisable(true);

        // Show user message
        aiResponseArea.appendText("\nYou: " + message + "\n");

        // Add user message to history
        conversationHistory.add(new String[]{"user", message});

        // Call AI in background thread
        new Thread(() -> {
            try {
                String hint = aiService.getHint(currentQuestion, message, conversationHistory);
                conversationHistory.add(new String[]{"assistant", hint});

                javafx.application.Platform.runLater(() -> {
                    aiResponseArea.appendText("AI: " + hint + "\n");
                    aiInputField.setDisable(false);
                });
            } catch (Exception e) {
                javafx.application.Platform.runLater(() -> {
                    aiResponseArea.appendText("Error: " + e.getMessage() + "\n");
                    aiInputField.setDisable(false);
                });
            }
        }).start();
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