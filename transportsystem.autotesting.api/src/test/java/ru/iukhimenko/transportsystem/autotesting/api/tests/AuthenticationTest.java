package ru.iukhimenko.transportsystem.autotesting.api.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.TestInstance.*;

import org.junit.jupiter.params.ParameterizedTest;
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
    @DisplayName("Status code = 200 when user logs in with correct credentials")
    @Epic("Authentication")
    @Severity(SeverityLevel.BLOCKER)
    public void statusCodeIsOkTest() {
        int actualStatusCode = authService.getAuthenticationRequestStatusCode(testUser);
        int expectedStatusCode = 200;
        assertThat(actualStatusCode)
                .as("Status code = 200 when user logs in with correct credentials")
                .isEqualTo(expectedStatusCode);
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
                .extracting(User::getUsername)
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
                .extracting(User::getUsername)
                .isEqualTo(testUser.getUsername());
    }

    @Test
    @DisplayName("Status code = 401 (Unauthorized) when user logs in with wrong password")
    @Epic("Authentication")
    @Severity(SeverityLevel.CRITICAL)
    public void canNotLogInWithWrongPasswordTest() {
        String wrongPassword = TestDataManager.getValidPassword();
        int actualStatusCode = authService.getAuthenticationRequestStatusCode(new User(testUser.getUsername(), wrongPassword));
        int expectedStatusCode = 401;
        assertThat(actualStatusCode)
                .as("Status code = 401 (Unauthorized) when user logs in with correct credentials")
                .isEqualTo(expectedStatusCode);
    }

    @ParameterizedTest(name = "Status code = 400 (Bad Request) when user logs in without password")
    @Epic("Authentication")
    @Severity(SeverityLevel.NORMAL)
    @EmptySource
    public void canNotLogInWithoutPasswordTest(String password) {
        int actualStatusCode = authService.getAuthenticationRequestStatusCode(new User(testUser.getUsername(), password));
        int expectedStatusCode = 400;
        assertThat(actualStatusCode)
                .as("Status code = 400 (Bad Request) when user logs in without password")
                .isEqualTo(expectedStatusCode);
    }

    @Test
    @DisplayName("User can log in with username having leading spaces")
    @Epic("Authentication")
    @Severity(SeverityLevel.MINOR)
    public void canLogInWithUsernameHavingLeadingSpaces() {
        String usernameWithLeadingSpaces = "   " + testUser.getUsername();
        User authenticatedUser = authService.authenticateUser(new User(usernameWithLeadingSpaces, testUser.getPassword()));
        assertThat(authenticatedUser)
                .as("Leading spaces in username are ignored on authentication")
                .extracting(User::getUsername)
                .isEqualTo(testUser.getUsername());
    }

    @Test
    @DisplayName("User can log in with username having trailing spaces")
    @Epic("Authentication")
    @Severity(SeverityLevel.MINOR)
    public void canLogInWithUsernameHavingTrailingSpaces() {
        User authenticatedUser = authService.authenticateUser(new User(testUser.getUsername() + "   ", testUser.getPassword()));
        assertThat(authenticatedUser)
                .as("Trailing spaces in username are ignored on authentication")
                .extracting(User::getUsername)
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