package com.mathcat.mathcat.services;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.mathcat.mathcat.models.Cat;
import com.mathcat.mathcat.models.Item;
import com.mathcat.mathcat.dao.CatDAO;

/**
 * Handles stat modification, validation, decay calculation, and persistence for the cat.
 */
public final class CatService {
    public static final double MAX_STAT = 100.0;
    public static final double MIN_STAT = 0.00;
    public static final double HAPPINESS_DECAY_RATE = 0.07; // per min: hits 0 in ~24 hours normally, ~8 hours when hungry (0.07 + 0.07*2 penalty = 0.21/min)
    public static final double FULLNESS_DECAY_RATE = 0.07; // per min: hits 0 in ~24 hours
    public static final double ENERGY_REGEN_RATE = 1.0; // per min at max fullness: hits 100 in ~100
                                                        // min
    public static final double HUNGER_THRESHOLD = 25.0;
    public static final double DAILY_ENERGY_CAP = 100.0;

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
     * @return true if the value is within {@link #MIN_STAT} and {@link #MAX_STAT} bounds
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
     * Clamps a stat value between the standard {@link #MIN_STAT} and {@link #MAX_STAT} bounds.
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
     * @param persist whether to save to the DAO after mutating
     */
    private static void decreaseHappiness(Cat cat, double value, boolean persist) {
        cat.setHappiness(clampStat(cat.getHappiness() - value));
        cat.setLastSaved(LocalDateTime.now());
        if (persist)
            CatDAO.save(cat);
    }

    /**
     * @param cat the cat to decrease happiness for
     * @param value the amount to decrease by
     */
    public static void decreaseHappiness(Cat cat, double value) {
        decreaseHappiness(cat, value, true);
    }

    /**
     * @param cat the cat to increase fullness for
     * @param value the amount to increase by
     */
    public static void increaseFullness(Cat cat, double value) {
        cat.setFullness(clampStat(cat.getFullness() + value));
        cat.setLastSaved(LocalDateTime.now());
        CatDAO.save(cat);
    }

    /**
     * @param cat the cat to decrease fullness for
     * @param value the amount to decrease by
     * @param persist whether to save to the DAO after mutating
     */
    private static void decreaseFullness(Cat cat, double value, boolean persist) {
        cat.setFullness(clampStat(cat.getFullness() - value));
        cat.setLastSaved(LocalDateTime.now());
        if (persist)
            CatDAO.save(cat);
    }

    /**
     * @param cat the cat to decrease fullness for
     * @param value the amount to decrease by
     */
    public static void decreaseFullness(Cat cat, double value) {
        decreaseFullness(cat, value, true);
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
     * Called by {@link CatScheduler} periodically to regenerate energy proportionally to current fullness,
     * subject to a daily cap ({@link #DAILY_ENERGY_CAP}). Resets the cap counter at the start of each new calendar day.
     *
     * @param cat the cat to regenerate energy for
     */
    public static void regenerateEnergy(Cat cat) {
        LocalDate today = LocalDate.now();
        if (cat.getEnergyCapResetDate() == null || !cat.getEnergyCapResetDate().equals(today)) {
            cat.setDailyEnergyGained(0.0);
            cat.setEnergyCapResetDate(today);
        }

        if (cat.getDailyEnergyGained() >= DAILY_ENERGY_CAP)
            return;

        double proportion = cat.getFullness() / MAX_STAT;
        double regen = proportion * ENERGY_REGEN_RATE;
        regen = Math.min(regen, DAILY_ENERGY_CAP - cat.getDailyEnergyGained());

        cat.setEnergy(clampStat(cat.getEnergy() + regen));
        cat.setDailyEnergyGained(cat.getDailyEnergyGained() + regen);
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

        long minutesElapsed = Duration.between(cat.getLastSaved(), LocalDateTime.now()).toMinutes();

        // persist=false skips the individual saves inside each method; we do one combined save
        // below
        decreaseHappiness(cat, minutesElapsed * HAPPINESS_DECAY_RATE, false);
        decreaseFullness(cat, minutesElapsed * FULLNESS_DECAY_RATE, false);
        cat.setLastSaved(LocalDateTime.now());
        CatDAO.save(cat); // single save after both stats are updated
    }

    /**
     * @param cat the cat to check hunger of
     * @return true if the cat's fullness is at or below {@link #HUNGER_THRESHOLD}
     */
    public static boolean isHungry(Cat cat) {
        return (cat.getFullness() <= HUNGER_THRESHOLD);
    }

    /**
     * Applies an additional happiness penalty if the cat's fullness is below {@link #HUNGER_THRESHOLD}.
     *
     * @param cat the cat to apply the penalty to
     */
    public static void applyHungerPenalty(Cat cat) {
        if (isHungry(cat)) {
            decreaseHappiness(cat, HAPPINESS_DECAY_RATE * 2);
        }
    }

    /**
     * Adds an item to the cat's inventory and saves.
     *
     * @param cat the cat to give the item to
     * @param item the item to add
     */
    public static void addItem(Cat cat, Item item) {
        cat.getItems().add(item);
        CatDAO.save(cat);
    }

    /**
     * Uses an item from the cat's inventory, applying its effect then removing it. Saves twice:
     * once inside applyItem (for the stat change) and once here (for the inventory change).
     *
     * @param cat the cat to use the item on
     * @param item the item to use — must be the exact reference held in the cat's inventory
     * @return true if the item was found and used, false if it was not in the inventory
     */
    public static boolean useItem(Cat cat, Item item) {
        // remove returns false if the item wasn't in the list
        if (!cat.getItems().remove(item))
            return false;
        item.applyItem(cat); // applies the stat effect and saves the stat change
        CatDAO.save(cat); // save the inventory change (item removed)
        return true;
    }

    /**
     * Removes an item from the cat's inventory without applying its effect.
     *
     * @param cat the cat to remove the item from
     * @param item the item to remove
     */
    public static void removeItem(Cat cat, Item item) {
        cat.getItems().remove(item);
        CatDAO.save(cat);
    }

    /**
     * Displays the cat's happiness level.
     *
     * @param cat the cat to get the happiness level
     */
    public static void displayHappiness(Cat cat) {
        cat.getHappiness();
    }

    /**
     * Displays the cat's hunger level.
     *
     * @param cat the cat to get the hunger level
     */
    public static void displayHunger(Cat cat) {
        cat.getFullness();
    }

    /**
     * Displays the cat's energy level.
     *
     * @param cat the cat to get the energy level
     */
    public static void displayEnergy(Cat cat) {
        cat.getEnergy();
    }
}
