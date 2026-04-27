package com.mathcat.mathcat.dao;

import com.mathcat.mathcat.models.User;
import org.junit.jupiter.api.Test;

import static com.mathcat.mathcat.dao.UserDAO.currentUser;
import static com.mathcat.mathcat.dao.UserDAO.users;
import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    @Test
    // RUN INDIVIDUALLY
    void noUsersExist() {
        UserDAO userDAO = new UserDAO();
        assertTrue(userDAO.NoUsersExist());
    }

    @Test
    void oneUserExists() {
        UserDAO userDAO = new UserDAO();
        userDAO.NewUser("Tomas", "tomas@gmail.com", "Pi1otInterview!");
        assertFalse(userDAO.NoUsersExist());
    }

    @Test
    void multipleUsersExist() {
        UserDAO userDAO = new UserDAO();
        userDAO.NewUser("Maison", "maisonrose@gmail.com", "Delancey26!");
        userDAO.NewUser("Alex", "alex@gmail.com", "Co1dPlay!?");
        userDAO.NewUser("Tomas", "tomas@gmail.com", "Pi1otInterview!");
        assertFalse(userDAO.NoUsersExist());
    }

    @Test
    void userNotNullOnMatch() {
        UserDAO userDAO = new UserDAO();
        userDAO.NewUser("Tomas", "tomas@gmail.com", "Pi1otInterview!");
        User matchedUser = null;
        User MatchedUser = userDAO.UserMatch(matchedUser, "Tomas");

        assertFalse(userDAO.NoUserMatchFound(MatchedUser));
    }

    @Test
    void userMatchWithOneUserExisting() {
        UserDAO userDAO = new UserDAO();
        userDAO.NewUser("Tomas", "tomas@gmail.com", "Pi1otInterview!");
        User matchedUser = null;
        User MatchedUser = userDAO.UserMatch(matchedUser, "Tomas");

        assertEquals("Tomas", MatchedUser.getUsername());
    }

    @Test
    void userMatchWithMultipleUsersExisting() {
        UserDAO userDAO = new UserDAO();
        userDAO.NewUser("Maison", "maisonrose@gmail.com", "Delancey26!");
        userDAO.NewUser("Alex", "alex@gmail.com", "Co1dPlay!?");
        userDAO.NewUser("Tomas", "tomas@gmail.com", "Pi1otInterview!");

        User matchedUser = null;
        User MatchedUser = userDAO.UserMatch(matchedUser, "Alex");

        assertEquals("Alex", MatchedUser.getUsername());
    }

    @Test
    void userMatchesWithMultipleUsersExisting() {
        UserDAO userDAO = new UserDAO();
        userDAO.NewUser("Maison", "maisonrose@gmail.com", "Delancey26!");
        userDAO.NewUser("Alex", "alex@gmail.com", "Co1dPlay!?");
        userDAO.NewUser("Tomas", "tomas@gmail.com", "Pi1otInterview!");

        User matchedUser = null;

        User MatchedUser1 = userDAO.UserMatch(matchedUser, "Alex");
        assertEquals("Alex", MatchedUser1.getUsername());

        User MatchedUser2 = userDAO.UserMatch(matchedUser, "Maison");
        assertEquals("Maison", MatchedUser2.getUsername());

        User MatchedUser3 = userDAO.UserMatch(matchedUser, "Tomas");
        assertEquals("Tomas", MatchedUser3.getUsername());
    }

    @Test
    void noUserMatchFoundOneUserExists() {
        UserDAO userDAO = new UserDAO();
        userDAO.NewUser("Tomas", "tomas@gmail.com", "Pi1otInterview!");
        User matchedUser = null;
        User MatchedUser = userDAO.UserMatch(matchedUser, "Jake");
        assertTrue(userDAO.NoUserMatchFound(MatchedUser));
    }

    @Test
    void noUserMatchFoundMultipleUsersExists() {
        UserDAO userDAO = new UserDAO();
        userDAO.NewUser("Maison", "maisonrose@gmail.com", "Delancey26!");
        userDAO.NewUser("Alex", "alex@gmail.com", "Co1dPlay!?");
        userDAO.NewUser("Tomas", "tomas@gmail.com", "Pi1otInterview!");
        User matchedUser = null;
        User MatchedUser = userDAO.UserMatch(matchedUser, "Jake");
        assertTrue(userDAO.NoUserMatchFound(MatchedUser));
    }

    @Test
    void noUserMatchFoundCaseSensitive() {
        UserDAO userDAO = new UserDAO();
        userDAO.NewUser("Tomas", "tomas@gmail.com", "Pi1otInterview!");
        User matchedUser = null;
        User MatchedUser = userDAO.UserMatch(matchedUser, "tomas");
        assertTrue(userDAO.NoUserMatchFound(MatchedUser));
    }

    @Test
    void userPasswordMatch() {
        UserDAO userDAO = new UserDAO();
        userDAO.NewUser("Tomas", "tomas@gmail.com", "Pi1otInterview!");
        User matchedUser = null;
        User MatchedUser = userDAO.UserMatch(matchedUser, "Tomas");

        assertTrue(userDAO.UserPasswordMatch(MatchedUser, "Pi1otInterview!"));
    }

    @Test
    void userPasswordDoesNotMatch() {
        UserDAO userDAO = new UserDAO();
        userDAO.NewUser("Tomas", "tomas@gmail.com", "Pi1otInterview!");
        User matchedUser = null;
        User MatchedUser = userDAO.UserMatch(matchedUser, "Tomas");

        assertFalse(userDAO.UserPasswordMatch(MatchedUser, "Pi2otInterview!"));
    }

    @Test
    void setCurrentUserWithOneUserExisting() {
        UserDAO userDAO = new UserDAO();
        userDAO.NewUser("Alex", "alex@gmail.com", "Co1dPlay!?");

        User matchedUser = null;
        User MatchedUser = userDAO.UserMatch(matchedUser, "Alex");
        userDAO.SetCurrentUser(MatchedUser);
        assertEquals("Alex", MatchedUser.getUsername());
        assertEquals("alex@gmail.com", MatchedUser.getEmail());
        assertEquals("Co1dPlay!?", MatchedUser.getPassword());
    }

    @Test
    void setCurrentUserWithMultipleUsersExisting() {
        UserDAO userDAO = new UserDAO();
        userDAO.NewUser("Maison", "maisonrose@gmail.com", "Delancey26!");
        userDAO.NewUser("Alex", "alex@gmail.com", "Co1dPlay!?");
        userDAO.NewUser("Tomas", "tomas@gmail.com", "Pi1otInterview!");

        User matchedUser = null;
        User MatchedUser = userDAO.UserMatch(matchedUser, "Alex");
        userDAO.SetCurrentUser(MatchedUser);
        assertEquals("Alex", MatchedUser.getUsername());
        assertEquals("alex@gmail.com", MatchedUser.getEmail());
        assertEquals("Co1dPlay!?", MatchedUser.getPassword());
    }

    @Test
    void userDoesExist() {
        UserDAO userDAO = new UserDAO();
        userDAO.NewUser("Maison", "maisonrose@gmail.com", "Delancey26!");
        userDAO.NewUser("Alex", "alex@gmail.com", "Co1dPlay!?");
        userDAO.NewUser("Tomas", "tomas@gmail.com", "Pi1otInterview!");

        assertTrue(userDAO.UserExists("Alex", "alex@gmail.com"));
    }

    @Test
    void userDoesNotExist() {
        UserDAO userDAO = new UserDAO();
        userDAO.NewUser("Maison", "maisonrose@gmail.com", "Delancey26!");
        userDAO.NewUser("Alex", "alex@gmail.com", "Co1dPlay!?");
        userDAO.NewUser("Tomas", "tomas@gmail.com", "Pi1otInterview!");

        assertFalse(userDAO.UserExists("Dingo", "dingo@gmail.com"));
    }

    @Test
    void userDoesExistOnlyUsernameSame() {
        UserDAO userDAO = new UserDAO();
        userDAO.NewUser("Maison", "maisonrose@gmail.com", "Delancey26!");
        userDAO.NewUser("Alex", "alex@gmail.com", "Co1dPlay!?");
        userDAO.NewUser("Tomas", "tomas@gmail.com", "Pi1otInterview!");

        assertTrue(userDAO.UserExists("Tomas", "tomasds@gmail.com"));
    }

    @Test
    void userDoesExistCaseSensitiveUsername() {
        UserDAO userDAO = new UserDAO();
        userDAO.NewUser("Maison", "maisonrose@gmail.com", "Delancey26!");
        userDAO.NewUser("Alex", "alex@gmail.com", "Co1dPlay!?");
        userDAO.NewUser("Tomas", "tomas@gmail.com", "Pi1otInterview!");

        assertTrue(userDAO.UserExists("alex", "alex@gmail.com"));
    }

    @Test
    void userDoesExistOnlyEmailSame() {
        UserDAO userDAO = new UserDAO();
        userDAO.NewUser("Maison", "maisonrose@gmail.com", "Delancey26!");
        userDAO.NewUser("Alex", "alex@gmail.com", "Co1dPlay!?");
        userDAO.NewUser("Tomas", "tomas@gmail.com", "Pi1otInterview!");

        assertTrue(userDAO.UserExists("Maison", "maisonrose@gmail.com"));
    }

    @Test
    // RUN INDIVIDUALLY
    void newUsers() {
        UserDAO userDao = new UserDAO();
        userDao.NewUser("Maison", "maisonrose@gmail.com", "Delancey26!");
        userDao.NewUser("Alex", "alex@gmail.com", "Co1dPlay!?");
        userDao.NewUser("Tomas", "tomas@gmail.com", "Pi1otInterview!");

        assertEquals(3, users.size());

        User matchedUser = null;

        User MatchedUser1 = userDao.UserMatch(matchedUser, "Alex");
        assertEquals(2, MatchedUser1.getId());
        assertEquals("Alex", MatchedUser1.getUsername());
        assertEquals("alex@gmail.com", MatchedUser1.getEmail());
        assertEquals("Co1dPlay!?", MatchedUser1.getPassword());

        User MatchedUser2 = userDao.UserMatch(matchedUser, "Maison");
        assertEquals(1, MatchedUser2.getId());
        assertEquals("Maison", MatchedUser2.getUsername());
        assertEquals("maisonrose@gmail.com", MatchedUser2.getEmail());
        assertEquals("Delancey26!", MatchedUser2.getPassword());

        User MatchedUser3 = userDao.UserMatch(matchedUser, "Tomas");
        assertEquals(3, MatchedUser3.getId());
        assertEquals("Tomas", MatchedUser3.getUsername());
        assertEquals("tomas@gmail.com", MatchedUser3.getEmail());
        assertEquals("Pi1otInterview!", MatchedUser3.getPassword());
    }

    @Test
    // RUN INDIVIDUALLY
    void newUsersCurrentUser() {
        UserDAO userDao = new UserDAO();
        userDao.NewUser("Maison", "maisonrose@gmail.com", "Delancey26!");
        userDao.NewUser("Alex", "alex@gmail.com", "Co1dPlay!?");

        assertEquals(2, currentUser.getId());
        assertEquals("Alex", currentUser.getUsername());
        assertEquals("alex@gmail.com", currentUser.getEmail());
        assertEquals("Co1dPlay!?", currentUser.getPassword());
    }

    @Test
    void newUsersNotCurrentUser() {
        UserDAO userDao = new UserDAO();
        userDao.NewUser("Alex", "alex@gmail.com", "Co1dPlay!?");
        userDao.NewUser("Maison", "maisonrose@gmail.com", "Delancey26!");

        assertNotEquals(1, currentUser.getId());
        assertNotEquals("Alex", currentUser.getUsername());
        assertNotEquals("alex@gmail.com", currentUser.getEmail());
        assertNotEquals("Co1dPlay!?", currentUser.getPassword());
    }
}