package com.mathcat.mathcat.services;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServicesTest {

    @Nested
    class LoginFieldsEmpty{
        @Test
        void loginUsernameEmpty() {
            assertTrue(UserService.fieldsEmpty("", "MaisonRose26?"));
        }

        @Test
        void loginPasswordEmpty() {
            assertTrue(UserService.fieldsEmpty("JakePettigrew", ""));
        }

        @Test
        void loginBothFieldsEmpty() {
            assertTrue(UserService.fieldsEmpty("", ""));
        }

        @Test
        void loginUsernameIsSpacesOnlyFieldsEmpty() {
            assertTrue(UserService.fieldsEmpty("", "Rainbow39Wh!te"));
        }

        @Test
        void loginNoFieldsEmpty() {
            assertFalse(UserService.fieldsEmpty("Dance", "Guru"));
        }
    }

    @Nested
    class CreateAccountFieldsEmpty {
        @Test
        void createAccountUsernameFieldEmpty() {
            assertTrue(UserService.fieldsEmpty("", "email", "password"));
        }

        @Test
        void createAccountEmailFieldEmpty() {
            assertTrue(UserService.fieldsEmpty("username", "", "password"));
        }

        @Test
        void createAccountPasswordFieldEmpty() {
            assertTrue(UserService.fieldsEmpty("username", "email", ""));
        }

        @Test
        void createAccountUsernameAndEmailFieldEmpty() {
            assertTrue(UserService.fieldsEmpty("", "", "password"));
        }

        @Test
        void createAccountUsernameAndPasswordFieldEmpty() {
            assertTrue(UserService.fieldsEmpty("", "email", ""));
        }

        @Test
        void createAccountPasswordAndEmailFieldEmpty() {
            assertTrue(UserService.fieldsEmpty("username", "", ""));
        }

        @Test
        void createAccountAllFieldsEmpty() {
            assertTrue(UserService.fieldsEmpty("", "", ""));
        }

        @Test
        void createAccountNoFieldsEmpty() {
            assertFalse(UserService.fieldsEmpty("MaisonRose", "maison227@outlook.com", "H1Delancey!"));
        }
    }

    @Nested
    class ValidUsername {
        @Test
        void allSpacesUsername() {
            assertFalse(UserService.validUsername("           "));
        }

        @Test
        void shortUsername() {
            assertFalse(UserService.validUsername("uh"));
        }

        @Test
        void longUsername() {
            assertFalse(UserService.validUsername("IThinkThatThisUsernameIsGoingToBeToLong"));
        }

        @Test
        void spacesUsername() {
            assertFalse(UserService.validUsername("No Spaces allowed"));
        }

        @Test
        void specialUsername() {
            assertFalse(UserService.validUsername("NotA!!owed"));
        }

        @Test
        void threeCharacterUsername() {
            assertTrue(UserService.validUsername("ugh"));
        }

        @Test
        void twentyCharacterUsername() {
            assertTrue(UserService.validUsername("TwentyCharacters2020"));
        }

        @Test
        void twentyOneCharacterUsername() {
            assertFalse(UserService.validUsername("Twenty_One_Characters"));
        }

        @Test
        void validUsername1() {
            assertTrue(UserService.validUsername("tomas_ds06"));
        }

        @Test
        void validUsername2() {
            assertTrue(UserService.validUsername("Maisonxrose"));
        }
    }

    @Nested
    class ValidEmail {
        @Test
        void allSpacesEmail() {
            assertFalse(UserService.validEmail("      "));
        }

        @Test
        void noATEmail() {
            assertFalse(UserService.validEmail("agrossioutlookcom"));
        }

        @Test
        void invalidATEmail1() {
            assertFalse(UserService.validEmail("agrossioutlookcom@"));
        }

        @Test
        void anATOnlyEmail() {
            assertFalse(UserService.validEmail("@"));
        }

        @Test
        void noDomainEmail() {
            assertFalse(UserService.validEmail("agrossi@outlook"));
        }

        @Test
        void invalidATEmail2() {
            assertFalse(UserService.validEmail("@agrossioutlookcom"));
        }

        @Test
        void validEmail1() {
            assertTrue(UserService.validEmail("agrossi@outlook.com"));
        }

        @Test
        void validEmail2() {
            assertTrue(UserService.validEmail("jpettigrew38@gmail.com"));
        }
    }

    @Nested
    class ValidPassword {
        @Test
        void allSpacesPassword() {
            assertFalse(UserService.validPassword("           "));
        }

        @Test
        void lowercaseOnlyPassword() {
            assertFalse(UserService.validPassword("isthisalongenoughpassword"));
        }

        @Test
        void uppercaseOnlyPassword() {
            assertFalse(UserService.validPassword("ALEXANDERGROSSI"));
        }

        @Test
        void numberOnlyPassword() {
            assertFalse(UserService.validPassword("12345678910"));
        }

        @Test
        void specialCharactersPassword() {
            assertFalse(UserService.validPassword("!?!?!?!??!?!?!"));
        }

        @Test
        void shortPassword() {
            assertFalse(UserService.validPassword("H0ldSt!ll"));
        }

        @Test
        void workingPassword1() {
            assertTrue(UserService.validPassword("Delancey19!"));
        }

        @Test
        void workingPassword2() {
            assertTrue(UserService.validPassword("Dy1nOnThisH!ll"));
        }
    }
}
