package ru.iukhimenko.transportsystem.autotesting.api.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.iukhimenko.transportsystem.autotesting.api.ApiTest;
import ru.iukhimenko.transportsystem.autotesting.api.service.AuthService;
import ru.iukhimenko.transportsystem.autotesting.api.tags.ApiRegression;
import ru.iukhimenko.transportsystem.autotesting.api.tags.ApiSmoke;
import ru.iukhimenko.transportsystem.autotesting.core.model.User;
import ru.iukhimenko.transportsystem.autotesting.core.util.TestDataManager;
import static org.assertj.core.api.Assertions.*;

@Tag("api_registration")
@ApiRegression
public class RegistrationTest extends ApiTest {
    @Test
    @ApiSmoke
    @DisplayName("A user with unique valid username can register")
    @Epic("Registration")
    @Severity(SeverityLevel.BLOCKER)
    public void canRegisterWithUniqueValidUsernameTest() {
        String testUsername = TestDataManager.getValidUsername();
        User user = new User(testUsername, TestDataManager.getValidPassword());
        AuthService authService = new AuthService();
        authService.registerUser(user);
        assertThat(authService.getUsers())
                .as("Registered user exists in the system")
                .extracting("username")
                .contains(user.getUsername());
    }

    @Test
    @ApiSmoke
    @DisplayName("A user with non-unique username cannot register")
    @Epic("Registration")
    @Severity(SeverityLevel.BLOCKER)
    public void canNotRegisterWithNonUniqueUsernameTest() {
        String uniqueUsername = TestDataManager.getValidUsername();
        User firstUser = new User(uniqueUsername, TestDataManager.getValidPassword());
        AuthService authService = new AuthService();
        authService.registerUser(firstUser);
        int numberOfUsers = authService.getUsers().size();
        User secondUser = new User(firstUser.getUsername(), "2");
        authService.registerUser(secondUser);
        assertThat(authService.getUsers().size())
                .as("Number of users has not increased")
                .isEqualTo(numberOfUsers);
    }

    @ParameterizedTest(name = "A user can not register with unsupported username: {0}")
    @ValueSource(strings = {"a1bc", "abc!", "abc?", "ab,c", "ab c", "ab#c", "ab@c", "ab$c", "ab&c"})
    @Description(value = "Only latin letters, underscores and points are allowed in a username. Other characters are not supported")
    @Epic("Registration")
    @Severity(SeverityLevel.NORMAL)
    public void canNotRegisterWithUnsupportedUsername(String username) {
        String password = TestDataManager.getValidPassword();
        AuthService authService = new AuthService();
        int numberOfUsers = authService.getUsers().size();
        User testUser = new User(username, password);
        authService.registerUser(testUser);
        assertThat(authService.getUsers().size())
                .as("Number of users has not increased")
                .isEqualTo(numberOfUsers);
    }

    @ParameterizedTest(name = "A user can not register with unsupported password: {0}")
    @ValueSource(strings = {"1", "12345", "abc?"})
    @Description(value = "Password length should be at least 6 characters")
    @Epic("Registration")
    @Severity(SeverityLevel.NORMAL)
    public void canNotRegisterWithUnsupportedPassword(String password) {
        String username = TestDataManager.getValidUsername();
        AuthService authService = new AuthService();
        int numberOfUsers = authService.getUsers().size();
        User testUser = new User(username, password);
        authService.registerUser(testUser);
        assertThat(authService.getUsers().size())
                .as("Number of users has not increased")
                .isEqualTo(numberOfUsers);
    }

    @Test
    @DisplayName("A user cannot register without username")
    @Epic("Registration")
    @Severity(SeverityLevel.NORMAL)
    public void canNotRegisterWithoutUsernameTest() {
        User user = new User("", TestDataManager.getValidPassword());
        AuthService authService = new AuthService();
        int numberOfUsers = authService.getUsers().size();
        authService.registerUser(user);
        assertThat(authService.getUsers().size()).as("Number of users has not increased").isEqualTo(numberOfUsers);
    }

    @Test
    @DisplayName("A user cannot register without password")
    @Epic("Registration")
    @Severity(SeverityLevel.NORMAL)
    public void canNotRegisterWithoutPasswordTest() {
        User user = new User(TestDataManager.getValidUsername(), "");
        AuthService authService = new AuthService();
        int numberOfUsers = authService.getUsers().size();
        authService.registerUser(user);
        assertThat(authService.getUsers().size())
                .as("Number of users has not increased")
                .isEqualTo(numberOfUsers);
    }
}