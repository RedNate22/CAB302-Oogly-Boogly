package com.mathcat.mathcat.database;

import java.sql.*;

/**
 * Manages the SQLite database connection and initialises tables.
 */
public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:mathcat/mathcat.db";
    private static Connection connection;

    /**
     * Returns the active database connection, creating one if it doesn't exist.
     * @return the SQLite connection
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
                    pet_name TEXT,
                    appearance TEXT,
                    level INTEGER DEFAULT 1,
                    xp INTEGER DEFAULT 0,
                    happiness INTEGER DEFAULT 100,
                    energy INTEGER DEFAULT 100,
                    fullness INTEGER DEFAULT 100,
                    currency INTEGER DEFAULT 0,
                    items TEXT DEFAULT '',
                    FOREIGN KEY (user_id) REFERENCES users(id)
                );
                """;

        try (Statement stmt = getConnection().createStatement()) {
            stmt.execute(createUsersTable);
            stmt.execute(createPetsTable);
            System.out.println("Database initialised successfully!");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}