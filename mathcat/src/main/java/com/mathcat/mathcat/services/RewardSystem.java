package com.mathcat.mathcat.services;

import com.mathcat.mathcat.controllers.ChatController;
import com.mathcat.mathcat.models.Cat;
import com.mathcat.mathcat.models.Difficulty;
import com.mathcat.mathcat.models.IQuestion;

/**
 * Manages the XP gain a user will receive upon successfully completing a question.
 * This class contains rules for subsequent XP gain and returns it.
 */
public class RewardSystem {

    /**
     * Returns the base XP a user will receive based on the difficulty of the question.
     *
     * @param question the current question
     * @return the XP user will receive based on difficulty
     */
    public static double baseXpReturn(IQuestion question) {
        double xp = 0;

        if (question.getDifficulty() == Difficulty.EASY) {
            xp = 10;
            return xp;
        }

        if (question.getDifficulty() == Difficulty.MEDIUM) {
            xp = 20;
            return xp;
        }

        if (question.getDifficulty() == Difficulty.HARD) {
            xp = 35;
            return xp;
        }
        return xp;
    }

    /**
     * Returns the bonus XP a user will receive based on criteria of when and how the user answered the question.
     * To receive full bonus XP, the user must be in surplus of 75 happiness, 25 fullness and has answered
     * the question unassisted
     *
     * @param car the current user
     * @param chatController the AI chatcontroller
     * @return the bonus XP user will receive based on criteria
     */
    public static double xpBonus(Cat car, ChatController chatController) {
        double bonus = 0;

        if (car.getHappiness() >= 75) {
            bonus += 5;
        }

        if (car.getFullness() >= 25) {
            bonus += 3;
        }

        if (!chatController.isAiUsed()) {
            bonus += 5;
        }
        return bonus;
    }

    /**
     * Returns the total XP a user will receive based on the criteria of baseXP and bonusXP
     *
     * @param car the current user
     * @param question the current question
     * @param chatController the AI chatcontroller
     *
     * @return the total XP the user will receive
     */
    public static double playerXpReturn(Cat car, IQuestion question, ChatController chatController) {
        double totalXp = 0;

        totalXp += baseXpReturn(question);
        totalXp += xpBonus(car, chatController);

        return totalXp;
    }

}
