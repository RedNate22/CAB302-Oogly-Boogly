package com.mathcat.mathcat.services;

import java.time.LocalDateTime;
import com.mathcat.mathcat.models.Cat;
import com.mathcat.mathcat.dao.CatDAO;

/**
 * Handles stat modification, validation, decay calculation, and persistence for the cat.
 */
public final class CatService {
    public static final double MAX_STAT = 100.0;
    public static final double MIN_STAT = 0.00;
    public static final double HAPPINESS_DECAY_RATE = 0.07; // per min: hits 0 in ~24 hours
    public static final double FULLNESS_DECAY_RATE = 0.07; // per min: hits 0 in ~24 hours
    public static final double HUNGER_THRESHOLD = 25.0;

    // Prevent instantiation
    private CatService() {}

    /**
     * @param name the cat name to validate
     * @return true if the name contains only letters and is 1-10 characters
     */
    public static boolean isValidCatName(String name) {
        return name.matches("^[A-Za-z]{1,10}$");
    }

    /**
     * @param value the stat value to validate
     * @return true if the value is within MIN_STAT and MAX_STAT bounds
     */
    public static boolean isValidStatValue(double value) {
        return (value >= MIN_STAT && value <= MAX_STAT);
    }

    /**
     * Clamps a value between a custom min and max. Used for non-standard stat bounds.
     * 
     * @param value the value to clamp
     * @param min the minimum bound
     * @param max the maximum bound
     * @return the clamped value
     */
    public static double clampStat(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Clamps a stat value between the standard MIN_STAT and MAX_STAT bounds.
     * 
     * @param value the stat value to clamp
     * @return the clamped value
     */
    public static double clampStat(double value) {
        return clampStat(value, MIN_STAT, MAX_STAT);
    }

    /**
     * @param cat the cat to increase happiness for
     * @param value the amount to increase by
     */
    public static void increaseHappiness(Cat cat, double value) {
        cat.setHappiness(clampStat(cat.getHappiness() + value));
        cat.setLastSaved(LocalDateTime.now());
        CatDAO.save(cat);
    }

    /**
     * @param cat the cat to decrease happiness for
     * @param value the amount to decrease by
     */
    public static void decreaseHappiness(Cat cat, double value) {
        cat.setHappiness(clampStat(cat.getHappiness() - value));
        cat.setLastSaved(LocalDateTime.now());
        CatDAO.save(cat);
    }

    /**
     * @param cat the cat to increase fullness for
     * @param value the amount to decrease by
     */
    public static void increaseFullness(Cat cat, double value) {
        cat.setFullness(clampStat(cat.getFullness() + value));
        cat.setLastSaved(LocalDateTime.now());
        CatDAO.save(cat);
    }

    /**
     * @param cat the cat to decrease fullness for
     * @param value the amount to decrease by
     */
    public static void decreaseFullness(Cat cat, double value) {
        cat.setFullness(clampStat(cat.getFullness() - value));
        cat.setLastSaved(LocalDateTime.now());
        CatDAO.save(cat);
    }

    /**
     * @param cat the cat to increase energy for
     * @param value the amount to increase by
     */
    public static void increaseEnergy(Cat cat, double value) {
        cat.setEnergy(clampStat(cat.getEnergy() + value));
        cat.setLastSaved(LocalDateTime.now());
        CatDAO.save(cat);
    }

    /**
     * @param cat the cat to decrease energy for
     * @param value the amount to decrease by
     */
    public static void decreaseEnergy(Cat cat, double value) {
        cat.setEnergy(clampStat(cat.getEnergy() - value));
        cat.setLastSaved(LocalDateTime.now());
        CatDAO.save(cat);
    }

    /**
     * Called by CatScheduler periodically to regenerate energy proportionally to current state of
     * fullness.
     * 
     * @param cat the cat to regenerate energy for
     */
    public static void regenerateEnergy(Cat cat) {
        double proportion = cat.getFullness() / MAX_STAT;
        double regen = proportion * MAX_STAT;
        cat.setEnergy(clampStat(cat.getEnergy() + regen));
        cat.setLastSaved(LocalDateTime.now());
        CatDAO.save(cat);
    }

    /**
     * Applies stat decay based on time elapsed since the cat was last saved. Used on login to
     * account for offline time.
     * 
     * @param cat the cat to apply decay to
     */
    public static void applyOfflineDecay(Cat cat) {
        if (cat.getLastSaved() == null)
            return;

        long minutesElapsed = java.time.Duration
                .between(cat.getLastSaved(), java.time.LocalDateTime.now()).toMinutes();

        double happinessDecay = minutesElapsed * HAPPINESS_DECAY_RATE;
        double fullnessDecay = minutesElapsed * FULLNESS_DECAY_RATE;

        decreaseHappiness(cat, happinessDecay);
        decreaseFullness(cat, fullnessDecay);
    }

    /**
     * @param cat the cat to check hunger of
     * @return true if the cat's fullness is at or below HUNGER_THRESHOLD
     */
    public static boolean isHungry(Cat cat) {
        return (cat.getFullness() <= HUNGER_THRESHOLD);
    }

    /**
     * Applies an additional happiness penalty if the cat's fulness is below HUNGER_THRESHOLD.
     * 
     * @param cat the cat to apply the penalty to
     */
    public static void applyHungerPenalty(Cat cat) {
        if (isHungry(cat)) {
            decreaseHappiness(cat, HAPPINESS_DECAY_RATE * 2);
        }
    }
}
