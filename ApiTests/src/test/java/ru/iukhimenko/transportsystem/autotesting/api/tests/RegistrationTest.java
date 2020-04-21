package ru.iukhimenko.transportsystem.autotesting.api.tests;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.iukhimenko.transportsystem.autotesting.api.ApiTest;
import ru.iukhimenko.transportsystem.autotesting.api.service.AuthService;
import ru.iukhimenko.transportsystem.autotesting.core.model.User;
import static org.assertj.core.api.Assertions.*;

public class RegistrationTest extends ApiTest {
    @Test
    @DisplayName("A user with unique username can register")
    public void canRegisterWithUniqueUsernameTest() {
        String testUsername = new Faker().name().username();
        User user = new User(testUsername, "1");
        AuthService authService = new AuthService();
        assertThat(authService.getUsers()).as("Registered user exists in the system").contains(user);
    }

    @Test
    @DisplayName("A user with non-unique username cannot register")
    public void canNotRegisterWithNonUniqueUsernameTest() {
        String uniqueUsername = new Faker().name().username();
        User firstUser = new User(uniqueUsername, "1");
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
        User user = new User("", "1");
        AuthService authService = new AuthService();
        int numberOfUsers = authService.getUsers().size();
        authService.registerUser(user);
        assertThat(authService.getUsers().size()).as("Number of users has not increased").isEqualTo(numberOfUsers);
    }

    @Test
    @DisplayName("A user cannot register without password")
    public void canNotRegisterWithoutPasswordTest() {
        User user = new User(new Faker().name().username(), "");
        AuthService authService = new AuthService();
        int numberOfUsers = authService.getUsers().size();
        authService.registerUser(user);
        assertThat(authService.getUsers().size()).as("Number of users has not increased").isEqualTo(numberOfUsers);
    }
}
