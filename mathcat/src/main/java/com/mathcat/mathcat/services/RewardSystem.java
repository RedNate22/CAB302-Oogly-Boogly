package com.mathcat.mathcat.services;

import com.mathcat.mathcat.models.Cat;
import com.mathcat.mathcat.models.Difficulty;
import com.mathcat.mathcat.models.IQuestion;
import com.mathcat.mathcat.dao.ItemDAO;
import com.mathcat.mathcat.models.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manages the XP gain a user will receive upon successfully completing a question.
 * This class contains rules for subsequent XP gain and returns it.
 */
public class RewardSystem {
    private static final Logger LOG = LoggerFactory.getLogger(RewardSystem.class);

    /** Creates a new RewardSystem. */
    public RewardSystem() {}

    /**
     * Returns the base XP a user will receive based on the difficulty of the question and whether they have enough energy
     * to receive a reward.
     *
     * @param question the current question
     * @param cat the current user's cat
     * @return the XP user will receive based on difficulty
     */
    public static double baseXpReturn(IQuestion question, Cat cat) {
        double xp = 0;

        if (question.getDifficulty() == Difficulty.EASY && cat.getEnergy() >= 5) {
            xp = 10;
            return xp;
        }

        if (question.getDifficulty() == Difficulty.MEDIUM && cat.getEnergy() >= 10) {
            xp = 20;
            return xp;
        }

        if (question.getDifficulty() == Difficulty.HARD && cat.getEnergy() >= 20) {
            xp = 35;
            return xp;
        }
        return xp;
    }

    /**
     * Returns the bonus XP a user will receive based on criteria of when and how the user answered the question.
     * To receive full bonus XP, the user must be in surplus of 75 happiness, 25 fullness and has answered
     * the question unassisted. On the contrary if not enough energy (no baseXP has been awarded), then there will be
     * no bonus XP
     *
     * @param cat the current user's cat
     * @param isAiUsed determines whether AI was used for the question
     * @param question the current question
     * @return the bonus XP user will receive based on criteria
     */
    public static double xpBonus(Cat cat, Boolean isAiUsed, IQuestion question) {
        double bonus = 0;
        if (baseXpReturn(question, cat) == 0) {
            return 0;
        }
        if (cat.getHappiness() >= 75) {
            bonus += 5;
        }
        if (cat.getFullness() >= 25) {
            bonus += 3;
        }
        if (!isAiUsed) {
            bonus += 5;
        }
        return bonus;
    }

    /**
     * Returns the total XP a user will receive based on the criteria of baseXP and bonusXP
     *
     * @param cat the current user
     * @param question the current question
     * @param isAiUsed boolean for whether AI was used for the question
     *
     * @return the total XP the user will receive
     */
    public static double playerXpReturn(Cat cat, IQuestion question, Boolean isAiUsed) {
        double totalXp = 0;

        totalXp += baseXpReturn(question, cat);
        totalXp += xpBonus(cat, isAiUsed, question);

        return totalXp;
    }

    /**
     * Generates a pseudorandom double that represents a percentage
     *
     * @return a pseudorandom double between 0-100
     */
    public static double randomNumberGenerator() {
        Random random = new Random();
        return random.nextDouble() * 100;
    }

    /**
     * Creates a shuffle array of items based on the original catalog it is provided
     *
     * @param arr ...
     *
     * @return a shuffled array
     */
    private static List<Item> fisherYatesShuffle(List<Item> arr) {
        List<Item> output = new ArrayList<>(arr);
        Random random = new Random();
        for (int i = output.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Item temp = output.get(i);
            output.set(i, output.get(j));
            output.set(j, temp);
        }
        return output;
    }

    /**
     * Apply the XP a user will receive to their account, as well as choosing the first item in a shuffled
     * catalog array (randomised item), and the user has a chance to earn this item with chance increasing
     * based on the difficulty of the question they solved
     *
     * @param cat the current user
     * @param question the current question
     * @param isAiUsed the AI chatcontroller
     */
    public static double userReward(Cat cat, IQuestion question, Boolean isAiUsed) {
        if (cat.getHappiness() < 100) {
            cat.setHappiness(cat.getHappiness() + 1);
        }

        double xpReturn = playerXpReturn(cat, question, isAiUsed);
        if (xpReturn == 0) {
            LOG.debug("No XP gained - insufficient energy (difficulty: {}, energy: {})",
                            question.getDifficulty(), String.format("%.2f", cat.getEnergy()));
            LOG.debug("No item dropped - insufficient energy (difficulty: {}, energy: {})",
                            question.getDifficulty(), String.format("%.2f", cat.getEnergy()));
            return xpReturn;
        }

        LOG.debug("XP: {} (base: {}, bonus: {}, aiUsed: {})", String.format("%.2f", xpReturn),
                String.format("%.2f", baseXpReturn(question, cat)),
                String.format("%.2f", xpBonus(cat, isAiUsed, question)), isAiUsed);
        LevelSystem.applyXp(cat, xpReturn);

        if (question.getDifficulty() == Difficulty.EASY) {
            cat.setEnergy(cat.getEnergy() - 5);
        }
        if (question.getDifficulty() == Difficulty.MEDIUM) {
            cat.setEnergy(cat.getEnergy() - 10);
        }
        if (question.getDifficulty() == Difficulty.HARD) {
            cat.setEnergy(cat.getEnergy() - 20);
        }
        LOG.debug("Energy after deduction: {}", String.format("%.2f", cat.getEnergy()));

        double percentage = randomNumberGenerator();
        Item newItem = fisherYatesShuffle(ItemDAO.getAll()).getFirst();

        if (question.getDifficulty() == Difficulty.EASY && percentage <= 15) {
            cat.getItems().add(newItem);
            ItemDAO.addItem(cat.getCatId(), newItem.getItemId());
            LOG.debug("Item dropped: {} (roll: {})", newItem.getItemName(),
                    String.format("%.2f", percentage));
            return xpReturn;
        }
        if (question.getDifficulty() == Difficulty.MEDIUM && percentage <= 25) {
            cat.getItems().add(newItem);
            ItemDAO.addItem(cat.getCatId(), newItem.getItemId());
            LOG.debug("Item dropped: {} (roll: {})", newItem.getItemName(),
                    String.format("%.2f", percentage));
            return xpReturn;
        }
        if (question.getDifficulty() == Difficulty.HARD && percentage <= 40) {
            cat.getItems().add(newItem);
            ItemDAO.addItem(cat.getCatId(), newItem.getItemId());
            LOG.debug("Item dropped: {} (roll: {})", newItem.getItemName(),
                    String.format("%.2f", percentage));
            return xpReturn;
        }
        LOG.debug("No item dropped (roll: {})", String.format("%.2f", percentage));
        return xpReturn;
    }
}
