package ru.iukhimenko.transportsystem.autotesting.api.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.TestInstance.*;

import org.junit.jupiter.params.provider.EmptySource;
import ru.iukhimenko.transportsystem.autotesting.api.ApiTest;
import ru.iukhimenko.transportsystem.autotesting.api.service.AuthService;
import ru.iukhimenko.transportsystem.autotesting.api.tags.ApiRegression;
import ru.iukhimenko.transportsystem.autotesting.api.tags.ApiSmoke;
import ru.iukhimenko.transportsystem.autotesting.core.model.User;
import ru.iukhimenko.transportsystem.autotesting.core.util.TestDataManager;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("api_auth")
@ApiRegression
@TestInstance(Lifecycle.PER_CLASS)
public class AuthenticationTest extends ApiTest {
    AuthService authService;
    User testUser;

    @BeforeAll
    public void createTestUser() {
        testUser = new User(TestDataManager.getValidUsername(), TestDataManager.getValidPassword());
        authService = new AuthService();
        authService.registerUser(testUser);
    }

    @Test
    @ApiSmoke
    @DisplayName("User can log in with correct credentials")
    @Epic("Authentication")
    @Severity(SeverityLevel.BLOCKER)
    public void canLoginWithCorrectCredentialsTest() {
        User authenticatedUser = authService.authenticateUser(testUser);
        assertThat(authenticatedUser)
                .as("User is successfully authenticated with correct credentials")
                .isNotNull()
                .extracting(user -> user.getUsername())
                .isEqualTo(testUser.getUsername());
    }

    @Test
    @DisplayName("User can log in with username in another case")
    @Epic("Authentication")
    @Severity(SeverityLevel.CRITICAL)
    public void canLogInWithUsernameInAnotherCaseTest() {
        String usernameInAnotherCase = changeFirstLetterCase(testUser.getUsername());
        User authenticatedUser = authService.authenticateUser(new User(usernameInAnotherCase, testUser.getPassword()));
        assertThat(authenticatedUser)
                .as("Username is not case-sensitive")
                .isNotNull()
                .extracting(user -> user.getUsername())
                .isEqualTo(testUser.getUsername());
    }

    @Test
    @DisplayName("User can not log in with wrong password")
    @Epic("Authentication")
    @Severity(SeverityLevel.CRITICAL)
    public void canNotLogInWithWrongPasswordTest() {
        String wrongPassword = TestDataManager.getValidPassword();
        User authenticatedUser = authService.authenticateUser(new User(testUser.getUsername(), wrongPassword));
        assertThat(authenticatedUser)
                .as("User is not authenticated with wrong password")
                .isNull();
    }

    @Test
    @DisplayName("User can not log in without password")
    @Epic("Authentication")
    @Severity(SeverityLevel.NORMAL)
    @EmptySource
    public void canNotLogInWithoutPasswordTest(String password) {
        User authenticatedUser = authService.authenticateUser(new User(testUser.getUsername(), password));
        assertThat(authenticatedUser)
                .as("User is not authenticated without password")
                .isNull();
    }

    @Test
    @DisplayName("User can log in with username having leading spaces")
    @Epic("Authentication")
    @Severity(SeverityLevel.MINOR)
    public void canLogInWithUsernameHavingLeadingSpaces() {
        StringBuilder usernameWithLeadingSpaces = new StringBuilder("   ").append(testUser.getUsername());
        User authenticatedUser = authService.authenticateUser(new User(usernameWithLeadingSpaces.toString(), testUser.getPassword()));
        assertThat(authenticatedUser)
                .as("Leading spaces in username are ignored on authentication")
                .isNotNull()
                .extracting(user -> user.getUsername())
                .isEqualTo(testUser.getUsername());
    }

    @Test
    @DisplayName("User can log in with username having trailing spaces")
    @Epic("Authentication")
    @Severity(SeverityLevel.MINOR)
    public void canLogInWithUsernameHavingTrailingSpaces() {
        StringBuilder usernameWithTrailingSpaces = new StringBuilder(testUser.getUsername()).append("   ");
        User authenticatedUser = authService.authenticateUser(new User(usernameWithTrailingSpaces.toString(), testUser.getPassword()));
        assertThat(authenticatedUser)
                .as("Trailing spaces in username are ignored on authentication")
                .isNotNull()
                .extracting(user -> user.getUsername())
                .isEqualTo(testUser.getUsername());
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
