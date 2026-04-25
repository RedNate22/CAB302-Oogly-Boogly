package com.mathcat.mathcat.models;

import java.util.ArrayList;
import java.time.LocalDateTime;

/**
 * Represents the virtual cat and its current state. Does not handle persistence or UI concerns.
 */
public class Cat {
    private String catName;

    private double happiness;
    private double fullness;
    private double energy;

    public static final double MAX_HAPPINESS = 100.0;
    public static final double MAX_FULLNESS = 100.0;
    public static final double MAX_ENERGY = 100.0;

    private int level;
    private double xp;

    private int userId; // associates cat with its owner
    private LocalDateTime lastSaved; // to calculate offline stat decay

    private ArrayList<Item> items;

    /**
     *
     */
    public Cat(String catName) {
        this.catName = catName;
        this.happiness = 100.0;
        this.fullness = 100.0;
        this.energy = 0.0;
        this.level = 1;
        this.xp = 0.0;
    }

    /**
     * @return
     */
    public String GetCatName() {
        return catName;
    }

    /**
     * @param name
     */
    public void SetCatName(String name) {
        this.catName = name;
    }

    /**
     * @return
     */
    public double GetHappiness() {
        return happiness;
    }

    /**
     * @param happiness
     */
    public void SetHappiness(double happiness) {
        this.happiness = happiness;
    }

    public double GetFullness() {
        return fullness;
    }

    public void SetFullness(double fullness) {
        this.fullness = fullness;
    }

    public double GetEnergy() {
        return energy;
    }

    public void SetEnergy(double energy) {
        this.energy = energy;
    }

    public int GetLevel() {
        return level;
    }

    public void SetLevel(int level) {
        this.level = level;
    }

    public double GetXp() {
        return xp;
    }

    public void SetXp(double xp) {
        this.xp = xp;
    }

    public int GetId() {
        return userId;
    }


    public void SetId(int id) {
        this.userId = id;
    }
}
