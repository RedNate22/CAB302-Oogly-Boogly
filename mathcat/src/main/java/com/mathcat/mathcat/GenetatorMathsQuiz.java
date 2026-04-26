package com.mathcat.mathcat;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

public class GenetatorMathsQuiz extends Application {

    private int a, b, correctAnswer;
    private final Random r = new Random();

    private boolean answerWasCorrect = false;

    @Override
    public void start(Stage stage) {

        Label questionLabel = new Label();
        TextField answerField = new TextField();
        Button submitBtn = new Button("Submit");
        Button skipBtn = new Button("Skip");
        Label feedbackLabel = new Label();

        generateNewQuestion(questionLabel, answerField, feedbackLabel);

        submitBtn.setOnAction(e -> {
            String userInput = answerField.getText().trim();

            if (userInput.isEmpty()) {
                feedbackLabel.setText("Please enter a number");
                return;
            }

            try {
                int userAnswer = Integer.parseInt(userInput);

                if (userAnswer == correctAnswer) {
                    feedbackLabel.setText("Correct!");
                    answerWasCorrect = true;
                    skipBtn.setText("Next Question");
                } else {
                    feedbackLabel.setText("Incorrect");
                    answerWasCorrect = false;
                }

            } catch (NumberFormatException ex) {
                feedbackLabel.setText("Enter a valid number");
            }
        });

        skipBtn.setOnAction(e -> {
            generateNewQuestion(questionLabel, answerField, feedbackLabel);
            skipBtn.setText("Skip");
            answerWasCorrect = false;
        });

        HBox buttonRow = new HBox(10, submitBtn, skipBtn);
        buttonRow.setStyle("-fx-alignment: center;");

        VBox layout = new VBox(15, questionLabel, answerField, buttonRow, feedbackLabel);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(layout, 350, 250);
        stage.setTitle("Maths Quiz");
        stage.setScene(scene);
        stage.show();
    }

    private void generateNewQuestion(Label questionLabel, TextField answerField, Label feedbackLabel) {
        a = r.nextInt(10) + 1;
        b = r.nextInt(10) + 1;
        correctAnswer = a + b;

        questionLabel.setText( a + " + " + b + " =");
        answerField.clear();
        feedbackLabel.setText("");
    }

    public static void main(String[] args) {
        launch();
    }
}