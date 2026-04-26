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
     * @return the cat's unique ID, 0 if not yet persisted
     */
    public int getCatId() {
        return catId;
    }

    /**
     * Sets the cat's unique ID. Should only be called by CatDAO after persisting.
     * 
     * @param catId the ID assigned by the data store
     */
    public void setCatId(int catId) {
        this.catId = catId;
    }

    /**
     * @return the cat's display name
     */
    public String getCatName() {
        return catName;
    }

    /**
     * @param catName the new display name
     */
    public void setCatName(String catName) {
        this.catName = catName;
    }

    /**
     * @return file path to the cat's sprite image
     */
    public String getCatSprite() {
        return catSprite;
    }

    /**
     * @param catSprite file path to the cat's sprite image
     */
    public void setCatSprite(String catSprite) {
        this.catSprite = catSprite;
    }

    /**
     * @return current happiness, between MIN_STAT and MAX_STAT
     */
    public double getHappiness() {
        return happiness;
    }

    /**
     * @param happines the new happiness value
     */
    public void setHappiness(double happiness) {
        this.happiness = happiness;
    }

    /**
     * @return current fullness, between MIN_STAT and MAX_STAT
     */
    public double getFullness() {
        return fullness;
    }

    /**
     * @param fullness the new fullness value
     */
    public void setFullness(double fullness) {
        this.fullness = fullness;
    }

    /**
     * @return the current energy, between MIN_STAT and MAX_STAT
     */
    public double getEnergy() {
        return energy;
    }

    /**
     * @param energy the new energy value
     */
    public void setEnergy(double energy) {
        this.energy = energy;
    }

    /**
     * @return the cat's current level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the new level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @return the cat's current XP
     */
    public double getXp() {
        return xp;
    }

    /**
     * @param xp the new XP value
     */
    public void setXp(double xp) {
        this.xp = xp;
    }

    /**
     * @return the ID of the user who owns this cat
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the ID of the owning user, assigned by CatDAO
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the cat's current inventory of items
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * @param items the new inventory list
     */
    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    /**
     * @return the timestamp of the last save, or null if never saved
     */
    public LocalDateTime getLastSaved() {
        return lastSaved;
    }

    /**
     * @param lastSaved the timestamp to record as the last save time
     */
    public void setLastSaved(LocalDateTime lastSaved) {
        this.lastSaved = lastSaved;
    }
}
