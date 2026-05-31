package com.mathcat.mathcat.services;

import com.mathcat.mathcat.dao.CatDAO;
import com.mathcat.mathcat.models.Cat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages XP gain and level progression rules for the cat. The {@link Cat} holds current XP and level as
 * data; this class owns the rules.
 */
public final class LevelSystem {

    // XP required to reach each level. Index = level, value = XP threshold.
    // e.g. XP_THRESHOLDS[2] = 200 means 200 XP is needed to reach level 2
    private static final double[] XP_THRESHOLDS = {0, // level 0 (unused)
            0, // level 1 (starting level)
            100, // level 2
            250, // level 3
            500, // level 4
            850, // level 5
            1300, // level 6
            1850, // level 7
            2500, // level 8
            3250, // level 9
            4100, // level 10
    };

    /** The highest level a cat can reach, determined by the length of the XP threshold table. */
    public static final int MAX_LEVEL = XP_THRESHOLDS.length - 1;

    private static final Logger LOG = LoggerFactory.getLogger(LevelSystem.class);

    private LevelSystem() {}

    /**
     * Returns the XP required to reach the next level from the given level.
     *
     * @param level the current level
     * @return the XP threshold for the next level, or -1 if already at {@link #MAX_LEVEL}
     */
    public static double getXpToNextLevel(double level) {
        if (level >= MAX_LEVEL) {
            return -1;
        }
        int levelInt = (int) level;
        return XP_THRESHOLDS[levelInt + 1];
    }

    /**
     * Checks if the cat has enough XP to level up.
     * 
     * @param cat the cat to check
     * @return true if the cat can level up
     */
    public static boolean canLevelUp(Cat cat) {
        if (cat.getLevel() >= MAX_LEVEL) {
            return false;
        }
        return cat.getXp() >= XP_THRESHOLDS[cat.getLevel() + 1];
    }

    /**
     * Increments the cat's level. Excess XP carries over to the next level. Does nothing if the cat
     * is already at {@link #MAX_LEVEL}.
     * 
     * @param cat the cat to level up.
     */
    public static void levelUp(Cat cat) {
        if (cat.getLevel() >= MAX_LEVEL) {
            return;
        }

        int oldLevel = cat.getLevel();
        double excessXp = cat.getXp() - XP_THRESHOLDS[cat.getLevel() + 1];
        cat.setLevel(cat.getLevel() + 1);
        cat.setXp(Math.max(0, excessXp));
        CatDAO.save(cat);
        LOG.debug("Level up! {} -> {}, excess XP carried: {}", oldLevel, cat.getLevel(),
                String.format("%.2f", cat.getXp()));
    }

    /**
     * Adds XP to the cat and triggers a {@link #levelUp(Cat) level up} if the threshold is reached.
     * 
     * @param cat the cat to apply XP to
     * @param amount the amount of XP to add
     */
    public static void applyXp(Cat cat, double amount) {
        cat.setXp(cat.getXp() + amount);
        LOG.debug("+{} XP applied. Total: {} (level {})", String.format("%.2f", amount),
                String.format("%.2f", cat.getXp()), cat.getLevel());
        while (canLevelUp(cat)) {
            levelUp(cat);
        }
        CatDAO.save(cat);
    }

}
