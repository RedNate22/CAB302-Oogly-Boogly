package com.mathcat.mathcat.database;

import java.sql.*;
import com.mathcat.mathcat.util.DebugLogger;

/**
 * Manages the SQLite database connection and initialises all tables.
 */
public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:mathcat.db";
    private static Connection connection;

    /**
     * Replaces the active connection with an in-memory SQLite database.
     * For use in tests only — data is lost when the connection closes.
     * @throws SQLException if the in-memory connection cannot be created
     */
    public static void useInMemoryDatabase() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
    }

    /**
     * Returns the active database connection, creating one if needed.
     * @return the SQLite connection
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
        }
        return connection;
    }

    /**
     * Creates all required tables if they don't already exist.
     * Should be called once on application startup.
     */
    public static void initialiseDatabase() {
        String createUsersTable = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT UNIQUE NOT NULL,
                    email TEXT UNIQUE NOT NULL,
                    password TEXT NOT NULL
                );
                """;

        String createPetsTable = """
                CREATE TABLE IF NOT EXISTS pets (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER NOT NULL,
                    cat_name TEXT,
                    cat_sprite TEXT,
                    happiness REAL DEFAULT 100.0,
                    fullness REAL DEFAULT 100.0,
                    energy REAL DEFAULT 100.0,
                    level INTEGER DEFAULT 1,
                    xp REAL DEFAULT 0.0,
                    last_saved TEXT,
                    daily_energy_gained REAL DEFAULT 0.0,
                    energy_cap_reset_date TEXT,
                    FOREIGN KEY (user_id) REFERENCES users(id)
                );
                """;

        String createItemsTable = """
                CREATE TABLE IF NOT EXISTS items (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    pet_id INTEGER NOT NULL,
                    item_id TEXT NOT NULL,
                    quantity INTEGER DEFAULT 1,
                    FOREIGN KEY (pet_id) REFERENCES pets(id)
                );
                """;

        try (Statement stmt = getConnection().createStatement()) {
            stmt.execute(createUsersTable);
            stmt.execute(createPetsTable);
            stmt.execute(createItemsTable);
            DebugLogger.log("Database", "Initialised successfully");
        } catch (SQLException e) {
            DebugLogger.log("Database", "Error: " + e.getMessage());
        }
    }
}