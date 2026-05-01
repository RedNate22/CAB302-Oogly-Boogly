package com.mathcat.mathcat.services;

import java.util.List;
import java.util.LinkedList;
// import java.util.Random;

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

    /**
     * Returns the next question from the queue. If the queue is empty, builds a new shuffled queue
     * based on the cat's current level before polling.
     *
     * @param level the cat's current level, used to determine difficulty weighting
     * @return the next {@link IQuestion} to present to the player
     */
    public IQuestion nextQuestion(int level) {
        if (questionQueue.isEmpty()) {
            // ! hardcoded difficulty for now
            questionQueue.addAll(QuestionBank.getByDifficulty(Difficulty.EASY));
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
    @SuppressWarnings("unused") // TODO
    private Difficulty pickDifficulty(int level) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Called by nextQuestion() when the queue is empty, after pickDifficulty().
    // Fetches all questions for the given difficulty from QuestionBank, shuffles them via
    // shuffle(),
    // and populates questionQueue.
    @SuppressWarnings("unused") // TODO
    private LinkedList<IQuestion> buildQueue(Difficulty difficulty) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Called by buildQueue(). Performs an in-place Fisher-Yates shuffle on the question list.
    @SuppressWarnings("unused") // TODO
    private List<IQuestion> shuffle(List<IQuestion> questions) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
