package com.mathcat.mathcat.models;

/**
 * Represents an item that can be used on the cat to modify its stats or apply a cosmetic effect.
 */
public class Item {
    private String itemId;
    private String itemName;
    private String itemImage; // format, [path/to/image]
    private EffectType effectType;
    private double effectAmount;

    /**
     * Creates a new cosmetic Item with the given name, image path, and no stat effects.
     * 
     * @param itemName the display name of the item
     * @param itemImage path to the item's sprite
     */
    public Item(String itemName, String itemImage) {
        this.itemName = itemName;
        this.itemImage = itemImage;
        this.effectType = EffectType.COSMETIC;
        this.effectAmount = 0.00;
    }

    /**
     * Creates a new Item that applies a stat effect when used. If effectType is COSMETIC,
     * effectAmount is forced to 0.0.
     * 
     * @param itemName the display name of the item
     * @param itemImage path to the item's sprite
     * @param effectType the type of stat this item affects
     * @param effectAmount the magnitude of the effect
     */
    public Item(String itemName, String itemImage, EffectType effectType, double effectAmount) {
        this.itemName = itemName;
        this.itemImage = itemImage;
        this.effectType = effectType;
        this.effectAmount = effectAmount;

        if (this.effectType == EffectType.COSMETIC) {
            this.effectAmount = 0.00;
        }
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public EffectType getEffectType() {
        return effectType;
    }

    public void setEffectType(EffectType effectType) {
        this.effectType = effectType;
    }

    public double getEffectAmount() {
        return effectAmount;
    }

    public void setEffectAmount(double effectAmount) {
        this.effectAmount = effectAmount;
    }
}

