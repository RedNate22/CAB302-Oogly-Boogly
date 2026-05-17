package com.mathcat.mathcat.dao;

import com.mathcat.mathcat.database.DatabaseManager;
import com.mathcat.mathcat.models.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ItemDAOTest {

    // Use a fixed cat ID for all tests
    private static final int CAT_ID = 1;

    @BeforeEach
    void setup() throws SQLException {
        // Use in-memory database so tests don't touch the real database file
        DatabaseManager.useInMemoryDatabase();
        DatabaseManager.initialiseDatabase();
    }

    @Nested
    class AddItem {

        @Test
        void addsNewItemToInventory() {
            ItemDAO.addItem(CAT_ID, "FOOD_TUNA");
            ArrayList<Item> inventory = ItemDAO.loadInventory(CAT_ID);
            assertEquals(1, inventory.size());
            assertEquals("FOOD_TUNA", inventory.get(0).getItemId());
        }

        @Test
        void newItemStartsWithQuantityOne() {
            ItemDAO.addItem(CAT_ID, "FOOD_TUNA");
            ArrayList<Item> inventory = ItemDAO.loadInventory(CAT_ID);
            assertEquals(1, inventory.get(0).getQuantity());
        }

        @Test
        void incrementsQuantityIfItemAlreadyOwned() {
            ItemDAO.addItem(CAT_ID, "FOOD_TUNA");
            ItemDAO.addItem(CAT_ID, "FOOD_TUNA");
            ArrayList<Item> inventory = ItemDAO.loadInventory(CAT_ID);
            // Should still be one row, but quantity 2
            assertEquals(1, inventory.size());
            assertEquals(2, inventory.get(0).getQuantity());
        }

        @Test
        void canAddDifferentItems() {
            ItemDAO.addItem(CAT_ID, "FOOD_TUNA");
            ItemDAO.addItem(CAT_ID, "TOY_BALL");
            ArrayList<Item> inventory = ItemDAO.loadInventory(CAT_ID);
            assertEquals(2, inventory.size());
        }
    }

    @Nested
    class UseItem {

        @Test
        void returnsTrueWhenItemUsedSuccessfully() {
            ItemDAO.addItem(CAT_ID, "FOOD_TUNA");
            assertTrue(ItemDAO.useItem(CAT_ID, "FOOD_TUNA"));
        }

        @Test
        void returnsFalseWhenCatHasNoItem() {
            assertFalse(ItemDAO.useItem(CAT_ID, "FOOD_TUNA"));
        }

        @Test
        void decrementsQuantityByOne() {
            ItemDAO.addItem(CAT_ID, "FOOD_TUNA");
            ItemDAO.addItem(CAT_ID, "FOOD_TUNA"); // quantity is now 2
            ItemDAO.useItem(CAT_ID, "FOOD_TUNA");
            ArrayList<Item> inventory = ItemDAO.loadInventory(CAT_ID);
            assertEquals(1, inventory.get(0).getQuantity());
        }

        @Test
        void removesItemWhenQuantityReachesZero() {
            ItemDAO.addItem(CAT_ID, "FOOD_TUNA"); // quantity 1
            ItemDAO.useItem(CAT_ID, "FOOD_TUNA"); // quantity hits 0 — row deleted
            ArrayList<Item> inventory = ItemDAO.loadInventory(CAT_ID);
            assertTrue(inventory.isEmpty());
        }
    }

    @Nested
    class LoadInventory {

        @Test
        void returnsEmptyListWhenNoItems() {
            ArrayList<Item> inventory = ItemDAO.loadInventory(CAT_ID);
            assertTrue(inventory.isEmpty());
        }

        @Test
        void returnsFullItemDetails() {
            ItemDAO.addItem(CAT_ID, "FOOD_TUNA");
            ArrayList<Item> inventory = ItemDAO.loadInventory(CAT_ID);
            Item item = inventory.get(0);
            // Check catalog details are loaded correctly
            assertEquals("Tuna", item.getItemName());
            assertEquals(30.0, item.getEffectAmount());
        }

        @Test
        void skipsUnknownItemIds() {
            // Manually insert a row with an item ID that doesn't exist in the catalog
            try {
                var conn = DatabaseManager.getConnection();
                var stmt = conn.prepareStatement(
                        "INSERT INTO items (pet_id, item_id, quantity) VALUES (?, ?, 1)");
                stmt.setInt(1, CAT_ID);
                stmt.setString(2, "TEST_ITEM");
                stmt.executeUpdate();
            } catch (Exception e) {
                fail("Test setup failed: " + e.getMessage());
            }

            // loadInventory should skip the unknown item and return empty
            ArrayList<Item> inventory = ItemDAO.loadInventory(CAT_ID);
            assertTrue(inventory.isEmpty());
        }
    }
}