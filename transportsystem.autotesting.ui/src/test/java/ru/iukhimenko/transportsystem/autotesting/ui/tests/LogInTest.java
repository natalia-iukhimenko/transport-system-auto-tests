package ru.iukhimenko.transportsystem.autotesting.ui.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.iukhimenko.transportsystem.autotesting.ui.base.UiTest;
import ru.iukhimenko.transportsystem.autotesting.ui.pages.LogInPage;

import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.open;
import static ru.iukhimenko.transportsystem.autotesting.core.Configs.*;
import static org.assertj.core.api.Assertions.assertThat;

public class LogInTest extends UiTest {
    @ParameterizedTest
    @MethodSource("provideWrongUsernamePasswordPair")
    public void alertIsShownWhenLogInWithWrongUsernameOrPassword(String username, String password) {
        LogInPage page = open(BASE_URI, LogInPage.class);
        page.logInWith(username, password);
        assertThat(page.showsWrongLoginOrPasswordMessage())
                .as("Alert is shown if a user logs in with wrong username or password")
                .isTrue();
    }

    @Test
    public void loginFieldIsHighlightedAsInvalidWhenSubmitWithEmptyLogin() {
        LogInPage page = open(BASE_URI, LogInPage.class);
        page.setPassword(ADMIN_PASSWORD)
                .clickLogInButton();
        assertThat(page.isLoginFieldHighlightedAsInvalid())
                .as("Login field is highlighted as invalid if a user logs in without login")
                .isTrue();
    }

    @Test
    public void passwordFieldIsHighlightedAsInvalidWhenSubmitWithEmptyPassword() {
        LogInPage page = open(BASE_URI, LogInPage.class);
        page.setLogin(ADMIN_USERNAME)
                .clickLogInButton();
        assertThat(page.isPasswordFieldHighlightedAsInvalid())
                .as("Password field is highlighted as invalid if a user logs in without password")
                .isTrue();
    }

    private static Stream<Arguments> provideWrongUsernamePasswordPair() {
        return Stream.of(
                Arguments.of(ADMIN_USERNAME, "wrong_password"),
                Arguments.of("wrong_username", ADMIN_PASSWORD)
        );
    }
}