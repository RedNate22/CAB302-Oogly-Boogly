package com.mathcat.mathcat;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;

public class ChatController {

    @FXML private VBox chatBox;
    @FXML private ScrollPane scrollPane;
    @FXML private TextField userInput;
    @FXML private Button sendButton;

    private AIService aiService;
    private String currentQuestion = "What is 15 multiplied by 6?";

    @FXML
    public void initialize() {
        aiService = new AIService();
        // Auto scroll to bottom when new messages appear
        chatBox.heightProperty().addListener((obs, old, newVal) ->
                scrollPane.setVvalue(1.0));
    }

    public void setQuestion(String question) {
        this.currentQuestion = question;
    }

    @FXML
    private void onSendClicked() {
        String message = userInput.getText().trim();
        if (message.isEmpty()) return;

        // Show user message
        addMessage(message, "#DCF8C6", Pos.CENTER_RIGHT);
        userInput.clear();
        sendButton.setDisable(true);

        // Get hint from Gemini in background thread
        new Thread(() -> {
            try {
                String hint = aiService.getHint(currentQuestion, message);
                javafx.application.Platform.runLater(() -> {
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
        label.setStyle("-fx-background-color: " + color + "; " +
                "-fx-padding: 8; -fx-background-radius: 10;");

        HBox container = new HBox(label);
        container.setAlignment(alignment);
        chatBox.getChildren().add(container);
    }
}