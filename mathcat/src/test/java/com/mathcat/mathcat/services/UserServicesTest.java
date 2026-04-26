package com.mathcat.mathcat.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServicesTest {

    @Test
    void loginUsernameEmpty() {
        assertTrue(UserServices.FieldsEmpty("", "MaisonRose26?"));
    }

    @Test
    void loginPasswordEmpty() {
        assertTrue(UserServices.FieldsEmpty("JakePettigrew", ""));
    }

    @Test
    void loginBothFieldsEmpty() {
        assertTrue(UserServices.FieldsEmpty("", ""));
    }

    @Test
    void loginUsernameIsSpacesOnlyFieldsEmpty() {
        assertTrue(UserServices.FieldsEmpty("", "Rainbow39Wh!te"));
    }

    @Test
    void loginNoFieldsEmpty() {
        assertFalse(UserServices.FieldsEmpty("Dance", "Guru"));
    }

    @Test
    void createAccountUsernameFieldEmpty() {
        assertTrue(UserServices.FieldsEmpty("", "email", "password"));
    }

    @Test
    void createAccountEmailFieldEmpty() {
        assertTrue(UserServices.FieldsEmpty("username", "", "password"));
    }

    @Test
    void createAccountPasswordFieldEmpty() {
        assertTrue(UserServices.FieldsEmpty("username", "email", ""));
    }

    @Test
    void createAccountUsernameAndEmailFieldEmpty() {
        assertTrue(UserServices.FieldsEmpty("", "", "password"));
    }

    @Test
    void createAccountUsernameAndPasswordFieldEmpty() {
        assertTrue(UserServices.FieldsEmpty("", "email", ""));
    }

    @Test
    void createAccountPasswordAndEmailFieldEmpty() {
        assertTrue(UserServices.FieldsEmpty("username", "", ""));
    }

    @Test
    void createAccountAllFieldsEmpty() {
        assertTrue(UserServices.FieldsEmpty("", "", ""));
    }

    @Test
    void createAccountNoFieldsEmpty() {
        assertFalse(UserServices.FieldsEmpty("MaisonRose", "maison227@outlook.com", "H1Delancey!"));
    }

    @Test
    void allSpacesUsername() {
        assertFalse(UserServices.ValidUsername("           "));
    }

    @Test
    void shortUsername() {
        assertFalse(UserServices.ValidUsername("uh"));
    }

    @Test
    void longUsername() {
        assertFalse(UserServices.ValidUsername("IThinkThatThisUsernameIsGoingToBeToLong"));
    }

    @Test
    void spacesUsername() {
        assertFalse(UserServices.ValidUsername("No Spaces allowed"));
    }

    @Test
    void specialUsername() {
        assertFalse(UserServices.ValidUsername("NotA!!owed"));
    }

    @Test
    void threeCharacterUsername() {
        assertTrue(UserServices.ValidUsername("ugh"));
    }

    @Test
    void twentyCharacterUsername() {
        assertTrue(UserServices.ValidUsername("TwentyCharacters2020"));
    }

    @Test
    void twentyOneCharacterUsername() {
        assertFalse(UserServices.ValidUsername("Twenty_One_Characters"));
    }

    @Test
    void validUsername1() {
        assertTrue(UserServices.ValidUsername("tomas_ds06"));
    }

    @Test
    void validUsername2() {
        assertTrue(UserServices.ValidUsername("Maisonxrose"));
    }

    @Test
    void allSpacesEmail() {
        assertFalse(UserServices.ValidEmail("      "));
    }

    @Test
    void noATEmail() {
        assertFalse(UserServices.ValidEmail("agrossioutlookcom"));
    }

    @Test
    void invalidATEmail1() {
        assertFalse(UserServices.ValidEmail("agrossioutlookcom@"));
    }

    @Test
    void anATOnlyEmail() {
        assertFalse(UserServices.ValidEmail("@"));
    }

    @Test
    void noDomainEmail() {
        assertFalse(UserServices.ValidEmail("agrossi@outlook"));
    }

    @Test
    void invalidATEmail2() {
        assertFalse(UserServices.ValidEmail("@agrossioutlookcom"));
    }

    @Test
    void validEmail1() {
        assertTrue(UserServices.ValidEmail("agrossi@outlook.com"));
    }

    @Test
    void validEmail2() {
        assertTrue(UserServices.ValidEmail("jpettigrew38@gmail.com"));
    }

    @Test
    void allSpacesPassword() {
        assertFalse(UserServices.ValidPassword("           "));
    }

    @Test
    void lowercaseOnlyPassword() {
        assertFalse(UserServices.ValidPassword("isthisalongenoughpassword"));
    }

    @Test
    void uppercaseOnlyPassword() {
        assertFalse(UserServices.ValidPassword("ALEXANDERGROSSI"));
    }

    @Test
    void numberOnlyPassword() {
        assertFalse(UserServices.ValidPassword("12345678910"));
    }

    @Test
    void specialCharactersPassword() {
        assertFalse(UserServices.ValidPassword("!?!?!?!??!?!?!"));
    }

    @Test
    void shortPassword() {
        assertFalse(UserServices.ValidPassword("H0ldSt!ll"));
    }

    @Test
    void workingPassword1() {
        assertTrue(UserServices.ValidPassword("Delancey19!"));
    }

    @Test
    void workingPassword2() {
        assertTrue(UserServices.ValidPassword("Dy1nOnThisH!ll"));
    }
}
