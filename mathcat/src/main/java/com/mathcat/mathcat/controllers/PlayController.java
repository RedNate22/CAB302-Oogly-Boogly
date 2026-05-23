package com.mathcat.mathcat.controllers;

import com.mathcat.mathcat.services.LevelSystem;
import com.mathcat.mathcat.services.RewardSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

import com.mathcat.mathcat.dao.CatDAO;
import com.mathcat.mathcat.dao.UserDAO;
import com.mathcat.mathcat.models.Cat;
import com.mathcat.mathcat.services.CatScheduler;
import com.mathcat.mathcat.services.CatService;
import com.mathcat.mathcat.services.QuestionService;
import com.mathcat.mathcat.services.SpriteService;
import com.mathcat.mathcat.models.IQuestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for the play screen. Handles math questions and delegates AI hint chat to
 * ChatController.
 */
public class PlayController {
    private static final Logger log = LoggerFactory.getLogger(PlayController.class);

    @FXML
    private Label petNameLabel;

    @FXML
    private ImageView viewCurrentPetImage;
    @FXML
    private ImageView viewCurrentAccessoryImage;

    @FXML
    private ProgressBar happinessProgressBar;
    @FXML
    private ProgressBar hungerProgressBar;
    @FXML
    private ProgressBar energyProgressBar;

    @FXML
    private Label happinessLabel;
    @FXML
    private Label hungerLabel;
    @FXML
    private Label energyLabel;

    @FXML
    private Label mathQuestionLabel;

    @FXML
    private ChatController chatController;

    @FXML
    private TextField answerInput;

    @FXML
    private Label feedbackLabel;

    private final QuestionService questionService = new QuestionService();
    private IQuestion currentQuestion;
    private Cat cat; // needs to be scoped here to be accessible by onSubmit()

    @FXML
    public void initialize() {
        cat = CatScheduler.getInstance().getCat();
        if (cat == null) {
            cat = CatDAO.load(UserDAO.currentUser.getId());
        }
        if (cat != null) {
            log.debug("loaded cat: {} (level {})", cat.getCatName(), cat.getLevel());
            CatScheduler.getInstance().setOnTick(() -> refreshStats(cat));
            petNameLabel.setText(cat.getCatName() + "'s Stats");
            viewCurrentPetImage.setImage(SpriteService.load(cat.getCatSprite()));
            viewCurrentAccessoryImage.setImage(SpriteService.load(cat.getCatAccessory()));
            refreshStats(cat);
            currentQuestion = questionService.nextQuestion(cat.getLevel());
            mathQuestionLabel.setText(currentQuestion.getText());

            if (chatController != null) {
                chatController.setQuestion(currentQuestion.getText());
                chatController.setAnswer(String.valueOf(currentQuestion.getAnswer()));
            }
        }
    }

    private void refreshStats(Cat cat) {
        double happiness = CatService.displayHappiness(cat);
        double hunger = CatService.displayHunger(cat);
        double energy = CatService.displayEnergy(cat);
        happinessProgressBar.setProgress(happiness / 100);
        hungerProgressBar.setProgress(hunger / 100);
        energyProgressBar.setProgress(energy / 100);
        happinessLabel.setText(String.format("%.0f", happiness));
        hungerLabel.setText(String.format("%.0f", hunger));
        energyLabel.setText(String.format("%.0f", energy));
    }

    /**
     * Handles the Submit button. Parses the student's answer and compares it to the correct answer.
     * On a correct answer, advances to the next question and resets the AI chat session. If the
     * student did not use AI hints, a bonus reward can be applied here. On an incorrect answer,
     * prompts the student to try again without advancing the question.
     *
     * @param event the button click event
     */
    public void onSubmit(ActionEvent event) {
        String input = answerInput.getText().trim();
        if (input.isEmpty())
            return;

        int userAnswer;
        try {
            userAnswer = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            feedbackLabel.setText("Please enter a whole number.");
            return;
        }

        if (userAnswer == currentQuestion.getAnswer()) {
            log.debug("correct answer: {} (difficulty: {})", userAnswer, currentQuestion.getDifficulty());
            RewardSystem.userReward(cat, currentQuestion, chatController.isAiUsed());
            CatDAO.save(cat);

            currentQuestion = questionService.nextQuestion(cat.getLevel());
            mathQuestionLabel.setText(currentQuestion.getText());
            answerInput.clear();
            feedbackLabel.setText("");

            if (chatController != null) {
                chatController.resetForNewQuestion();
                chatController.setQuestion(currentQuestion.getText());
                chatController.setAnswer(String.valueOf(currentQuestion.getAnswer()));
            }
        } else {
            log.debug("incorrect answer: {}", userAnswer);
            feedbackLabel.setText("Incorrect, try again!");
        }
    }

    /**
     * Handles return to home screen.
     */
    public void onConfirmGoBack(ActionEvent event) throws IOException {
        Parent root =
                FXMLLoader.load(getClass().getResource("/com/mathcat/mathcat/home-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("MathCat");
        stage.getScene().setRoot(root);
    }

    /**
     * Handles logout.
     */
    public void onLogoutConfirm(ActionEvent event) throws IOException {
        NavigationUtil.logout(event);
    }

    public void onPressPlay(ActionEvent event) throws IOException {
        Parent root =
                FXMLLoader.load(getClass().getResource("/com/mathcat/mathcat/play-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("MathCat");
        stage.getScene().setRoot(root);
    }
}
