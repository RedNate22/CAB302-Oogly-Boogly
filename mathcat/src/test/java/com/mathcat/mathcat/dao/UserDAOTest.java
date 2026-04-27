package com.mathcat.mathcat.dao;

import com.mathcat.mathcat.database.DatabaseManager;
import com.mathcat.mathcat.models.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    @BeforeAll
    static void setupDatabase() throws SQLException {
        DatabaseManager.useInMemoryDatabase();
        DatabaseManager.initialiseDatabase();
    }

    @BeforeEach
    void clearUsers() throws SQLException {
        try (Statement stmt = DatabaseManager.getConnection().createStatement()) {
            stmt.execute("DELETE FROM users");
        }
    }

    @Nested
    class FindAll {
        @Test
        void noUsersExist() throws SQLException {
            assertTrue(UserDAO.findAll().isEmpty());
        }

        @Test
        void oneUserExists() throws SQLException {
            UserDAO.insert(new User("Nate", "nate@gmail.com", "Slay!22"));
            assertFalse(UserDAO.findAll().isEmpty());
        }

        @Test
        void multipleUsersExist() throws SQLException {
            UserDAO.insert(new User("Nate", "nate@gmail.com", "Slay!22"));
            UserDAO.insert(new User("Alex", "alex@gmail.com", "Y0Mama!"));
            UserDAO.insert(new User("Zayan", "zayan@gmail.com", "Kitt3?%"));
            assertEquals(3, UserDAO.findAll().size());
        }
    }

    @Nested
    class FindByUsername {
        @Test
        void returnsCorrectUser() throws SQLException {
            UserDAO.insert(new User("Nate", "nate@gmail.com", "Slay!22"));
            User found = UserDAO.findByUsername("Nate");
            assertNotNull(found);
            assertEquals("Nate", found.getUsername());
        }

        @Test
        void returnsNullIfNotFound() throws SQLException {
            UserDAO.insert(new User("Nate", "nate@gmail.com", "Slay!22"));
            assertNull(UserDAO.findByUsername("Jake"));
        }

        @Test
        void isCaseSensitive() throws SQLException {
            UserDAO.insert(new User("Nate", "nate@gmail.com", "Slay!22"));
            assertNull(UserDAO.findByUsername("nate"));
        }

        @Test
        void returnsCorrectUserWithMultipleUsers() throws SQLException {
            UserDAO.insert(new User("Nate", "nate@gmail.com", "Slay!22"));
            UserDAO.insert(new User("Alex", "alex@gmail.com", "Y0Mama!"));
            UserDAO.insert(new User("Zayan", "zayan@gmail.com", "Kitt3?%"));
            assertEquals("Nate", UserDAO.findByUsername("Nate").getUsername());
            assertEquals("Alex", UserDAO.findByUsername("Alex").getUsername());
            assertEquals("Zayan", UserDAO.findByUsername("Zayan").getUsername());
        }
    }

    @Nested
    class FindByEmail {
        @Test
        void returnsCorrectUser() throws SQLException {
            UserDAO.insert(new User("Nate", "nate@gmail.com", "Slay!22"));
            User found = UserDAO.findByEmail("nate@gmail.com");
            assertNotNull(found);
            assertEquals("nate@gmail.com", found.getEmail());
        }

        @Test
        void returnsNullIfNotFound() throws SQLException {
            UserDAO.insert(new User("Nate", "nate@gmail.com", "Slay!22"));
            assertNull(UserDAO.findByEmail("jake@gmail.com"));
        }
    }

    @Nested
    class Password {
        @Test
        void storedCorrectly() throws SQLException {
            UserDAO.insert(new User("Nate", "nate@gmail.com", "Slay!22"));
            assertEquals("Slay!22", UserDAO.findByUsername("Nate").getPassword());
        }

        @Test
        void wrongPasswordDoesNotMatch() throws SQLException {
            UserDAO.insert(new User("Nate", "nate@gmail.com", "Slay!22"));
            assertNotEquals("WrongPassword1!", UserDAO.findByUsername("Nate").getPassword());
        }
    }

    @Nested
    class DeleteByUsername {
        @Test
        void removesUser() throws SQLException {
            UserDAO.insert(new User("Nate", "nate@gmail.com", "Slay!22"));
            UserDAO.deleteByUsername("Nate");
            assertNull(UserDAO.findByUsername("Nate"));
        }
    }

    @Nested
    class Id {
        @Test
        void assignedByDatabase() throws SQLException {
            UserDAO.insert(new User("Nate", "nate@gmail.com", "Slay!22"));
            assertTrue(UserDAO.findByUsername("Nate").getId() > 0);
        }
    }

    @Nested
    class SetCurrentUser {
        @Test
        void storesCorrectUser() throws SQLException {
            UserDAO.insert(new User("Alex", "alex@gmail.com", "Y0Mama!"));
            UserDAO.setCurrentUser(UserDAO.findByUsername("Alex"));
            assertEquals("Alex", UserDAO.currentUser.getUsername());
            assertEquals("alex@gmail.com", UserDAO.currentUser.getEmail());
            assertEquals("Y0Mama!", UserDAO.currentUser.getPassword());
        }

        @Test
        void storesCorrectUserWithMultipleUsers() throws SQLException {
            UserDAO.insert(new User("Nate", "nate@gmail.com", "Slay!22"));
            UserDAO.insert(new User("Alex", "alex@gmail.com", "Y0Mama!"));
            UserDAO.setCurrentUser(UserDAO.findByUsername("Alex"));
            assertEquals("Alex", UserDAO.currentUser.getUsername());
            assertNotEquals("Nate", UserDAO.currentUser.getUsername());
        }
    }
}
