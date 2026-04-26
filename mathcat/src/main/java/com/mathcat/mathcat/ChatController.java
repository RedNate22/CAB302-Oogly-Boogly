package com.mathcat.mathcat;

import com.mathcat.mathcat.services.AIService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import java.util.ArrayList;
import java.util.List;

public class ChatController {

    @FXML private VBox chatBox;
    @FXML private ScrollPane scrollPane;
    @FXML private TextField userInput;
    @FXML private Button sendButton;

    private AIService aiService;
    private String currentQuestion = "What is 15 multiplied by 6?";
    private int hintCount = 0;
    private static final int MAX_HINTS = 3;
    private final List<String[]> conversationHistory = new ArrayList<>();

    /**
     * Initialises the chat controller when the FXML is loaded.
     * Sets up the AI service, auto-scroll behaviour, and displays
     * the initial warning message about hint penalties.
     */
    @FXML
    public void initialize() {
        aiService = new AIService();
        chatBox.heightProperty().addListener((obs, old, newVal) -> scrollPane.setVvalue(1.0));
        addMessage("⚠️ You have " + MAX_HINTS + " hints available. Using hints will reduce your score.", "#FFF9C4", Pos.CENTER);
    }

    public void setQuestion(String question) {
        this.currentQuestion = question;
    }

    public int getHintCount() {
        return hintCount;
    }

    public boolean hasReachedLimit() {
        return hintCount >= MAX_HINTS;
    }

    @FXML
    private void onSendClicked() {
        String message = userInput.getText().trim();
        if (message.isEmpty()) return;

        hintCount++;

        // Show user message
        addMessage(message, "#DCF8C6", Pos.CENTER_RIGHT);
        userInput.clear();
        sendButton.setDisable(true);

        // Show penalty warning
        if (hintCount < MAX_HINTS) {
            addMessage("💡 Hints remaining: " + (MAX_HINTS - hintCount), "#FFF9C4", Pos.CENTER);
        } else if (hintCount == MAX_HINTS) {
            addMessage("⚠️ No more bonus rewards will be given for using hints.", "#FFCCCC", Pos.CENTER);
        }

        // Add user message to history BEFORE sending
        conversationHistory.add(new String[]{"user", message});

        // Get hint from AI in background thread
        new Thread(() -> {
            try {
                String hint = aiService.getHint(currentQuestion, message, conversationHistory);

                javafx.application.Platform.runLater(() -> {
                    // Add AI response to history
                    conversationHistory.add(new String[]{"assistant", hint});
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

    private void addMessage(String message, String color, Pos alignment) {
        Label label = new Label(message);
        label.setWrapText(true);
        label.setMaxWidth(300);
        label.setStyle("-fx-background-color: " + color + "; -fx-padding: 8; -fx-background-radius: 10;");

        HBox container = new HBox(label);
        container.setAlignment(alignment);
        chatBox.getChildren().add(container);
    }
}