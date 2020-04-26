package ru.iukhimenko.transportsystem.autotesting.api.tests;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.iukhimenko.transportsystem.autotesting.api.ApiTest;
import ru.iukhimenko.transportsystem.autotesting.api.service.AuthService;
import ru.iukhimenko.transportsystem.autotesting.core.model.User;
import ru.iukhimenko.transportsystem.autotesting.core.util.TestDataManager;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthenticationTest extends ApiTest {
    @Test
    @DisplayName("User can log in with correct credentials")
    public void canLoginWithCorrectCredentialsTest() {
        String username = TestDataManager.getValidUsername(), password = TestDataManager.getValidPassword();
        AuthService authService = new AuthService();
        User registeredUser = new User(username, password);
        authService.registerUser(registeredUser);

        User authenticatedUser = authService.authenticateUser(registeredUser);
        assertThat(authenticatedUser).as("User is successfully authenticated with correct credentials").isEqualTo(registeredUser);
    }

    @Test
    @DisplayName("User can log in with username in another case")
    public void canLogInWithUsernameInAnotherCaseTest() {
        String sourceUsername = TestDataManager.getValidUsername(), password = TestDataManager.getValidPassword();
        AuthService authService = new AuthService();
        User registeredUser = new User(sourceUsername, password);
        authService.registerUser(registeredUser);

        String usernameInAnotherCase = changeFirstLetterCase(registeredUser.getUsername());
        User authenticatedUser = authService.authenticateUser(new User(usernameInAnotherCase, registeredUser.getPassword()));
        assertThat(authenticatedUser).as("Username is not case-sensitive").isEqualTo(registeredUser);
    }

    @Test
    @DisplayName("User can not log in with wrong password")
    public void canNotLogInWithWrongPasswordTest() {
        String username = TestDataManager.getValidUsername(), password = TestDataManager.getValidPassword();
        AuthService authService = new AuthService();
        User registeredUser = new User(username, password);
        authService.registerUser(registeredUser);

        String wrongPassword = TestDataManager.getValidPassword();
        User authenticatedUser = authService.authenticateUser(new User(registeredUser.getUsername(), wrongPassword));
        assertThat(authenticatedUser).as("User is not authenticated with wrong password").isNull();
    }

    @Test
    @DisplayName("User can not log in without password")
    public void canNotLogInWithoutPasswordTest() {
        String username = TestDataManager.getValidUsername(), password = TestDataManager.getValidPassword();
        AuthService authService = new AuthService();
        User registeredUser = new User(username, password);
        authService.registerUser(registeredUser);

        User authenticatedUser = authService.authenticateUser(new User(registeredUser.getUsername(), ""));
        assertThat(authenticatedUser).as("User is not authenticated without password").isNull();
    }

    @Test
    @DisplayName("User can log in with username having leading spaces")
    public void canLogInWithUsernameHavingLeadingSpaces() {
        String username = TestDataManager.getValidUsername(), password = TestDataManager.getValidPassword();
        AuthService authService = new AuthService();
        User registeredUser = new User(username, password);
        authService.registerUser(registeredUser);

        User authenticatedUser = authService.authenticateUser(new User("   " + registeredUser.getUsername(), registeredUser.getPassword()));
        assertThat(authenticatedUser).as("Leading spaces in username are ignored on authentication").isEqualTo(registeredUser);
    }

    @Test
    @DisplayName("User can log in with username having trailing spaces")
    public void canLogInWithUsernameHavingTrailingSpaces() {
        String username = TestDataManager.getValidUsername(), password = TestDataManager.getValidPassword();
        AuthService authService = new AuthService();
        User registeredUser = new User(username, password);
        authService.registerUser(registeredUser);

        User authenticatedUser = authService.authenticateUser(new User(registeredUser.getUsername() + "   ", registeredUser.getPassword()));
        assertThat(authenticatedUser).as("Trailing spaces in username are ignored on authentication").isEqualTo(registeredUser);
    }

    private String changeFirstLetterCase(String sourceString) {
        if (StringUtils.isNotEmpty(sourceString)) {
            StringBuilder resultString = new StringBuilder(sourceString);
            char firstLetter = resultString.charAt(0);
            if (Character.isUpperCase(firstLetter))
                resultString.setCharAt(0, Character.toLowerCase(firstLetter));
            else
                resultString.setCharAt(0, Character.toUpperCase(firstLetter));
            return resultString.toString();
        }
        else
            return sourceString;
    }
}