package com.mathcat.mathcat.dao;

import com.mathcat.mathcat.database.DatabaseManager;
import com.mathcat.mathcat.models.Cat;
import com.mathcat.mathcat.models.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class CatDAOTest {

    @BeforeAll
    static void setupDatabase() throws SQLException {
        DatabaseManager.useInMemoryDatabase();
        DatabaseManager.initialiseDatabase();
    }

    @BeforeEach
    void clearTables() throws SQLException {
        try (Statement stmt = DatabaseManager.getConnection().createStatement()) {
            stmt.execute("DELETE FROM pets");
            stmt.execute("DELETE FROM users");
        }
    }

    // Helper to insert a user and return their ID
    private int insertUser(String username) throws SQLException {
        UserDAO.insert(new User(username, username + "@gmail.com", "Password1!"));
        return UserDAO.findByUsername(username).getId();
    }

    @Nested
    class Save {
        @Test
        void insertsNewCat() throws SQLException {
            int userId = insertUser("Nate");
            Cat cat = new Cat("Whiskers");
            cat.setUserId(userId);
            CatDAO.save(cat);
            assertNotNull(CatDAO.load(userId));
        }

        @Test
        void assignsDatabaseId() throws SQLException {
            int userId = insertUser("Nate");
            Cat cat = new Cat("Whiskers");
            cat.setUserId(userId);
            CatDAO.save(cat);
            assertTrue(cat.getCatId() > 0);
        }

        @Test
        void updatesCatIfAlreadyExists() throws SQLException {
            int userId = insertUser("Nate");
            Cat cat = new Cat("Whiskers");
            cat.setUserId(userId);
            CatDAO.save(cat);

            cat.setHappiness(50.0);
            CatDAO.save(cat);

            assertEquals(50.0, CatDAO.load(userId).getHappiness());
        }

        @Test
        void updatesCatName() throws SQLException {
            int userId = insertUser("Nate");
            Cat cat = new Cat("Whiskers");
            cat.setUserId(userId);
            CatDAO.save(cat);

            cat.setCatName("Mittens");
            CatDAO.save(cat);

            assertEquals("Mittens", CatDAO.load(userId).getCatName());
        }

        @Test
        void twoCatsForDifferentUsersDoNotCollide() throws SQLException {
            int userId1 = insertUser("Nate");
            int userId2 = insertUser("Alex");

            Cat cat1 = new Cat("Whiskers");
            cat1.setUserId(userId1);
            CatDAO.save(cat1);

            Cat cat2 = new Cat("Mittens");
            cat2.setUserId(userId2);
            CatDAO.save(cat2);

            assertEquals("Whiskers", CatDAO.load(userId1).getCatName());
            assertEquals("Mittens", CatDAO.load(userId2).getCatName());
        }

        @Test
        void savesDefaultStats() throws SQLException {
            int userId = insertUser("Nate");
            Cat cat = new Cat("Whiskers");
            cat.setUserId(userId);
            CatDAO.save(cat);

            Cat loaded = CatDAO.load(userId);
            assertEquals(100.0, loaded.getHappiness());
            assertEquals(100.0, loaded.getFullness());
            assertEquals(100.0, loaded.getEnergy());
            assertEquals(1, loaded.getLevel());
            assertEquals(0.0, loaded.getXp());
        }
    }

    @Nested
    class Load {
        @Test
        void returnsNullIfNoCatExists() throws SQLException {
            int userId = insertUser("Nate");
            assertNull(CatDAO.load(userId));
        }

        @Test
        void returnsCorrectCatName() throws SQLException {
            int userId = insertUser("Nate");
            Cat cat = new Cat("Whiskers");
            cat.setUserId(userId);
            CatDAO.save(cat);
            assertEquals("Whiskers", CatDAO.load(userId).getCatName());
        }

        @Test
        void returnsCorrectUserId() throws SQLException {
            int userId = insertUser("Nate");
            Cat cat = new Cat("Whiskers");
            cat.setUserId(userId);
            CatDAO.save(cat);
            assertEquals(userId, CatDAO.load(userId).getUserId());
        }

        @Test
        void returnsCorrectCatId() throws SQLException {
            int userId = insertUser("Nate");
            Cat cat = new Cat("Whiskers");
            cat.setUserId(userId);
            CatDAO.save(cat);
            assertEquals(cat.getCatId(), CatDAO.load(userId).getCatId());
        }

        @Test
        void loadsUpdatedStats() throws SQLException {
            int userId = insertUser("Nate");
            Cat cat = new Cat("Whiskers");
            cat.setUserId(userId);
            CatDAO.save(cat);

            cat.setHappiness(75.0);
            cat.setFullness(60.0);
            cat.setEnergy(80.0);
            CatDAO.save(cat);

            Cat loaded = CatDAO.load(userId);
            assertEquals(75.0, loaded.getHappiness());
            assertEquals(60.0, loaded.getFullness());
            assertEquals(80.0, loaded.getEnergy());
        }
    }

    @Nested
    class Delete {
        @Test
        void removesCat() throws SQLException {
            int userId = insertUser("Nate");
            Cat cat = new Cat("Whiskers");
            cat.setUserId(userId);
            CatDAO.save(cat);
            CatDAO.delete(cat.getCatId());
            assertNull(CatDAO.load(userId));
        }

        @Test
        void doesNothingIfCatDoesNotExist() throws SQLException {
            assertDoesNotThrow(() -> CatDAO.delete(999));
        }

        @Test
        void doesNotAffectSiblingCat() throws SQLException {
            int userId1 = insertUser("Nate");
            int userId2 = insertUser("Alex");

            Cat cat1 = new Cat("Whiskers");
            cat1.setUserId(userId1);
            CatDAO.save(cat1);

            Cat cat2 = new Cat("Mittens");
            cat2.setUserId(userId2);
            CatDAO.save(cat2);

            CatDAO.delete(cat1.getCatId());

            assertNotNull(CatDAO.load(userId2));
        }
    }

    @Nested
    class ClearForTesting {
        @Test
        void removesAllCats() throws SQLException {
            int userId1 = insertUser("Nate");
            int userId2 = insertUser("Alex");

            Cat cat1 = new Cat("Whiskers");
            cat1.setUserId(userId1);
            CatDAO.save(cat1);

            Cat cat2 = new Cat("Mittens");
            cat2.setUserId(userId2);
            CatDAO.save(cat2);

            CatDAO.clearForTesting();

            assertNull(CatDAO.load(userId1));
            assertNull(CatDAO.load(userId2));
        }
    }
}