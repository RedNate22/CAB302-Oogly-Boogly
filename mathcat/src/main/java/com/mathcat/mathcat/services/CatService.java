package com.mathcat.mathcat.services;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.mathcat.mathcat.models.Cat;
import com.mathcat.mathcat.models.Item;
import com.mathcat.mathcat.dao.CatDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles stat modification, validation, decay calculation, and persistence for the cat.
 */
public final class CatService {
    /** Maximum value for any cat stat. */
    public static final double MAX_STAT = 100.0;
    /** Minimum value for any cat stat. */
    public static final double MIN_STAT = 0.00;
    /** Happiness decay applied per minute under normal conditions. At 0.07 per minute, happiness
     * reaches 0 in approximately 24 hours normally, or 8 hours when hungry (combined base and
     * penalty rate of 0.21 per minute). */
    public static final double HAPPINESS_DECAY_RATE = 0.07;
    /** Fullness decay applied per minute. At 0.07 per minute, fullness reaches 0 in approximately
     * 24 hours. */
    public static final double FULLNESS_DECAY_RATE = 0.07;
    /** Energy regenerated per minute at maximum fullness. At 1.0 per minute, energy reaches 100
     * in approximately 100 minutes. Scales proportionally with current fullness. */
    public static final double ENERGY_REGEN_RATE = 1.0;
    /** Fullness level at or below which the cat is considered hungry and receives a happiness
     * penalty. */
    public static final double HUNGER_THRESHOLD = 25.0;
    /** Maximum energy the cat can regenerate per day via the scheduler. */
    public static final double DAILY_ENERGY_CAP = 100.0;

    private static final Logger LOG = LoggerFactory.getLogger(CatService.class);

    private CatService() {}

    /**
     * Returns true if the given name is a valid cat name.
     *
     * @param name the cat name to validate
     * @return true if the name contains only letters and is 1-10 characters
     */
    public static boolean isValidCatName(String name) {
        return name.matches("^[A-Za-z]{1,10}$");
    }

    /**
     * Returns true if the given value is within the standard stat bounds.
     *
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
     * Increases the cat's happiness by the given amount and saves.
     *
     * @param cat the cat to increase happiness for
     * @param value the amount to increase by
     */
    public static void increaseHappiness(Cat cat, double value) {
        cat.setHappiness(clampStat(cat.getHappiness() + value));
        cat.setLastSaved(LocalDateTime.now());
        CatDAO.save(cat);
    }

    /**
     * Decreases the cat's happiness by the given amount, optionally saving.
     *
     * @param cat the cat to decrease happiness for
     * @param value the amount to decrease by
     * @param persist whether to save to the DAO after mutating
     */
    private static void decreaseHappiness(Cat cat, double value, boolean persist) {
        cat.setHappiness(clampStat(cat.getHappiness() - value));
        cat.setLastSaved(LocalDateTime.now());
        if (persist) {
            CatDAO.save(cat);
        }
    }

    /**
     * Decreases the cat's happiness by the given amount and saves.
     *
     * @param cat the cat to decrease happiness for
     * @param value the amount to decrease by
     */
    public static void decreaseHappiness(Cat cat, double value) {
        decreaseHappiness(cat, value, true);
    }

    /**
     * Increases the cat's fullness by the given amount and saves.
     *
     * @param cat the cat to increase fullness for
     * @param value the amount to increase by
     */
    public static void increaseFullness(Cat cat, double value) {
        cat.setFullness(clampStat(cat.getFullness() + value));
        cat.setLastSaved(LocalDateTime.now());
        CatDAO.save(cat);
    }

    /**
     * Decreases the cat's fullness by the given amount, optionally saving.
     *
     * @param cat the cat to decrease fullness for
     * @param value the amount to decrease by
     * @param persist whether to save to the DAO after mutating
     */
    private static void decreaseFullness(Cat cat, double value, boolean persist) {
        cat.setFullness(clampStat(cat.getFullness() - value));
        cat.setLastSaved(LocalDateTime.now());
        if (persist) {
            CatDAO.save(cat);
        }
    }

    /**
     * Decreases the cat's fullness by the given amount and saves.
     *
     * @param cat the cat to decrease fullness for
     * @param value the amount to decrease by
     */
    public static void decreaseFullness(Cat cat, double value) {
        decreaseFullness(cat, value, true);
    }

    /**
     * Increases the cat's energy by the given amount and saves.
     *
     * @param cat the cat to increase energy for
     * @param value the amount to increase by
     */
    public static void increaseEnergy(Cat cat, double value) {
        cat.setEnergy(clampStat(cat.getEnergy() + value));
        cat.setLastSaved(LocalDateTime.now());
        CatDAO.save(cat);
    }

    /**
     * Decreases the cat's energy by the given amount and saves.
     *
     * @param cat the cat to decrease energy for
     * @param value the amount to decrease by
     */
    public static void decreaseEnergy(Cat cat, double value) {
        cat.setEnergy(clampStat(cat.getEnergy() - value));
        cat.setLastSaved(LocalDateTime.now());
        CatDAO.save(cat);
    }

