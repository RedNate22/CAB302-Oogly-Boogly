package com.mathcat.mathcat.services;

import java.util.List;
import java.util.LinkedList;
import java.util.Random;

import com.mathcat.mathcat.models.QuestionBank;
import com.mathcat.mathcat.models.Difficulty;
import com.mathcat.mathcat.models.IQuestion;

/**
 * Manages the flow of math questions to the player.
 *
 * Questions are stored as static arrays in {@link QuestionBank}, grouped by difficulty and type.
 * Each difficulty has 3 arrays of 10 questions each (30 total per difficulty).
 *
 * When {@link #nextQuestion(int)} is called and the queue is empty, a new difficulty pool is
 * selected based on the cat's level, shuffled, and loaded into the queue. Questions are then served
 * one at a time until the pool is exhausted, at which point the process repeats.
 */
public class QuestionService {
    private final LinkedList<IQuestion> questionQueue = new LinkedList<>();
    private final Random random = new Random();

    /**
     * Returns the next question from the queue. If the queue is empty, builds a new shuffled queue
     * based on the cat's current level before polling.
     *
     * @param level the cat's current level, used to determine difficulty weighting
     * @return the next {@link IQuestion} to present to the player
     */
    public IQuestion nextQuestion(int level) {
        if (questionQueue.isEmpty()) {
            buildQueue(pickDifficulty(level));
        }
        return questionQueue.poll();
    }

    // @formatter:off don't remove pls - Nate
    /**
     * Difficulty weighting table
     * | Level | EASY | MEDIUM | HARD |
     * |-------|------|--------|------|
     * | 1-2   | 100% | 0%     | 0%   |
     * | 3-4   | 40%  | 60%    | 0%   |
     * | 5-6   | 0%   | 60%    | 40%  |
     * | 7+    | 0%   | 20%    | 80%  |
     */
    // @formatter:on

    // Called by nextQuestion() when the queue is empty.
    // Rolls a weighted random based on the cat's current level and returns the appropriate
    // Difficulty.
    private Difficulty pickDifficulty(int level) {
        int roll = random.nextInt(100); // 0-99

        if (level <= 2) {
            return Difficulty.EASY;
        } else if (level <= 4) {
            return roll < 40 ? Difficulty.EASY : Difficulty.MEDIUM;
        } else if (level <= 6) {
            return roll < 60 ? Difficulty.MEDIUM : Difficulty.HARD;
        } else {
            return roll < 20 ? Difficulty.MEDIUM : Difficulty.HARD;
        }
    }

    // Called by nextQuestion() when the queue is empty, after pickDifficulty().
    // Fetches all questions for the given difficulty from QuestionBank, shuffles them via
    // shuffle(),
    // and populates questionQueue.
    private void buildQueue(Difficulty difficulty) {
        List<IQuestion> questions = QuestionBank.getByDifficulty(difficulty);
        shuffle(questions);
        questionQueue.addAll(questions);
    }

    // Called by buildQueue(). Performs an in-place Fisher-Yates shuffle on the question list.
    private List<IQuestion> shuffle(List<IQuestion> questions) {
        for (int i = questions.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            IQuestion temp = questions.get(i);
            questions.set(i, questions.get(j));
            questions.set(j, temp);
        }
        return questions;
    }
}
