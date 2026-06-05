package com.mathcat.mathcat.controllers;

import com.mathcat.mathcat.services.LevelSystem;
import com.mathcat.mathcat.services.RewardSystem;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import com.mathcat.mathcat.dao.CatDAO;
import com.mathcat.mathcat.dao.UserDAO;
import com.mathcat.mathcat.models.Cat;
import com.mathcat.mathcat.services.CatScheduler;
import com.mathcat.mathcat.services.CatService;
import com.mathcat.mathcat.services.QuestionService;
import com.mathcat.mathcat.services.SpriteService;
import com.mathcat.mathcat.models.IQuestion;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javafx.application.Platform;

/**
 * Controller for the play screen. Handles math questions and delegates AI hint chat to
 * ChatController.
 */
public class PlayController {
    private static final Logger LOG = LoggerFactory.getLogger(PlayController.class);

    /** Creates a new PlayController. */
    public PlayController() {}

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
    private ProgressBar levelProgressBar;

    @FXML
    private Label happinessLabel;
    @FXML
    private Label hungerLabel;
    @FXML
    private Label energyLabel;
    @FXML
    private Label levelProgressLabel;

    @FXML
    private Label mathQuestionLabel;

    @FXML
    private ChatController chatController;

    @FXML
    private TextField answerInput;

    @FXML
    private Button submitButton;

    @FXML
    private Button showAnswerButton;

    @FXML
    private Label feedbackLabel;

    private PauseTransition feedbackTimer;

    private final QuestionService questionService = new QuestionService();
    private IQuestion currentQuestion;
    private Cat cat; // needs to be scoped here to be accessible by onSubmit()
    private int attempts;

