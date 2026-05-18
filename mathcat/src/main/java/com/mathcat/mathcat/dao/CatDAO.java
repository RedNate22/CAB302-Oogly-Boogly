package com.mathcat.mathcat.dao;

import com.mathcat.mathcat.database.DatabaseManager;
import com.mathcat.mathcat.models.Cat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Access Object for Cat database operations.
 * Handles only raw SQL queries — no business logic.
 */
public final class CatDAO {
    private static final Logger log = LoggerFactory.getLogger(CatDAO.class);

    /**
     * Saves a cat to the database. Inserts if new, updates if existing.
     * @param cat the cat to save
     */
    public static void save(Cat cat) {
        try {
            if (cat.getCatId() == 0) {
                String sql = """
                        INSERT INTO pets (user_id, cat_name, cat_sprite, happiness, fullness, energy,
                        level, xp, last_saved, daily_energy_gained, energy_cap_reset_date)
                        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                        """;
                try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setInt(1, cat.getUserId());
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

                    ResultSet keys = stmt.getGeneratedKeys();
                    if (keys.next()) {
                        cat.setCatId(keys.getInt(1));
                    }
                }
            } else {
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
        } catch (SQLException e) {
            log.error("Error saving cat", e);
        }
    }

    /**
     * Loads a cat from the database by user ID.
     * @param userId the database ID of the owning user
     * @return the Cat, or null if not found
     */
    public static Cat load(int userId) {
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
        } catch (SQLException e) {
            log.error("Error loading cat", e);
        }
        return null;
    }

    /**
     * Deletes a cat from the database by cat ID.
     * @param catId the cat's database ID
     */
    public static void delete(int catId) {
        String sql = "DELETE FROM pets WHERE id = ?";
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, catId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            log.error("Error deleting cat", e);
        }
    }

    /**
     * Deletes all pets from the database. For use in unit tests only.
     */
    public static void clearForTesting() {
        String sql = "DELETE FROM pets";
        try (Statement stmt = DatabaseManager.getConnection().createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            log.error("Error clearing cats", e);
        }
    }
}