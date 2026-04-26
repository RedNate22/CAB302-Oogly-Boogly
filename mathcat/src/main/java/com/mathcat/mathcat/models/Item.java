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

    /**
     * Creates a new cosmetic Item with the given name, image path, and no stat effects.
     * 
     * @param itemName the display name of the item
     * @param itemImage path to the item's sprite
     */
    public Item(String itemName, String itemImage) {
        this.itemName = itemName;
        this.itemImage = itemImage;
        this.effectType = ItemEffectType.COSMETIC;
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
    public Item(String itemName, String itemImage, ItemEffectType effectType, double effectAmount) {
        this.itemName = itemName;
        this.itemImage = itemImage;
        this.effectType = effectType;
        this.effectAmount = effectAmount;

        if (this.effectType == ItemEffectType.COSMETIC) {
            this.effectAmount = 0.00;
        }
    }

    /**
     * @return String
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * @param itemId
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * @return String
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return String
     */
    public String getItemImage() {
        return itemImage;
    }

    /**
     * @param itemImage
     */
    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    /**
     * @return ItemEffectType
     */
    public ItemEffectType getEffectType() {
        return effectType;
    }

    /**
     * @param effectType
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
     * @return double
     */
    public double getEffectAmount() {
        return effectAmount;
    }

    /**
     * @param effectAmount
     */
    public void setEffectAmount(double effectAmount) {
        if (this.effectType == ItemEffectType.COSMETIC) {
            this.effectAmount = 0.00;
            return;
        }
        this.effectAmount = effectAmount;
    }

    // TODO
    public void applyItem(Cat cat) {


    }
}

