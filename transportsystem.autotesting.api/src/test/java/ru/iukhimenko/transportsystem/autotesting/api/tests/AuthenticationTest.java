package ru.iukhimenko.transportsystem.autotesting.api.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.iukhimenko.transportsystem.autotesting.api.ApiTest;
import ru.iukhimenko.transportsystem.autotesting.api.service.AuthService;
import ru.iukhimenko.transportsystem.autotesting.api.tags.ApiRegression;
import ru.iukhimenko.transportsystem.autotesting.api.tags.ApiSmoke;
import ru.iukhimenko.transportsystem.autotesting.core.model.User;
import ru.iukhimenko.transportsystem.autotesting.core.util.TestDataManager;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("api_auth")
@ApiRegression
public class AuthenticationTest extends ApiTest {
    @Test
    @ApiSmoke
    @DisplayName("User can log in with correct credentials")
    @Epic("Authentication")
    @Severity(SeverityLevel.BLOCKER)
    public void canLoginWithCorrectCredentialsTest() {
        String username = TestDataManager.getValidUsername(), password = TestDataManager.getValidPassword();
        AuthService authService = new AuthService();
        User registeredUser = new User(username, password);
        authService.registerUser(registeredUser);

        User authenticatedUser = authService.authenticateUser(registeredUser);
        assertThat(authenticatedUser)
                .as("User is successfully authenticated with correct credentials")
                .isNotNull()
                .extracting(user -> user.getUsername())
                .isEqualTo(registeredUser.getUsername());
    }

    @Test
    @DisplayName("User can log in with username in another case")
    @Epic("Authentication")
    @Severity(SeverityLevel.CRITICAL)
    public void canLogInWithUsernameInAnotherCaseTest() {
        String sourceUsername = TestDataManager.getValidUsername(), password = TestDataManager.getValidPassword();
        AuthService authService = new AuthService();
        User registeredUser = new User(sourceUsername, password);
        authService.registerUser(registeredUser);

        String usernameInAnotherCase = changeFirstLetterCase(registeredUser.getUsername());
        User authenticatedUser = authService.authenticateUser(new User(usernameInAnotherCase, registeredUser.getPassword()));
        assertThat(authenticatedUser)
                .as("Username is not case-sensitive")
                .isNotNull()
                .extracting(user -> user.getUsername())
                .isEqualTo(registeredUser.getUsername());
    }

    @Test
    @DisplayName("User can not log in with wrong password")
    @Epic("Authentication")
    @Severity(SeverityLevel.CRITICAL)
    public void canNotLogInWithWrongPasswordTest() {
        String username = TestDataManager.getValidUsername(), password = TestDataManager.getValidPassword();
        AuthService authService = new AuthService();
        User registeredUser = new User(username, password);
        authService.registerUser(registeredUser);

        String wrongPassword = TestDataManager.getValidPassword();
        User authenticatedUser = authService.authenticateUser(new User(registeredUser.getUsername(), wrongPassword));
        assertThat(authenticatedUser)
                .as("User is not authenticated with wrong password")
                .isNull();
    }

    @Test
    @DisplayName("User can not log in without password")
    @Epic("Authentication")
    @Severity(SeverityLevel.NORMAL)
    public void canNotLogInWithoutPasswordTest() {
        String username = TestDataManager.getValidUsername(), password = TestDataManager.getValidPassword();
        AuthService authService = new AuthService();
        User registeredUser = new User(username, password);
        authService.registerUser(registeredUser);

        User authenticatedUser = authService.authenticateUser(new User(registeredUser.getUsername(), ""));
        assertThat(authenticatedUser)
                .as("User is not authenticated without password")
                .isNull();
    }

    @Test
    @DisplayName("User can log in with username having leading spaces")
    @Epic("Authentication")
    @Severity(SeverityLevel.MINOR)
    public void canLogInWithUsernameHavingLeadingSpaces() {
        String username = TestDataManager.getValidUsername(), password = TestDataManager.getValidPassword();
        AuthService authService = new AuthService();
        User registeredUser = new User(username, password);
        authService.registerUser(registeredUser);

        User authenticatedUser = authService.authenticateUser(new User("   " + registeredUser.getUsername(), registeredUser.getPassword()));
        assertThat(authenticatedUser)
                .as("Leading spaces in username are ignored on authentication")
                .isNotNull()
                .extracting(user -> user.getUsername())
                .isEqualTo(registeredUser.getUsername());
    }

    @Test
    @DisplayName("User can log in with username having trailing spaces")
    @Epic("Authentication")
    @Severity(SeverityLevel.MINOR)
    public void canLogInWithUsernameHavingTrailingSpaces() {
        String username = TestDataManager.getValidUsername(), password = TestDataManager.getValidPassword();
        AuthService authService = new AuthService();
        User registeredUser = new User(username, password);
        authService.registerUser(registeredUser);

        User authenticatedUser = authService.authenticateUser(new User(registeredUser.getUsername() + "   ", registeredUser.getPassword()));
        assertThat(authenticatedUser)
                .as("Trailing spaces in username are ignored on authentication")
                .isNotNull()
                .extracting(user -> user.getUsername())
                .isEqualTo(registeredUser.getUsername());
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
