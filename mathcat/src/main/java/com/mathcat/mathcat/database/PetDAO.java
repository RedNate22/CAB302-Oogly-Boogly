package com.mathcat.mathcat.database;

import java.sql.*;

public class PetDAO {

    // Create a new pet for a user
    public static void createPet(String username, String petName, String appearance) {
        String getUserId = "SELECT id FROM users WHERE username = ?";
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(getUserId)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("id");
                String sql = """
                        INSERT INTO pets (user_id, pet_name, appearance)
                        VALUES (?, ?, ?)
                        """;
                try (PreparedStatement petStmt = DatabaseManager.getConnection().prepareStatement(sql)) {
                    petStmt.setInt(1, userId);
                    petStmt.setString(2, petName);
                    petStmt.setString(3, appearance);
                    petStmt.executeUpdate();
                    System.out.println("Pet created for user: " + username);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error creating pet: " + e.getMessage());
        }
    }

    // Save/update pet progress
    public static void savePet(String username, int level, int xp,
                               int happiness, int energy, int fullness,
                               int currency, String items) {
        String sql = """
                UPDATE pets SET level = ?, xp = ?, happiness = ?,
                energy = ?, fullness = ?, currency = ?, items = ?
                WHERE user_id = (SELECT id FROM users WHERE username = ?)
                """;
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, level);
            stmt.setInt(2, xp);
            stmt.setInt(3, happiness);
            stmt.setInt(4, energy);
            stmt.setInt(5, fullness);
            stmt.setInt(6, currency);
            stmt.setString(7, items);
            stmt.setString(8, username);
            stmt.executeUpdate();
            System.out.println("Pet saved for user: " + username);
        } catch (SQLException e) {
            System.out.println("Error saving pet: " + e.getMessage());
        }
    }

    // Load pet data for a user
    public static ResultSet loadPet(String username) {
        String sql = """
                SELECT p.* FROM pets p
                JOIN users u ON p.user_id = u.id
                WHERE u.username = ?
                """;
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, username);
            return stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error loading pet: " + e.getMessage());
        }
        return null;
    }
}