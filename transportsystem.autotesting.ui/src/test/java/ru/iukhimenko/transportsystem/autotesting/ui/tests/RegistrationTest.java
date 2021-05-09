package ru.iukhimenko.transportsystem.autotesting.ui.tests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.iukhimenko.transportsystem.autotesting.ui.base.UiTest;
import ru.iukhimenko.transportsystem.autotesting.ui.pages.LogInPage;
import ru.iukhimenko.transportsystem.autotesting.ui.pages.RegistrationPage;
import ru.iukhimenko.transportsystem.autotesting.ui.tags.UiRegression;
import ru.iukhimenko.transportsystem.autotesting.ui.tags.UiSmoke;

import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.iukhimenko.transportsystem.autotesting.core.TransportSystemConfig.TRANSPORT_SYSTEM_CONFIG;
import static ru.iukhimenko.transportsystem.autotesting.core.util.TestDataManager.getValidPassword;
import static ru.iukhimenko.transportsystem.autotesting.core.util.TestDataManager.getValidUsername;

@Tag("ui_register")
@UiRegression
public class RegistrationTest extends UiTest {
    @BeforeEach
    public void openRegistrationPage() {
        open(TRANSPORT_SYSTEM_CONFIG.baseUrl(), LogInPage.class)
                .clickRegisterButton();
    }

    @Test
    @UiSmoke
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("'Log In' page is opened after successful registration")
    public void redirectToLogInPageAfterSuccessfulRegistration() {
        String username = getValidUsername(), password = getValidPassword();
        LogInPage logInPage = new RegistrationPage().registerAs(username, password);
        assertThat(logInPage.hasLogInLegend())
                .as("Log In page is opened after successful registration")
                .isTrue();
    }

    @Test
    @UiSmoke
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("'Login is busy!' message is shown after registration with not unique login")
    public void alertIsShownWhenRegisterWithNotUniqueUsernameTest() {
        RegistrationPage page = new RegistrationPage();
        page.registerAs(TRANSPORT_SYSTEM_CONFIG.adminUsername(), getValidPassword());
        assertThat(page.showsLoginIsBusy())
                .as("'Login is busy!' message is shown after registration with not unique login")
                .isTrue();
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {"_test1", "!test", "te,st", "test?", "тест"})
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Login field is highlighted as invalid when filled with unsupported value")
    public void loginFieldHighlightedAsInvalidWhenInputUnsupportedLoginTest(String login) {
        RegistrationPage page = new RegistrationPage();
        page.setLogin(login);
        assertThat(page.isLoginFieldHighlightedAsInvalid())
                .as("Login field is highlighted as invalid when filled with unsupported value")
                .isTrue();
    }

    @ParameterizedTest
    @EmptySource
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Login field is highlighted as invalid when register with empty value")
    public void loginFieldHighlightedAsInvalidWhenRegisterWithEmptyLoginTest(String login) {
        RegistrationPage page = new RegistrationPage();
        page.setLogin(login)
                .setPassword(getValidPassword())
                .clickRegisterButton();
        assertThat(page.isLoginFieldHighlightedAsInvalid())
                .as("Login field is highlighted as invalid when register with empty value")
                .isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "12345", "abcde"})
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Password field is highlighted as invalid when filled with unsupported value")
    public void passwordFieldIsHighlightedAsInvalidWhenInputUnsupportedPasswordTest(String password) {
        RegistrationPage page = new RegistrationPage();
        page.setPassword(password);
        assertThat(page.isPasswordFieldHighlightedAsInvalid())
                .as("Password field is highlighted as invalid when filled with unsupported value")
                .isTrue();
    }

    @ParameterizedTest
    @EmptySource
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Password field is highlighted as invalid when register with empty value")
    public void passwordFieldIsHighlightedAsInvalidWhenRegisterWithEmptyPasswordTest(String password) {
        RegistrationPage page = new RegistrationPage();
        page.setLogin(getValidUsername())
                .setPassword(password)
                .clickRegisterButton();
        assertThat(page.isPasswordFieldHighlightedAsInvalid())
                .as("Password field is highlighted as invalid when register with empty value")
                .isTrue();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    public void loginPromptTextTest() {
        RegistrationPage page = new RegistrationPage();
        assertThat(page.getLoginFieldPromptText()).isEqualTo("Допустимы только латинские буквы, точка и символ подчёркивания");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    public void passwordPromptTextTest() {
        RegistrationPage page = new RegistrationPage();
        assertThat(page.getPasswordFieldPromptText()).isEqualTo("Должен быть не короче 6 символов");
    }
}
