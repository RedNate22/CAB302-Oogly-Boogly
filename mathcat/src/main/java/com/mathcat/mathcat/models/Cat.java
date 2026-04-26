package com.mathcat.mathcat.models;

import java.util.ArrayList;
import java.time.LocalDateTime;

/**
 * Represents the player's virtual cat, holding its current stats, level, and inventory.
 */
public final class Cat {
    private int catId;
    private int userId; // associates cat with its owner
    private String catName;
    private String catSprite; // path/to/file to match cat with a sprite

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

    /**
     * @return int
     */
    public int getCatId() {
        return catId;
    }

    /**
     * @param catId
     */
    public void setCatId(int catId) {
        this.catId = catId;
    }

    /**
     * @return String
     */
    public String getCatName() {
        return catName;
    }

    /**
     * @param catName
     */
    public void setCatName(String catName) {
        this.catName = catName;
    }

    /**
     * @return String
     */
    public String getCatSprite() {
        return catSprite;
    }

    /**
     * @param catSprite
     */
    public void setCatSprite(String catSprite) {
        this.catSprite = catSprite;
    }

    /**
     * @return double
     */
    public double getHappiness() {
        return happiness;
    }

    /**
     * @param happiness
     */
    public void setHappiness(double happiness) {
        this.happiness = happiness;
    }

    /**
     * @return double
     */
    public double getFullness() {
        return fullness;
    }

    /**
     * @param fullness
     */
    public void setFullness(double fullness) {
        this.fullness = fullness;
    }

    /**
     * @return double
     */
    public double getEnergy() {
        return energy;
    }

    /**
     * @param energy
     */
    public void setEnergy(double energy) {
        this.energy = energy;
    }

    /**
     * @return int
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @return double
     */
    public double getXp() {
        return xp;
    }

    /**
     * @param xp
     */
    public void setXp(double xp) {
        this.xp = xp;
    }

    /**
     * @return int
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return ArrayList<Item>
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * @param items
     */
    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    /**
     * @return LocalDateTime
     */
    public LocalDateTime getLastSaved() {
        return lastSaved;
    }

    /**
     * @param lastSaved
     */
    public void setLastSaved(LocalDateTime lastSaved) {
        this.lastSaved = lastSaved;
    }
}
