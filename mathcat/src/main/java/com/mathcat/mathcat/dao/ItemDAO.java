package com.mathcat.mathcat.dao;

import com.mathcat.mathcat.models.Item;
import com.mathcat.mathcat.models.ItemEffectType;
import com.mathcat.mathcat.database.DatabaseManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.sql.*;

/**
 * Provides access to the static item catalog. Items are predefined and shared across all users;
 * the catalog is populated once on class load.
 */
public class ItemDAO {

    // Ordered map so getAll() returns items in insertion order (useful for shop display)
    private static final Map<String, Item> catalog = new LinkedHashMap<>();

    static {
        // Fullness items
        register(new Item("FOOD_TUNA",   "Tuna",   "sprites/items/tuna.png",   ItemEffectType.FULLNESS,  30.0));
        register(new Item("FOOD_MILK",   "Milk",   "sprites/items/milk.png",   ItemEffectType.FULLNESS,  15.0));
        register(new Item("FOOD_KIBBLE", "Kibble", "sprites/items/kibble.png", ItemEffectType.FULLNESS,  10.0));

        // Happiness items
        register(new Item("TOY_BALL",    "Ball",          "sprites/items/ball.png",    ItemEffectType.HAPPINESS, 25.0));
        register(new Item("TOY_LASER",   "Laser Pointer", "sprites/items/laser.png",   ItemEffectType.HAPPINESS, 20.0));
        register(new Item("TOY_CATNIP",  "Catnip",        "sprites/items/catnip.png",  ItemEffectType.HAPPINESS, 35.0));

        // Energy items
        register(new Item("ENERGY_NAP",  "Cozy Blanket", "sprites/items/blanket.png", ItemEffectType.ENERGY, 40.0));

        // Cosmetic items
        register(new Item("COSMETIC_HAT",  "Top Hat",    "sprites/items/hat.png",    ItemEffectType.COSMETIC, 0.0));
        register(new Item("COSMETIC_BOW",  "Bow Tie",    "sprites/items/bow.png",    ItemEffectType.COSMETIC, 0.0));
    }

    private ItemDAO() {}

    private static void register(Item item) {
        catalog.put(item.getItemId(), item);
    }

    /**
     * Retrieves an item from the catalog by its ID.
     *
     * @param itemId the catalog ID to look up (e.g. "FOOD_TUNA")
     * @return the matching Item, or null if not found
     */
    public static Item getById(String itemId) {
        return catalog.get(itemId);
    }

    /**
     * @return a copy of all items in the catalog, in insertion order
     */
    public static List<Item> getAll() {
        return new ArrayList<>(catalog.values());
    }
}