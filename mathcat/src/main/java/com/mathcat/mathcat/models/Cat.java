package com.mathcat.mathcat.models;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents the player's virtual cat, holding its current stats, level, and inventory.
 */
public final class Cat {
    private int catId;
    private int userId; // associates cat with its owner
    private String catName;
    private String catSprite; // path/to/file to match cat with a sprite
    private String catAccessory;

    private double happiness;
    private double fullness;
    private double energy;

    private int level;
    private double xp;

    private ArrayList<Item> items;

    // Metadata: to calculate offline stat decay
    private LocalDateTime lastSaved;

    // Daily energy cap tracking
    private double dailyEnergyGained;
    private LocalDate energyCapResetDate;

    /**
     * Creates a new Cat with default stats at full happiness, fullness, and energy, level 1, no XP,
     * and no items.
     *
     * @param catName the display name of the cat
     */
    public Cat(String catName) {
        this.catId = 0;
        this.userId = 0;
        this.catName = catName;
        this.catSprite = null;
        this.catAccessory = null;
        this.happiness = 100.00;
        this.fullness = 100.00;
        this.energy = 100.00;
        this.level = 1;
        this.xp = 0.00;
        this.items = new ArrayList<>();
        this.dailyEnergyGained = 0.0;
        this.energyCapResetDate = null;
    }

    /**
     * Returns the cat's unique ID, or 0 if not yet persisted to the database.
     *
     * @return the cat's unique ID, 0 if not yet persisted
     */
    public int getCatId() {
        return catId;
    }

    /**
     * Sets the cat's unique ID. Should only be called by {@link com.mathcat.mathcat.dao.CatDAO
     * CatDAO} after persisting.
     *
     * @param catId the ID assigned by the data store
     */
    public void setCatId(int catId) {
        this.catId = catId;
    }

    /**
     * Returns the cat's display name.
     *
     * @return the cat's display name
     */
    public String getCatName() {
        return catName;
    }

    /**
     * Sets the cat's display name.
     *
     * @param catName the new display name
     */
    public void setCatName(String catName) {
        this.catName = catName;
    }

    /**
     * Returns the file path to the cat's sprite image.
     *
     * @return file path to the cat's sprite image
     */
    public String getCatSprite() {
        return catSprite;
    }

    /**
     * Sets the file path to the cat's sprite image.
     *
     * @param catSprite file path to the cat's sprite image
     */
    public void setCatSprite(String catSprite) {
        this.catSprite = catSprite;
    }

    /**
     * Returns the file path to the cat's accessory image.
     *
     * @return file path to the cat's accessory image
     */
    public String getCatAccessory() {
        return catAccessory;
    }

    /**
     * Sets the file path to the cat's accessory image.
     *
     * @param catAccessory file path to the cat's accessory image
     */
    public void setCatAccessory(String catAccessory) {
        this.catAccessory = catAccessory;
    }

    /**
     * Returns the cat's current happiness level.
     *
     * @return current happiness, between {@link com.mathcat.mathcat.services.CatService#MIN_STAT
     *         MIN_STAT} and {@link com.mathcat.mathcat.services.CatService#MAX_STAT MAX_STAT}
     */
    public double getHappiness() {
        return happiness;
    }

    /**
     * Sets the cat's happiness level.
     *
     * @param happiness the new happiness value
     */
    public void setHappiness(double happiness) {
        this.happiness = happiness;
    }

    /**
     * Returns the cat's current fullness level.
     *
     * @return current fullness, between {@link com.mathcat.mathcat.services.CatService#MIN_STAT
     *         MIN_STAT} and {@link com.mathcat.mathcat.services.CatService#MAX_STAT MAX_STAT}
     */
    public double getFullness() {
        return fullness;
    }

    /**
     * Sets the cat's fullness level.
     *
     * @param fullness the new fullness value
     */
    public void setFullness(double fullness) {
        this.fullness = fullness;
    }

    /**
     * Returns the cat's current energy level.
     *
     * @return the current energy, between {@link com.mathcat.mathcat.services.CatService#MIN_STAT
     *         MIN_STAT} and {@link com.mathcat.mathcat.services.CatService#MAX_STAT MAX_STAT}
     */
    public double getEnergy() {
        return energy;
    }

    /**
     * Sets the cat's energy level.
     *
     * @param energy the new energy value
     */
    public void setEnergy(double energy) {
        this.energy = energy;
    }

    /**
     * Returns the cat's current level.
     *
     * @return the cat's current level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the cat's level.
     *
     * @param level the new level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Returns the cat's current XP.
     *
     * @return the cat's current XP
     */
    public double getXp() {
        return xp;
    }

    /**
     * Sets the cat's XP.
     *
     * @param xp the new XP value
     */
    public void setXp(double xp) {
        this.xp = xp;
    }

    /**
     * Returns the ID of the user who owns this cat.
     *
     * @return the ID of the user who owns this cat
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the owning user.
     *
     * @param userId the ID of the owning user, assigned by {@link com.mathcat.mathcat.dao.CatDAO
     *        CatDAO}
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Returns the cat's current inventory of items.
     *
     * @return the cat's current inventory of items
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * Sets the cat's inventory list.
     *
     * @param items the new inventory list
     */
    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    /**
     * Returns the timestamp of the last save, or null if never saved.
     *
     * @return the timestamp of the last save, or null if never saved
     */
    public LocalDateTime getLastSaved() {
        return lastSaved;
    }

    /**
     * Records the given timestamp as the last save time.
     *
     * @param lastSaved the timestamp to record as the last save time
     */
    public void setLastSaved(LocalDateTime lastSaved) {
        this.lastSaved = lastSaved;
    }

    /**
     * Returns the total energy gained today via regeneration.
     *
     * @return total energy gained today via regeneration
     */
    public double getDailyEnergyGained() {
        return dailyEnergyGained;
    }

    /**
     * Sets the daily energy gained total.
     *
     * @param dailyEnergyGained the new daily energy gained total
     */
    public void setDailyEnergyGained(double dailyEnergyGained) {
        this.dailyEnergyGained = dailyEnergyGained;
    }

    /**
     * Returns the date the daily energy cap was last reset, or {@code null} if never reset.
     *
     * @return the date the daily energy cap was last reset, or {@code null} if never reset
     */
    public LocalDate getEnergyCapResetDate() {
        return energyCapResetDate;
    }

    /**
     * Sets the date the daily energy cap was last reset.
     *
     * @param energyCapResetDate the date to record as the last cap reset
     */
    public void setEnergyCapResetDate(LocalDate energyCapResetDate) {
        this.energyCapResetDate = energyCapResetDate;
    }
}
