package com.mathcat.mathcat.services;

import com.mathcat.mathcat.dao.CatDAO;
import com.mathcat.mathcat.models.Cat;
import com.mathcat.mathcat.models.Difficulty;
import com.mathcat.mathcat.models.Question;

public class RewardSystem {
    // prototype system
    // READ DIFFICULTY TO BEGIN WITH
    // AWARD USER WITH XP BASED ON DIFFICULTY (ITEM STUFF LATER)
    // 3 SEPERATE METHODS FOR BONUS XP, NO AI, HAPPY CAR AND WELL FED
    // NEEDS TO BE ABLE TO ACCESS CURRENT USERS XP AND LEVEL

    public int baseXpReturn(Question question) {
        int xp = 0;

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

    public int xpBonus(Cat car) {
        int bonus = 0;

        if (car.getHappiness() >= 75) {
            bonus += 5;
        }

        if (car.getFullness() >= 25) {
            bonus += 3;
        }
        return bonus;
    }

}
