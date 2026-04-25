package com.mathcat.mathcat.models;

import java.util.ArrayList;
import java.time.LocalDateTime;

/**
 * Represents the player's virtual cat, holding its current stats, level, and inventory.
 */
public class Cat {
    private int catId;
    private int userId; // associates cat with its owner
    private String catName;

    private double happiness;
    private double fullness;
    private double energy;

    private int level;
    private double xp;

    private ArrayList<Item> items;

    // Metadata: to calculate offline stat decay
    private LocalDateTime lastSaved;

    /**
     * Creates a new Cat with default stats at full happiness and fullness, zero energy, level 1, no
     * XP, and no items.
     * 
     * @param catName the display name of the cat
     */
    public Cat(String catName) {
        this.catId = 0;
        this.userId = 0;
        this.catName = catName;
        this.happiness = 100.00;
        this.fullness = 100.00;
        this.energy = 0.00;
        this.level = 1;
        this.xp = 0.00;
        this.items = new ArrayList<>();
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public double getHappiness() {
        return happiness;
    }

    public void setHappiness(double happiness) {
        this.happiness = happiness;
    }

    public double getFullness() {
        return fullness;
    }

    public void setFullness(double fullness) {
        this.fullness = fullness;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getXp() {
        return xp;
    }

    public void setXp(double xp) {
        this.xp = xp;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public LocalDateTime getLastSaved() {
        return lastSaved;
    }

    public void setLastSaved(LocalDateTime lastSaved) {
        this.lastSaved = lastSaved;
    }
}
