package com.mathcat.mathcat.services;

import com.mathcat.mathcat.controllers.ChatController;
import com.mathcat.mathcat.models.Cat;
import com.mathcat.mathcat.models.Difficulty;
import com.mathcat.mathcat.models.IQuestion;

public class RewardSystem {
    // prototype system
    // READ DIFFICULTY TO BEGIN WITH
    // AWARD USER WITH XP BASED ON DIFFICULTY (ITEM STUFF LATER)
    // 3 SEPERATE METHODS FOR BONUS XP, NO AI, HAPPY CAR AND WELL FED
    // NEEDS TO BE ABLE TO ACCESS CURRENT USERS XP AND LEVEL

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

    public static double playerXpReturn(Cat car, IQuestion question, ChatController chatController) {
        double totalXp = 0;

        totalXp += baseXpReturn(question);
        totalXp += xpBonus(car, chatController);

        return totalXp;
    }

}
