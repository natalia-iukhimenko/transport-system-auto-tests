package ru.iukhimenko.transportsystem.autotesting.ui.tests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.iukhimenko.transportsystem.autotesting.ui.base.UiTest;
import ru.iukhimenko.transportsystem.autotesting.ui.pages.LogInPage;
import ru.iukhimenko.transportsystem.autotesting.ui.tags.UiRegression;

import java.util.stream.Stream;
import static com.codeborne.selenide.Selenide.open;
import static ru.iukhimenko.transportsystem.autotesting.core.Configs.*;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("ui_auth")
@UiRegression
public class LogInTest extends UiTest {
    @ParameterizedTest
    @MethodSource("provideWrongUsernamePasswordPair")
    @Severity(SeverityLevel.CRITICAL)
    public void alertIsShownWhenLogInWithWrongUsernameOrPasswordTest(String username, String password) {
        LogInPage page = open(BASE_URI, LogInPage.class);
        page.logInWith(username, password);
        assertThat(page.showsWrongLoginOrPasswordMessage())
                .as("Alert is shown if a user logs in with wrong username or password")
                .isTrue();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    public void loginIsHighlightedAsInvalidWhenSubmitWithEmptyLoginTest() {
        LogInPage page = open(BASE_URI, LogInPage.class);
        page.setPassword(ADMIN_PASSWORD)
                .clickLogInButton();
        assertThat(page.isLoginFieldHighlightedAsInvalid())
                .as("Login field is highlighted as invalid if a user logs in without login")
                .isTrue();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    public void passwordIsHighlightedAsInvalidWhenSubmitWithEmptyPasswordTest() {
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