package com.mathcat.mathcat.controllers;

import com.mathcat.mathcat.services.AIService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the AI hint chat panel. Manages the conversation history, renders chat bubbles,
 * and communicates with {@link com.mathcat.mathcat.services.AIService} on a background thread.
 */
public class ChatController {

    @FXML
    private VBox chatBox;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private AIService aiService;
    private String currentQuestion;

    // Tracks whether the student used the AI hint system at all
    // If true, no bonus reward is given for this question
    private boolean aiUsed = false;

    private final List<String[]> conversationHistory = new ArrayList<>();

    /**
     * Initialises the chat controller when the FXML is loaded. Sets up the AI service, auto-scroll
     * behaviour, and displays the initial warning message about hint penalties.
     */
    @FXML
    public void initialize() {
        aiService = new AIService();
        chatBox.heightProperty().addListener((obs, old, newVal) -> scrollPane.setVvalue(1.0));
    }

    /**
     * Sets the current math question context for the AI tutor. Should be called by the question
     * screen before the chat is shown, so the AI knows which problem the student is working on.
     * 
     * @param question the math question the student is attempting to solve
     */
    public void setQuestion(String question) {
        this.currentQuestion = question;
    }

    /**
     * Returns whether the student used the AI hint system for this question. If true, no bonus
     * reward should be given.
     * 
     * @return true if AI was used at least once
     */
    public boolean isAiUsed() {
        return aiUsed;
    }

    private void resetAiUsed() {
        aiUsed = false;
    }

    /**
     * Resets the chat session for a new question. Clears conversation history, removes all messages
     * from the chat window, and resets the AI used flag.
     */
    public void resetForNewQuestion() {
        conversationHistory.clear();
        chatBox.getChildren().clear();
        resetAiUsed();
    }

    /**
     * Handles the Send button click event. Increments the hint count, displays the user's message,
     * adds it to conversation history, and sends it to the AI service in a background thread to
     * avoid freezing the UI. Displays the AI's hint response in the chat window.
     */
    @FXML
    private void onSendClicked() {
        String rawMessage = userInput.getText();
        if (rawMessage == null || rawMessage.isBlank()) return;

        String message = AIService.sanitiseInput(rawMessage, 500);
        if (message.isBlank()) return;

        // Change this bool value to false when next question is started
        aiUsed = true;

        // Show user message
        addMessage(message, "#DCF8C6", Pos.CENTER_RIGHT);
        userInput.clear();
        sendButton.setDisable(true);

        // Add user message to history BEFORE sending
        conversationHistory.add(new String[] {"user", message});

        // Get hint from AI in background thread
        new Thread(() -> {
            try {
                String hint = aiService.getHint(currentQuestion, currentAnswer, message,
                        conversationHistory);

                javafx.application.Platform.runLater(() -> {
                    // Add AI response to history
                    conversationHistory.add(new String[] {"assistant", hint});
                    addMessage(hint, "#F1F0F0", Pos.CENTER_LEFT);
                    sendButton.setDisable(false);
                });
            } catch (Exception e) {
                javafx.application.Platform.runLater(() -> {
                    addMessage("Error: " + e.getMessage(), "#FFCCCC", Pos.CENTER_LEFT);
                    sendButton.setDisable(false);
                });
            }
        }).start();
    }

    /**
     * Creates a styled chat bubble and adds it to the chat window.
     * 
     * @param message the text to display in the bubble
     * @param color the background color of the bubble in hex format (e.g. "#DCF8C6")
     * @param alignment the position of the bubble (LEFT for AI hints, RIGHT for user messages,
     *        CENTER for warnings)
     */
    private void addMessage(String message, String color, Pos alignment) {
        Label label = new Label(message);
        label.setWrapText(true);
        label.setMaxWidth(300);
        label.setStyle(
                "-fx-background-color: " + color + "; -fx-padding: 8; -fx-background-radius: 10;");

        HBox container = new HBox(label);
        container.setAlignment(alignment);
        chatBox.getChildren().add(container);
    }

    private String currentAnswer;

    public void setAnswer(String answer) {
        this.currentAnswer = answer;
    }
}
