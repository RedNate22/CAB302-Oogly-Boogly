package com.mathcat.mathcat.services;

import com.mathcat.mathcat.controllers.ChatController;
import com.mathcat.mathcat.models.Cat;
import com.mathcat.mathcat.models.Difficulty;
import com.mathcat.mathcat.models.IQuestion;
import com.mathcat.mathcat.dao.ItemDAO;
import com.mathcat.mathcat.models.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manages the XP gain a user will receive upon successfully completing a question.
 * This class contains rules for subsequent XP gain and returns it.
 */
public class RewardSystem {

    /**
     * Returns the base XP a user will receive based on the difficulty of the question and whether they have enough energy
     * to receive a reward.
     *
     * @param question the current question
     * @return the XP user will receive based on difficulty
     */
    public static double baseXpReturn(IQuestion question, Cat car) {
        double xp = 0;

        if (question.getDifficulty() == Difficulty.EASY && car.getEnergy() >= 5) {
            xp = 10;
            return xp;
        }

        if (question.getDifficulty() == Difficulty.MEDIUM && car.getEnergy() >= 10) {
            xp = 20;
            return xp;
        }

        if (question.getDifficulty() == Difficulty.HARD && car.getEnergy() >= 20) {
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
     * @param car the current user
     * @param chatController the AI chatcontroller
     * @return the bonus XP user will receive based on criteria
     */
    public static double xpBonus(Cat car, ChatController chatController, IQuestion question) {
        double bonus = 0;

        if (baseXpReturn(question, car) == 0) {
            return 0;
        }

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

        totalXp += baseXpReturn(question, car);
        totalXp += xpBonus(car, chatController, question);

        return totalXp;
    }

    /**
     * Generates a pseudorandom double that represents a percentage
     *
     * @return a pseudorandom double between 0-100
     */
    public static double randomNumberGenerator() {
        Random random = new Random();
        double min = 0, max = 1000;
        return (random.nextDouble(max - min + 1))/100;
    }

    /**
     * Creates a shuffle array of items based on the original catalog it is provided
     *
     * @param arr ...
     *
     * @return a shuffled array
     */
    public static List<Item> fisherYatesShuffle(List<Item> arr) {
        List<Item> output = new ArrayList<>();
        boolean[] visited = new boolean[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            int j = new Random().nextInt(arr.size());
            while (visited[j]) {
                j = new Random().nextInt(arr.size());
            }
            output.add(arr.get(j));
            visited[j] = true;
        }
        return output;
    }

    /**
     * Apply the XP a user will receive to their account, as well as choosing the first item in a shuffled
     * catalog array (randomised item), and the user has a chance to earn this item with chance increasing
     * based on the difficulty of the question they solved
     *
     * @param car the current user
     * @param question the current question
     * @param chatController the AI chatcontroller
     */
    public static void userReward(Cat car, IQuestion question, ChatController chatController) {
        double xpReturn = playerXpReturn(car, question, chatController);

        if (xpReturn == 0) {
            return;
        }
        LevelSystem.applyXp(car, xpReturn);

        double percentage = randomNumberGenerator();

        List<Item> shuffledCatalog = fisherYatesShuffle(ItemDAO.getAll());
        ArrayList<Item> newUserItemList = car.getItems();
        Item newItem = shuffledCatalog.getFirst();
        newUserItemList.add(newItem);

        if (question.getDifficulty() == Difficulty.EASY && percentage <= 15) {
            car.setItems(newUserItemList);
            return;
        }

        if (question.getDifficulty() == Difficulty.MEDIUM && percentage <= 25) {
            car.setItems(newUserItemList);
            return;
        }

        if (question.getDifficulty() == Difficulty.HARD && percentage <= 40) {
            car.setItems(newUserItemList);
            return;
        }
    }
}
