package com.mathcat.mathcat.models;

import com.mathcat.mathcat.services.CatService;

/**
 * Represents an item that can be used on the cat to modify its stats or apply a cosmetic effect.
 */
public final class Item {
    private String itemId;
    private String itemName;
    private String itemImage; // format, [path/to/image]
    private ItemEffectType effectType;
    private double effectAmount;
    // Tracks how many of this item the cat owns -- only relevant for inventory, not the catalog
    private int quantity;

    /**
     * Creates a new cosmetic Item with the given ID, name, image path, and no stat effects.
     *
     * @param itemId unique catalog identifier (e.g. "COSMETIC_HAT")
     * @param itemName the display name of the item
     * @param itemImage path to the item's sprite
     */
    public Item(String itemId, String itemName, String itemImage) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemImage = itemImage;
        this.effectType = ItemEffectType.COSMETIC;
        this.effectAmount = 0.00;

    }

    /**
     * Creates a new Item that applies a stat effect when used. If effectType is {@link ItemEffectType#COSMETIC},
     * effectAmount is forced to 0.0.
     *
     * @param itemId unique catalog identifier (e.g. "FOOD_TUNA")
     * @param itemName the display name of the item
     * @param itemImage path to the item's sprite
     * @param effectType the type of stat this item affects
     * @param effectAmount the magnitude of the effect
     */
    public Item(String itemId, String itemName, String itemImage, ItemEffectType effectType,
            double effectAmount) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemImage = itemImage;
        this.effectType = effectType;
        this.effectAmount = effectAmount;

        if (this.effectType == ItemEffectType.COSMETIC) {
            this.effectAmount = 0.00;
        }
    }

    /**
     * Returns the item's unique catalog identifier.
     *
     * @return the item's unique catalog identifier
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * Sets the item's catalog identifier.
     *
     * @param itemId the new catalog identifier
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * Returns the item's display name.
     *
     * @return the item's display name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Sets the item's display name.
     *
     * @param itemName the new display name
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Returns the file path to the item's sprite image.
     *
     * @return file path to the item's sprite image
     */
    public String getItemImage() {
        return itemImage;
    }

    /**
     * Sets the file path to the item's sprite image.
     *
     * @param itemImage file path to the item's sprite image
     */
    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    /**
     * Returns the effect type, describing which stat this item affects.
     *
     * @return the {@link ItemEffectType} describing which stat this item affects
     */
    public ItemEffectType getEffectType() {
        return effectType;
    }

    /**
     * Sets the effect type. If set to {@link ItemEffectType#COSMETIC}, resets effectAmount to 0.
     *
     * @param effectType the new effect type
     */
    public void setEffectType(ItemEffectType effectType) {
        if (effectType == ItemEffectType.COSMETIC) {
            this.effectAmount = 0.00;
            this.effectType = effectType;
            return;
        }
        this.effectType = effectType;
    }

    /**
     * Returns the magnitude of the stat effect applied when this item is used.
     *
     * @return the magnitude of the stat effect applied when this item is used
     */
    public double getEffectAmount() {
        return effectAmount;
    }

    /**
     * Sets the effect amount. Has no effect if this item's type is {@link ItemEffectType#COSMETIC}.
     *
     * @param effectAmount the new effect magnitude
     */
    public void setEffectAmount(double effectAmount) {
        if (this.effectType == ItemEffectType.COSMETIC) {
            this.effectAmount = 0.00;
            return;
        }
        this.effectAmount = effectAmount;
    }

    /**
     * Applies this item's effect to the cat by calling the appropriate {@link CatService} method. Cosmetic
     * items have no stat effect.
     *
     * @param cat the cat to apply the effect to
     */
    public void applyItem(Cat cat) {
        switch (effectType) {
            case HAPPINESS:
                CatService.increaseHappiness(cat, effectAmount);
                break;
            case FULLNESS:
                CatService.increaseFullness(cat, effectAmount);
                break;
            case ENERGY:
                CatService.increaseEnergy(cat, effectAmount);
                break;
            case COSMETIC:
                // no stat effect
                break;
        }
    }

    /**
     * Returns how many of this item the cat currently owns.
     *
     * @return how many of this item the cat currently owns
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets how many of this item the cat currently owns.
     *
     * @param quantity the number of this item in the cat's inventory
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
