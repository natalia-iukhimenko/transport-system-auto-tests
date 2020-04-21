package ru.iukhimenko.transportsystem.autotesting.api.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.iukhimenko.transportsystem.autotesting.api.ApiTest;
import ru.iukhimenko.transportsystem.autotesting.api.service.AuthService;
import ru.iukhimenko.transportsystem.autotesting.core.model.User;
import ru.iukhimenko.transportsystem.autotesting.core.util.TestDataManager;

import static org.assertj.core.api.Assertions.*;

public class RegistrationTest extends ApiTest {
    @Test
    @DisplayName("A user with unique username can register")
    public void canRegisterWithUniqueUsernameTest() {
        String testUsername = TestDataManager.getValidUsername();
        User user = new User(testUsername, TestDataManager.getValidPassword());
        AuthService authService = new AuthService();
        authService.registerUser(user);
        assertThat(authService.getUsers()).as("Registered user exists in the system").contains(user);
    }

    @Test
    @DisplayName("A user with non-unique username cannot register")
    public void canNotRegisterWithNonUniqueUsernameTest() {
        String uniqueUsername = TestDataManager.getValidUsername();
        User firstUser = new User(uniqueUsername, TestDataManager.getValidPassword());
        AuthService authService = new AuthService();
        authService.registerUser(firstUser);
        int numberOfUsers = authService.getUsers().size();
        User secondUser = new User(firstUser.getUsername(), "2");
        authService.registerUser(secondUser);
        assertThat(authService.getUsers().size()).as("Number of users has not increased").isEqualTo(numberOfUsers);
    }

    @Test
    @DisplayName("A user cannot register without username")
    public void canNotRegisterWithoutUsernameTest() {
        User user = new User("", TestDataManager.getValidPassword());
        AuthService authService = new AuthService();
        int numberOfUsers = authService.getUsers().size();
        authService.registerUser(user);
        assertThat(authService.getUsers().size()).as("Number of users has not increased").isEqualTo(numberOfUsers);
    }

    @Test
    @DisplayName("A user cannot register without password")
    public void canNotRegisterWithoutPasswordTest() {
        User user = new User(TestDataManager.getValidUsername(), "");
        AuthService authService = new AuthService();
        int numberOfUsers = authService.getUsers().size();
        authService.registerUser(user);
        assertThat(authService.getUsers().size()).as("Number of users has not increased").isEqualTo(numberOfUsers);
    }
}