package com.mathcat.mathcat.services;

import com.mathcat.mathcat.models.Cat;

public final class CatService {

    // TODO
    public static boolean isValidCatName(String name) {
        return false;
    }

    public static final double MAX_STAT = 100.0;
    public static final double MIN_STAT = 0.00;

    public static boolean isValidStatValue(double value) {
        return (value >= MIN_STAT && value <= MAX_STAT);
    }

    public static double clampStat(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    public static double clampStat(double value) {
        return clampStat(value, MIN_STAT, MAX_STAT);
    }

    public static void increaseHappiness() {

    }

    public static void decreaseHappiness() {

    }

    public static void increaseFullness() {

    }

    public static void decreaseFullness() {

    }

    public static void increaseEnergy() {

    }

    public static void decreaseEnergy() {

    }

    public static boolean isHungry() {
        return false;
    }

    public static void regenerateEnergy(Cat cat) {

    }

    public static void applyOfflineDecay(Cat cat) {

    }
}
