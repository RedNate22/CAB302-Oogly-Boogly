package com.mathcat.mathcat.database;

import com.mathcat.mathcat.models.Cat;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Handles SQLite persistence for Cat (pet) data.
 */
public class PetDAO {

    /**
     * Saves a new cat to the database linked to the given user ID.
     * @param cat the cat to save
     * @param userId the database ID of the owning user
     * @throws SQLException if the insert fails
     */
    public static void insert(Cat cat, int userId) throws SQLException {
        String sql = """
                INSERT INTO pets (user_id, cat_name, cat_sprite, happiness, fullness, energy,
                level, xp, last_saved, daily_energy_gained, energy_cap_reset_date)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, userId);
            stmt.setString(2, cat.getCatName());
            stmt.setString(3, cat.getCatSprite());
            stmt.setDouble(4, cat.getHappiness());
            stmt.setDouble(5, cat.getFullness());
            stmt.setDouble(6, cat.getEnergy());
            stmt.setInt(7, cat.getLevel());
            stmt.setDouble(8, cat.getXp());
            stmt.setString(9, cat.getLastSaved() != null ? cat.getLastSaved().toString() : null);
            stmt.setDouble(10, cat.getDailyEnergyGained());
            stmt.setString(11, cat.getEnergyCapResetDate() != null ? cat.getEnergyCapResetDate().toString() : null);
            stmt.executeUpdate();

            // Set the generated ID back on the cat
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                cat.setCatId(keys.getInt(1));
            }
        }
    }

    /**
     * Updates an existing cat's stats in the database.
     * @param cat the cat with updated stats
     * @throws SQLException if the update fails
     */
    public static void update(Cat cat) throws SQLException {
        String sql = """
                UPDATE pets SET cat_name = ?, cat_sprite = ?, happiness = ?, fullness = ?,
                energy = ?, level = ?, xp = ?, last_saved = ?, daily_energy_gained = ?,
                energy_cap_reset_date = ? WHERE id = ?
                """;
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, cat.getCatName());
            stmt.setString(2, cat.getCatSprite());
            stmt.setDouble(3, cat.getHappiness());
            stmt.setDouble(4, cat.getFullness());
            stmt.setDouble(5, cat.getEnergy());
            stmt.setInt(6, cat.getLevel());
            stmt.setDouble(7, cat.getXp());
            stmt.setString(8, cat.getLastSaved() != null ? cat.getLastSaved().toString() : null);
            stmt.setDouble(9, cat.getDailyEnergyGained());
            stmt.setString(10, cat.getEnergyCapResetDate() != null ? cat.getEnergyCapResetDate().toString() : null);
            stmt.setInt(11, cat.getCatId());
            stmt.executeUpdate();
        }
    }

    /**
     * Loads a cat from the database by user ID.
     * @param userId the database ID of the owning user
     * @return the Cat, or null if not found
     * @throws SQLException if the query fails
     */
    public static Cat loadByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM pets WHERE user_id = ?";
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Cat cat = new Cat(rs.getString("cat_name"));
                cat.setCatId(rs.getInt("id"));
                cat.setUserId(userId);
                cat.setCatSprite(rs.getString("cat_sprite"));
                cat.setHappiness(rs.getDouble("happiness"));
                cat.setFullness(rs.getDouble("fullness"));
                cat.setEnergy(rs.getDouble("energy"));
                cat.setLevel(rs.getInt("level"));
                cat.setXp(rs.getDouble("xp"));
                String lastSaved = rs.getString("last_saved");
                if (lastSaved != null) cat.setLastSaved(LocalDateTime.parse(lastSaved));
                cat.setDailyEnergyGained(rs.getDouble("daily_energy_gained"));
                String resetDate = rs.getString("energy_cap_reset_date");
                if (resetDate != null) cat.setEnergyCapResetDate(LocalDate.parse(resetDate));
                return cat;
            }
        }
        return null;
    }

    /**
     * Deletes a cat from the database by cat ID.
     * @param catId the cat's database ID
     * @throws SQLException if the delete fails
     */
    public static void delete(int catId) throws SQLException {
        String sql = "DELETE FROM pets WHERE id = ?";
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, catId);
            stmt.executeUpdate();
        }
    }
}