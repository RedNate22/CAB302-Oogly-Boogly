package com.mathcat.mathcat.controllers;

import com.mathcat.mathcat.services.AIService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import java.util.ArrayList;
import java.util.List;

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



    private String currentQuestion = "";
    private int hintCount = 0;
    private static final int MAX_HINTS = 3;
    private final List<String[]> conversationHistory = new ArrayList<>();

    /**
     * Initialises the chat controller when the FXML is loaded. Sets up the AI service, auto-scroll
     * behaviour, and displays the initial warning message about hint penalties.
     */
    @FXML
    public void initialize() {
        aiService = new AIService();
        chatBox.heightProperty().addListener((obs, old, newVal) -> scrollPane.setVvalue(1.0));
        addMessage(
                "⚠️ You have " + MAX_HINTS
                        + " hints available. Using hints will reduce your score.",
                "#FFF9C4", Pos.CENTER);
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
     * Returns the number of hints the student has used so far. Used by the question screen to
     * calculate the penalty on the student's reward.
     * 
     * @return the number of hints used (0 to MAX_HINTS)
     */
    public int getHintCount() {
        return hintCount;
    }

    /**
     * Checks if the student has used all available hints. If true, no bonus rewards will be given
     * for this question.
     * 
     * @return true if the student has reached the hint limit, false otherwise
     */
    public boolean hasReachedLimit() {
        return hintCount >= MAX_HINTS;
    }

    /**
     * Handles the Send button click event. Increments the hint count, displays the user's message,
     * adds it to conversation history, and sends it to the AI service in a background thread to
     * avoid freezing the UI. Displays the AI's Socratic hint response in the chat window.
     */
    @FXML
    private void onSendClicked() {
        String message = userInput.getText().trim();
        if (message.isEmpty())
            return;

        hintCount++;

        // Show user message
        addMessage(message, "#DCF8C6", Pos.CENTER_RIGHT);
        userInput.clear();
        sendButton.setDisable(true);

        // Show penalty warning
        if (hintCount < MAX_HINTS) {
            addMessage("💡 Hints remaining: " + (MAX_HINTS - hintCount), "#FFF9C4", Pos.CENTER);
        } else if (hintCount == MAX_HINTS) {
            addMessage("⚠️ No more bonus rewards will be given for using hints.", "#FFCCCC",
                    Pos.CENTER);
        }

        // Add user message to history BEFORE sending
        conversationHistory.add(new String[] {"user", message});

        // Get hint from AI in background thread
        new Thread(() -> {
            try {
                String hint = aiService.getHint(currentQuestion, message, conversationHistory);

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
}
