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

    /**
     * Maximum number of characters a user may type in a single message.
     * Prevents excessively long inputs from being sent to the AI API.
     */
    private static final int MAX_INPUT_LENGTH = 500;

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
    private String currentAnswer;

    // Tracks whether the student used the AI hint system at all
    // If true, no bonus reward is given for this question
    private boolean aiUsed = false;

    private final List<String[]> conversationHistory = new ArrayList<>();

    /**
     * Initialises the chat controller when the FXML is loaded. Sets up the AI service,
     * auto-scroll behaviour, and enforces a per-message character limit on the input field.
     */
    @FXML
    public void initialize() {
        aiService = new AIService();
        chatBox.heightProperty().addListener((obs, old, newVal) -> scrollPane.setVvalue(1.0));

        // Enforce max input length in real time - reject any keystroke that would exceed the limit.
        // Also guards against paste - if someone pastes 1000 chars, the whole paste is rejected.
        userInput.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.length() > MAX_INPUT_LENGTH) {
                userInput.setText(oldText);
            }
        });
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
     * Handles the Send button click event. Validates and sanitises the user's message,
     * displays it in the chat window, adds it to conversation history, and sends it to
     * the AI service on a background thread to avoid freezing the UI.
     * Input is rejected if null, blank, or if sanitising removes all content
     * (e.g. a message made up entirely of HTML tags or control characters).
     */
    @FXML
    private void onSendClicked() {
        String rawMessage = userInput.getText();
        if (rawMessage == null || rawMessage.isBlank()) return;

        String message = AIService.sanitiseInput(rawMessage, 500);
        if (message.isBlank()) return;


        aiUsed = true;

        // Show user message
        addMessage(message, "#DCF8C6", Pos.CENTER_RIGHT);
        userInput.clear();
        sendButton.setDisable(true);

        // Add user message to history BEFORE sending
        conversationHistory.add(new String[] {"user", message});

        // Daemon thread so the JVM exits immediately if the window is closed mid-request
        // instead of hanging until the 10s timeout completes
        Thread hintThread = new Thread(() -> {
            String hint = aiService.getHint(currentQuestion, currentAnswer, message,
                    conversationHistory);

            javafx.application.Platform.runLater(() -> {
                // Add AI response to history
                conversationHistory.add(new String[] {"assistant", hint});
                addMessage(hint, "#F1F0F0", Pos.CENTER_LEFT);
                sendButton.setDisable(false);
            });
        });
        hintThread.setDaemon(true);
        hintThread.start();
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

    /**
     * Sets the correct answer for the current question. Passed to the AI system prompt only -
     * never shown directly to the student.
     *
     * @param answer the correct answer as a string
     */
    public void setAnswer(String answer) {
        this.currentAnswer = answer;
    }
}
