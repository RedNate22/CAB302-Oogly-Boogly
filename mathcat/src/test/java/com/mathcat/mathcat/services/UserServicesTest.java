package com.mathcat.mathcat.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServicesTest {

    @Test
    void loginUsernameEmpty() {
        UserServices userServices = new UserServices();
        assertTrue(userServices.FieldsEmpty("", "MaisonRose26?"));
    }

    @Test
    void loginPasswordEmpty() {
        UserServices userServices = new UserServices();
        assertTrue(userServices.FieldsEmpty("JakePettigrew", ""));
    }

    @Test
    void loginBothFieldsEmpty() {
        UserServices userServices = new UserServices();
        assertTrue(userServices.FieldsEmpty("", ""));
    }

    @Test
    void loginUsernameIsSpacesOnlyFieldsEmpty() {
        UserServices userServices = new UserServices();
        assertTrue(userServices.FieldsEmpty("", "Rainbow39Wh!te"));
    }

    @Test
    void loginNoFieldsEmpty() {
        UserServices userServices = new UserServices();
        assertFalse(userServices.FieldsEmpty("Dance", "Guru"));
    }

    @Test
    void createAccountUsernameFieldEmpty() {
        UserServices userServices = new UserServices();
        assertTrue(userServices.FieldsEmpty("","email","password"));
    }

    @Test
    void createAccountEmailFieldEmpty() {
        UserServices userServices = new UserServices();
        assertTrue(userServices.FieldsEmpty("username","","password"));
    }

    @Test
    void createAccountPasswordFieldEmpty() {
        UserServices userServices = new UserServices();
        assertTrue(userServices.FieldsEmpty("username","email",""));
    }

    @Test
    void createAccountUsernameAndEmailFieldEmpty() {
        UserServices userServices = new UserServices();
        assertTrue(userServices.FieldsEmpty("","","password"));
    }

    @Test
    void createAccountUsernameAndPasswordFieldEmpty() {
        UserServices userServices = new UserServices();
        assertTrue(userServices.FieldsEmpty("","email",""));
    }

    @Test
    void createAccountPasswordAndEmailFieldEmpty() {
        UserServices userServices = new UserServices();
        assertTrue(userServices.FieldsEmpty("username","",""));
    }

    @Test
    void createAccountAllFieldsEmpty() {
        UserServices userServices = new UserServices();
        assertTrue(userServices.FieldsEmpty("","",""));
    }

    @Test
    void createAccountNoFieldsEmpty() {
        UserServices userServices = new UserServices();
        assertFalse(userServices.FieldsEmpty("MaisonRose","maison227@outlook.com","H1Delancey!"));
    }

    @Test
    void allSpacesUsername() {
        UserServices userServices = new UserServices();
        assertFalse(userServices.ValidUsername("           "));
    }

    @Test
    void shortUsername() {
        UserServices userServices = new UserServices();
        assertFalse(userServices.ValidUsername("uh"));
    }

    @Test
    void longUsername() {
        UserServices userServices = new UserServices();
        assertFalse(userServices.ValidUsername("IThinkThatThisUsernameIsGoingToBeToLong"));
    }

    @Test
    void spacesUsername() {
        UserServices userServices = new UserServices();
        assertFalse(userServices.ValidUsername("No Spaces allowed"));
    }

    @Test
    void specialUsername() {
        UserServices userServices = new UserServices();
        assertFalse(userServices.ValidUsername("NotA!!owed"));
    }

    @Test
    void threeCharacterUsername() {
        UserServices userServices = new UserServices();
        assertTrue(userServices.ValidUsername("ugh"));
    }

    @Test
    void twentyCharacterUsername() {
        UserServices userServices = new UserServices();
        assertTrue(userServices.ValidUsername("TwentyCharacters2020"));
    }

    @Test
    void twentyOneCharacterUsername() {
        UserServices userServices = new UserServices();
        assertFalse(userServices.ValidUsername("Twenty_One_Characters"));
    }

    @Test
    void validUsername1() {
        UserServices userServices = new UserServices();
        assertTrue(userServices.ValidUsername("tomas_ds06"));
    }

    @Test
    void validUsername2() {
        UserServices userServices = new UserServices();
        assertTrue(userServices.ValidUsername("Maisonxrose"));
    }

    @Test
    void allSpacesEmail() {
        UserServices userServices = new UserServices();
        assertFalse(userServices.ValidEmail("      "));
    }

    @Test
    void noATEmail() {
        UserServices userServices = new UserServices();
        assertFalse(userServices.ValidEmail("agrossioutlookcom"));
    }

    @Test
    void invalidATEmail1() {
        UserServices userServices = new UserServices();
        assertFalse(userServices.ValidEmail("agrossioutlookcom@"));
    }

    @Test
    void anATOnlyEmail() {
        UserServices userServices = new UserServices();
        assertFalse(userServices.ValidEmail("@"));
    }

    @Test
    void noDomainEmail() {
        UserServices userServices = new UserServices();
        assertFalse(userServices.ValidEmail("agrossi@outlook"));
    }

    @Test
    void invalidATEmail2() {
        UserServices userServices = new UserServices();
        assertFalse(userServices.ValidEmail("@agrossioutlookcom"));
    }

    @Test
    void validEmail1() {
        UserServices userServices = new UserServices();
        assertTrue(userServices.ValidEmail("agrossi@outlook.com"));
    }

    @Test
    void validEmail2() {
        UserServices userServices = new UserServices();
        assertTrue(userServices.ValidEmail("jpettigrew38@gmail.com"));
    }

    @Test
    void allSpacesPassword() {
        UserServices userServices = new UserServices();
        assertFalse(userServices.ValidPassword("           "));
    }

    @Test
    void lowercaseOnlyPassword() {
        UserServices userServices = new UserServices();
        assertFalse(userServices.ValidPassword("isthisalongenoughpassword"));
    }

    @Test
    void uppercaseOnlyPassword() {
        UserServices userServices = new UserServices();
        assertFalse(userServices.ValidPassword("ALEXANDERGROSSI"));
    }

    @Test
    void numberOnlyPassword() {
        UserServices userServices = new UserServices();
        assertFalse(userServices.ValidPassword("12345678910"));
    }

    @Test
    void specialCharactersPassword() {
        UserServices userServices = new UserServices();
        assertFalse(userServices.ValidPassword("!?!?!?!??!?!?!"));
    }

    @Test
    void shortPassword() {
        UserServices userServices = new UserServices();
        assertFalse(userServices.ValidPassword("H0ldSt!ll"));
    }

    @Test
    void workingPassword1() {
        UserServices userServices = new UserServices();
        assertTrue(userServices.ValidPassword("Delancey19!"));
    }

    @Test
    void workingPassword2() {
        UserServices userServices = new UserServices();
        assertTrue(userServices.ValidPassword("Dy1nOnThisH!ll"));
    }
}