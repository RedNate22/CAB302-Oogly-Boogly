package com.mathcat.mathcat.services;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServicesTest {

    @Nested
    class LoginFieldsEmpty{
        @Test
        void loginUsernameEmpty() {
            assertTrue(UserService.FieldsEmpty("", "MaisonRose26?"));
        }

        @Test
        void loginPasswordEmpty() {
            assertTrue(UserService.FieldsEmpty("JakePettigrew", ""));
        }

        @Test
        void loginBothFieldsEmpty() {
            assertTrue(UserService.FieldsEmpty("", ""));
        }

        @Test
        void loginUsernameIsSpacesOnlyFieldsEmpty() {
            assertTrue(UserService.FieldsEmpty("", "Rainbow39Wh!te"));
        }

        @Test
        void loginNoFieldsEmpty() {
            assertFalse(UserService.FieldsEmpty("Dance", "Guru"));
        }
    }

    @Nested
    class CreateAccountFieldsEmpty {
        @Test
        void createAccountUsernameFieldEmpty() {
            assertTrue(UserService.FieldsEmpty("", "email", "password"));
        }

        @Test
        void createAccountEmailFieldEmpty() {
            assertTrue(UserService.FieldsEmpty("username", "", "password"));
        }

        @Test
        void createAccountPasswordFieldEmpty() {
            assertTrue(UserService.FieldsEmpty("username", "email", ""));
        }

        @Test
        void createAccountUsernameAndEmailFieldEmpty() {
            assertTrue(UserService.FieldsEmpty("", "", "password"));
        }

        @Test
        void createAccountUsernameAndPasswordFieldEmpty() {
            assertTrue(UserService.FieldsEmpty("", "email", ""));
        }

        @Test
        void createAccountPasswordAndEmailFieldEmpty() {
            assertTrue(UserService.FieldsEmpty("username", "", ""));
        }

        @Test
        void createAccountAllFieldsEmpty() {
            assertTrue(UserService.FieldsEmpty("", "", ""));
        }

        @Test
        void createAccountNoFieldsEmpty() {
            assertFalse(UserService.FieldsEmpty("MaisonRose", "maison227@outlook.com", "H1Delancey!"));
        }
    }

    @Nested
    class ValidUsername {
        @Test
        void allSpacesUsername() {
            assertFalse(UserService.ValidUsername("           "));
        }

        @Test
        void shortUsername() {
            assertFalse(UserService.ValidUsername("uh"));
        }

        @Test
        void longUsername() {
            assertFalse(UserService.ValidUsername("IThinkThatThisUsernameIsGoingToBeToLong"));
        }

        @Test
        void spacesUsername() {
            assertFalse(UserService.ValidUsername("No Spaces allowed"));
        }

        @Test
        void specialUsername() {
            assertFalse(UserService.ValidUsername("NotA!!owed"));
        }

        @Test
        void threeCharacterUsername() {
            assertTrue(UserService.ValidUsername("ugh"));
        }

        @Test
        void twentyCharacterUsername() {
            assertTrue(UserService.ValidUsername("TwentyCharacters2020"));
        }

        @Test
        void twentyOneCharacterUsername() {
            assertFalse(UserService.ValidUsername("Twenty_One_Characters"));
        }

        @Test
        void validUsername1() {
            assertTrue(UserService.ValidUsername("tomas_ds06"));
        }

        @Test
        void validUsername2() {
            assertTrue(UserService.ValidUsername("Maisonxrose"));
        }
    }

    @Nested
    class ValidEmail {
        @Test
        void allSpacesEmail() {
            assertFalse(UserService.ValidEmail("      "));
        }

        @Test
        void noATEmail() {
            assertFalse(UserService.ValidEmail("agrossioutlookcom"));
        }

        @Test
        void invalidATEmail1() {
            assertFalse(UserService.ValidEmail("agrossioutlookcom@"));
        }

        @Test
        void anATOnlyEmail() {
            assertFalse(UserService.ValidEmail("@"));
        }

        @Test
        void noDomainEmail() {
            assertFalse(UserService.ValidEmail("agrossi@outlook"));
        }

        @Test
        void invalidATEmail2() {
            assertFalse(UserService.ValidEmail("@agrossioutlookcom"));
        }

        @Test
        void validEmail1() {
            assertTrue(UserService.ValidEmail("agrossi@outlook.com"));
        }

        @Test
        void validEmail2() {
            assertTrue(UserService.ValidEmail("jpettigrew38@gmail.com"));
        }
    }

    @Nested
    class ValidPassword {
        @Test
        void allSpacesPassword() {
            assertFalse(UserService.ValidPassword("           "));
        }

        @Test
        void lowercaseOnlyPassword() {
            assertFalse(UserService.ValidPassword("isthisalongenoughpassword"));
        }

        @Test
        void uppercaseOnlyPassword() {
            assertFalse(UserService.ValidPassword("ALEXANDERGROSSI"));
        }

        @Test
        void numberOnlyPassword() {
            assertFalse(UserService.ValidPassword("12345678910"));
        }

        @Test
        void specialCharactersPassword() {
            assertFalse(UserService.ValidPassword("!?!?!?!??!?!?!"));
        }

        @Test
        void shortPassword() {
            assertFalse(UserService.ValidPassword("H0ldSt!ll"));
        }

        @Test
        void workingPassword1() {
            assertTrue(UserService.ValidPassword("Delancey19!"));
        }

        @Test
        void workingPassword2() {
            assertTrue(UserService.ValidPassword("Dy1nOnThisH!ll"));
        }
    }
}