    /**
     * Called by {@link CatScheduler} periodically to regenerate energy proportionally to current
     * fullness, subject to a daily cap ({@link #DAILY_ENERGY_CAP}). Resets the cap counter at the
     * start of each new calendar day.
     *
     * @param cat the cat to regenerate energy for
     */
    public static void regenerateEnergy(Cat cat) {
        LocalDate today = LocalDate.now();
        if (cat.getEnergyCapResetDate() == null || !cat.getEnergyCapResetDate().equals(today)) {
            cat.setDailyEnergyGained(0.0);
            cat.setEnergyCapResetDate(today);
        }

        if (cat.getDailyEnergyGained() >= DAILY_ENERGY_CAP) {
            return;
        }

        double proportion = cat.getFullness() / MAX_STAT;
        double regen = proportion * ENERGY_REGEN_RATE;
        regen = Math.min(regen, DAILY_ENERGY_CAP - cat.getDailyEnergyGained());

        cat.setEnergy(clampStat(cat.getEnergy() + regen));
        cat.setDailyEnergyGained(cat.getDailyEnergyGained() + regen);
        cat.setLastSaved(LocalDateTime.now());
        CatDAO.save(cat);
        LOG.debug("Energy regen +{} (fullness: {}) daily total: {}/{}",
                String.format("%.2f", regen), String.format("%.2f", cat.getFullness()),
                String.format("%.2f", cat.getDailyEnergyGained()),
                String.format("%.2f", DAILY_ENERGY_CAP));
    }

    /**
     * Applies stat decay based on time elapsed since the cat was last saved. Used on login to
     * account for offline time.
     *
     * @param cat the cat to apply decay to
     */
    public static void applyOfflineDecay(Cat cat) {
        if (cat.getLastSaved() == null) {
            return;
        }

        long minutesElapsed = Duration.between(cat.getLastSaved(), LocalDateTime.now()).toMinutes();
        LOG.debug("Offline decay - {} min elapsed, happiness -{}, fullness -{}", minutesElapsed,
                minutesElapsed * HAPPINESS_DECAY_RATE, minutesElapsed * FULLNESS_DECAY_RATE);

        // Estimate how long the cat was hungry during the offline window.
        // Fullness decays linearly, so we calculate when it crossed HUNGER_THRESHOLD and apply
        // the hunger penalty (HAPPINESS_DECAY_RATE * 2) only for those minutes.
        double minutesUntilHungry =
                Math.max(0.0, (cat.getFullness() - HUNGER_THRESHOLD) / FULLNESS_DECAY_RATE);
        double hungryMinutes = Math.max(0.0, minutesElapsed - minutesUntilHungry);

        decreaseHappiness(cat, minutesElapsed * HAPPINESS_DECAY_RATE, false);
        decreaseHappiness(cat, hungryMinutes * HAPPINESS_DECAY_RATE * 2, false);
        decreaseFullness(cat, minutesElapsed * FULLNESS_DECAY_RATE, false);
        cat.setLastSaved(LocalDateTime.now());
        CatDAO.save(cat);
    }

    /**
     * Returns true if the cat's fullness is at or below the hunger threshold.
     *
     * @param cat the cat to check hunger of
     * @return true if the cat's fullness is at or below {@link #HUNGER_THRESHOLD}
     */
    public static boolean isHungry(Cat cat) {
        return (cat.getFullness() <= HUNGER_THRESHOLD);
    }

    /**
     * Applies an additional happiness penalty if the cat's fullness is below
     * {@link #HUNGER_THRESHOLD}.
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
        LOG.debug("Item added: {} ({} +{})", item.getItemName(), item.getEffectType(),
                        item.getEffectAmount());
        CatDAO.save(cat);
    }

    /**
     * Uses an item from the cat's inventory, applying its effect then removing it. Saves twice:
     * once inside applyItem (for the stat change) and once here (for the inventory change).
     *
     * @param cat the cat to use the item on
     * @param item the item to use -- must be the exact reference held in the cat's inventory
     * @return true if the item was found and used, false if it was not in the inventory
     */
    public static boolean useItem(Cat cat, Item item) {
        if (!cat.getItems().remove(item)) {
            return false;
        }
        item.applyItem(cat);
        LOG.debug("Item used: {} ({} +{})", item.getItemName(), item.getEffectType(),
                        item.getEffectAmount());
        CatDAO.save(cat);
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
     * Returns the cat's current happiness as a display value.
     *
     * @param cat the cat to get the happiness level of
     * @return the cat's current happiness
     */
    public static double displayHappiness(Cat cat) {
        double catHappiness = cat.getHappiness();
        return catHappiness;
    }

    /**
     * Returns the cat's current fullness as a display value.
     *
     * @param cat the cat to get the hunger level of
     * @return the cat's current fullness
     */
    public static double displayHunger(Cat cat) {
        double catHunger = cat.getFullness();
        return catHunger;
    }

    /**
     * Returns the cat's current energy as a display value.
     *
     * @param cat the cat to get the energy level of
     * @return the cat's current energy
     */
    public static double displayEnergy(Cat cat) {
        double catEnergy = cat.getEnergy();
        return catEnergy;
    }

    /**
     * Returns the cat's current level as a display value.
     *
     * @param cat the cat to get the level of
     * @return the cat's current level
     */
    public static double displayLevel(Cat cat) {
        double catLevel = cat.getLevel();
        return catLevel;
    }

    /**
     * Returns the cat's current XP as a display value.
     *
     * @param cat the cat to get the XP of
     * @return the cat's current XP
     */
    public static double displayXP(Cat cat) {
        double catXP = cat.getXp();
        return catXP;
    }
}
