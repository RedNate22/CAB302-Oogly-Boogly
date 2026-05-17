package com.mathcat.mathcat.dao;

import com.mathcat.mathcat.models.Item;
import com.mathcat.mathcat.models.ItemEffectType;
import com.mathcat.mathcat.database.DatabaseManager;
import java.sql.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * Adds one of the specified item to a cat's inventory. If the cat already has
     * the item, the quantity is incremented by 1 instead of inserting a new row.
     *
     * @param catId  the database ID of the cat
     * @param itemId the catalog ID of the item (e.g. "FOOD_TUNA")
     */
    public static void addItem(int catId, String itemId) {
        String checkSql = "SELECT quantity FROM items WHERE pet_id = ? AND item_id = ?";
        String updateSql = "UPDATE items SET quantity = quantity + 1 WHERE pet_id = ? AND item_id = ?";
        String insertSql = "INSERT INTO items (pet_id, item_id, quantity) VALUES (?, ?, 1)";

        try (PreparedStatement checkStmt =
                     DatabaseManager.getConnection().prepareStatement(checkSql)) {
            checkStmt.setInt(1, catId);
            checkStmt.setString(2, itemId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Cat already has this item — increment quantity
                try (PreparedStatement updateStmt =
                             DatabaseManager.getConnection().prepareStatement(updateSql)) {
                    updateStmt.setInt(1, catId);
                    updateStmt.setString(2, itemId);
                    updateStmt.executeUpdate();
                }
            } else {
                // Cat does not have this item — insert a new row with quantity 1
                try (PreparedStatement insertStmt =
                             DatabaseManager.getConnection().prepareStatement(insertSql)) {
                    insertStmt.setInt(1, catId);
                    insertStmt.setString(2, itemId);
                    insertStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.err.println("[ItemDAO] Error adding item: " + e.getMessage());
        }
    }

    /**
     * Decrements the quantity of an item in a cat's inventory by 1.
     * If the quantity reaches 0, the row is deleted. Uses a transaction
     * so the inventory never contains a row with 0 quantity.
     *
     * @param catId  the database ID of the cat
     * @param itemId the catalog ID of the item to use
     * @return true if the item was used successfully, false if the cat has none
     */
    public static boolean useItem(int catId, String itemId) {
        String checkSql = "SELECT quantity FROM items WHERE pet_id = ? AND item_id = ?";
        String decrementSql = "UPDATE items SET quantity = quantity - 1 WHERE pet_id = ? AND item_id = ?";
        String deleteSql = "DELETE FROM items WHERE pet_id = ? AND item_id = ? AND quantity <= 0";

        Connection conn = null;
        try {
            conn = DatabaseManager.getConnection();

            // Check the cat actually has this item before doing anything
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, catId);
                checkStmt.setString(2, itemId);
                ResultSet rs = checkStmt.executeQuery();
                if (!rs.next() || rs.getInt("quantity") <= 0) {
                    return false; // cat has none of this item
                }
            }

            // Use a transaction — decrement then delete if 0, atomically
            // This ensures we never end up with a row sitting at 0 quantity
            conn.setAutoCommit(false);

            try (PreparedStatement decrementStmt = conn.prepareStatement(decrementSql)) {
                decrementStmt.setInt(1, catId);
                decrementStmt.setString(2, itemId);
                decrementStmt.executeUpdate();
            }

            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                deleteStmt.setInt(1, catId);
                deleteStmt.setString(2, itemId);
                deleteStmt.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("[ItemDAO] Error using item: " + e.getMessage());
            // Roll back if anything went wrong so inventory stays consistent
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException re) {
                    System.err.println("[ItemDAO] Rollback failed: " + re.getMessage());
                }
            }
            return false;
        } finally {
            // Always restore auto-commit so other queries are not affected
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    System.err.println("[ItemDAO] Failed to restore auto-commit: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Loads all items in a cat's inventory from the database. Each row is matched
     * against the item catalog to get full item details, then added to the list
     * with its saved quantity.
     *
     * @param catId the database ID of the cat
     * @return a list of items the cat currently owns, or an empty list if none
     */
    public static ArrayList<Item> loadInventory(int catId) {
        String sql = "SELECT item_id, quantity FROM items WHERE pet_id = ?";
        ArrayList<Item> inventory = new ArrayList<>();

        try (PreparedStatement stmt =
                     DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, catId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String itemId = rs.getString("item_id");
                int quantity = rs.getInt("quantity");

                // Look up full item details from the static catalog
                Item catalogItem = catalog.get(itemId);
                if (catalogItem == null) {
                    // Item ID in database does not match any catalog entry — skip it
                    System.err.println("[ItemDAO] Unknown item in inventory: " + itemId);
                    continue;
                }

                // Create a copy so each inventory entry is independent from the catalog object
                Item item = new Item(
                        catalogItem.getItemId(),
                        catalogItem.getItemName(),
                        catalogItem.getItemImage(),
                        catalogItem.getEffectType(),
                        catalogItem.getEffectAmount()
                );
                item.setQuantity(quantity);
                inventory.add(item);
            }
        } catch (SQLException e) {
            System.err.println("[ItemDAO] Error loading inventory: " + e.getMessage());
        }

        return inventory;
    }
}