    /**
     * Loads the current cat, sets up the answer input listener, and serves the first question.
     */
    @FXML
    public void initialize() {
        answerInput.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*(\\.\\d*)?")) {
                answerInput.setText(oldVal);
            }
        });

        cat = CatScheduler.getInstance().getCat();
        if (cat == null) {
            cat = CatDAO.load(UserDAO.currentUser.getId());
        }
        answerInput.setOnAction(event -> onSubmit(event));

        if (cat != null) {
            LOG.debug("loaded cat: {} (level {})", cat.getCatName(), cat.getLevel());
            CatScheduler.getInstance().setOnTick(() -> refreshStats(cat));
            petNameLabel.setText(cat.getCatName() + "'s Stats");
            viewCurrentPetImage.setImage(SpriteService.load(cat.getCatSprite()));
            viewCurrentAccessoryImage.setImage(SpriteService.load(cat.getCatAccessory()));
            refreshStats(cat);
            currentQuestion = questionService.nextQuestion(cat.getLevel());
            mathQuestionLabel.setText(currentQuestion.getText());

            setupNextQuestion();
            Platform.runLater(() -> answerInput.requestFocus());
        }
    }

    private void setupNextQuestion() {
        if (chatController != null) {
            chatController.resetForNewQuestion();
            chatController.setQuestion(currentQuestion.getText());
            chatController.setAnswer(String.valueOf(currentQuestion.getAnswer()));
        }
    }

    private void refreshStats(Cat cat) {
        double happiness = CatService.displayHappiness(cat);
        double hunger = CatService.displayHunger(cat);
        double energy = CatService.displayEnergy(cat);
        double level = CatService.displayLevel(cat);
        double xp = CatService.displayXP(cat);
        double nextLevelXP = LevelSystem.getXpToNextLevel(level);
        happinessProgressBar.setProgress(happiness / 100);
        hungerProgressBar.setProgress(hunger / 100);
        energyProgressBar.setProgress(energy / 100);
        levelProgressBar.setProgress(nextLevelXP < 0 ? 1.0 : xp / nextLevelXP);

        happinessLabel.setText(String.format("%.0f", happiness));
        hungerLabel.setText(String.format("%.0f", hunger));
        energyLabel.setText(String.format("%.0f", energy));
        levelProgressLabel.setText(String.format("Level %.0f", level));
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
        if (input.isEmpty()) {
            return;
        }

        int userAnswer;
        try {
            userAnswer = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            feedbackLabel.setText("Please enter a whole number.");
            return;
        }

        if (userAnswer == currentQuestion.getAnswer()) {
            resetQuestionState();
            LOG.debug("correct answer: {} (difficulty: {})", userAnswer,
                    currentQuestion.getDifficulty());
            double xpRewarded =
                    RewardSystem.userReward(cat, currentQuestion, chatController.isAiUsed());
            CatDAO.save(cat);
            refreshStats(cat);

            currentQuestion = questionService.nextQuestion(cat.getLevel());
            mathQuestionLabel.setText(currentQuestion.getText());
            answerInput.clear();

            if (xpRewarded != 0) {
                setFeedbackLabel(String.format("Correct! XP Earned: %.2f", xpRewarded));
            } else {
                setFeedbackLabel("Correct! No XP Gained");
            }
            setupNextQuestion();
        } else {
            LOG.debug("Incorrect answer: {}, Correct Answer: {}", userAnswer,
                    currentQuestion.getAnswer());
            attempts++;

            if (attempts >= 3) {
                LOG.debug("3 incorrect attempts reached, showing answer button.");
                setFeedbackLabel("Incorrect! Use the \"Show Answer\" button for help.");
                showAnswerButton.setVisible(true);
                showAnswerButton.setManaged(true);
            } else {
                setFeedbackLabel("Incorrect, try again!");
            }
        }
    }

    private void resetQuestionState() {
        attempts = 0;
        answerInput.setDisable(false);
        submitButton.setDisable(false);
        showAnswerButton.setVisible(false);
        showAnswerButton.setManaged(false);
    }

    /**
     * Reveals the correct answer, then disables the input and submit button so the user can only skip.
     *
     * @param event the button click event
     */
    @FXML
    public void onShowAnswer(ActionEvent event) {
        LOG.debug("Answer revealed: {}", currentQuestion.getAnswer());
        setFeedbackLabel("The answer is: " + currentQuestion.getAnswer());
        answerInput.setDisable(true);
        submitButton.setDisable(true);
        showAnswerButton.setVisible(false);
        showAnswerButton.setManaged(false);
    }

    private void setFeedbackLabel(String feedback) {
        if (feedbackTimer != null) {
            feedbackTimer.stop(); // prevent early dismissal from stacked timers
        }
        feedbackLabel.setText(feedback);
        feedbackLabel.setVisible(true);

        feedbackTimer = new PauseTransition(Duration.seconds(4));

        feedbackTimer.setOnFinished((ActionEvent event) -> {
            feedbackLabel.setVisible(false);
        });
        feedbackTimer.play();
    }

    /**
     * Handles the Skip button. Advances to the next question the same way a correct answer
     * does, but does not call the reward system.
     *
     * @param event the button click event
     */
    @FXML
    public void onSkip(ActionEvent event) {
        if (cat == null || currentQuestion == null) {
            return;
        }
        resetQuestionState();
        LOG.debug("question skipped: {}", currentQuestion.getText());
        currentQuestion = questionService.nextQuestion(cat.getLevel());
        mathQuestionLabel.setText(currentQuestion.getText());
        answerInput.clear();
        feedbackLabel.setText("");

        setupNextQuestion();
    }

    /**
     * Handles return to home screen.
     *
     * @param event the button click event
     */
    public void onConfirmGoBack(ActionEvent event) {
        NavigationUtil.navigateTo(event, "/com/mathcat/mathcat/home-view.fxml");
    }

    /**
     * Handles logout.
     *
     * @param event the button click event
     */
    public void onLogoutConfirm(ActionEvent event) {
        NavigationUtil.logout(event);
    }

    /**
     * Reloads the play screen.
     *
     * @param event the button click event
     */
    public void onPressPlay(ActionEvent event) {
        NavigationUtil.navigateTo(event, "/com/mathcat/mathcat/play-view.fxml");
    }
}
