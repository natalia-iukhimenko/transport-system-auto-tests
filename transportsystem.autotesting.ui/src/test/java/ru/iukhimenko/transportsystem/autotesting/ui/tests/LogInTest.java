package ru.iukhimenko.transportsystem.autotesting.ui.tests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.iukhimenko.transportsystem.autotesting.ui.base.UiTest;
import ru.iukhimenko.transportsystem.autotesting.ui.pages.HomePage;
import ru.iukhimenko.transportsystem.autotesting.ui.pages.LogInPage;
import ru.iukhimenko.transportsystem.autotesting.ui.tags.UiRegression;
import ru.iukhimenko.transportsystem.autotesting.ui.tags.UiSmoke;

import java.util.stream.Stream;
import static com.codeborne.selenide.Selenide.open;
import static ru.iukhimenko.transportsystem.autotesting.core.Configs.*;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("ui_auth")
@UiRegression
public class LogInTest extends UiTest {
    @Test
    @UiSmoke
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Username is displayed after successful authentication")
    public void usernameIsDisplayedAfterSuccessfulAuthenticationTest() {
        String username = ADMIN_USERNAME, password = ADMIN_PASSWORD;
        HomePage homePage = open(BASE_URI, LogInPage.class).logInWith(username, password);
        String displayedUsername = homePage.getPageHeader().getDisplayedUsername();
        assertThat(displayedUsername).isEqualTo(username);
    }

    @ParameterizedTest
    @MethodSource("provideWrongUsernamePasswordPair")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Alert is shown if a user logs in with wrong credentials")
    public void alertIsShownWhenLogInWithWrongUsernameOrPasswordTest(String username, String password) {
        LogInPage page = open(BASE_URI, LogInPage.class);
        page.logInWith(username, password);
        assertThat(page.showsWrongLoginOrPasswordMessage())
                .as("Alert is shown if a user logs in with wrong username or password")
                .isTrue();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Login field is highlighted as invalid if a user logs in without login")
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
    @DisplayName("Password field is highlighted as invalid if a user logs in without password")
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