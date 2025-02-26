package ru.iukhimenko.transportsystem.autotesting.ui.pages;

import static com.codeborne.selenide.Condition.*;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.iukhimenko.transportsystem.autotesting.ui.SelenideElementHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class LogInPage {
    private final Logger logger = LoggerFactory.getLogger(LogInPage.class);
    private final SelenideElement loginField = $(By.xpath("//*[@test-id = 'username']"));
    private final SelenideElement passwordField = $(By.xpath("//*[@test-id = 'password']"));
    private final SelenideElement signInButton = $(By.xpath("//*[@test-id = 'sign_in_button']"));
    private final SelenideElement signUpButton = $(By.xpath("//*[@test-id = 'sign_up_button']"));
    private final SelenideElement errorMessageAlert = $(By.xpath("//*[@test-id = 'error-message']"));

    public LogInPage setLogin(String login) {
        logger.info("Typing login '{}' on authorization page", login);
        loginField.setValue(login);
        return this;
    }

    public LogInPage setPassword(String password) {
        logger.info("Typing password '{}' on authorization page", password);
        passwordField.setValue(password);
        return this;
    }

    public void clickSignInButton() {
        logger.info("Clicking 'Sign in' button on authorization page");
        signInButton.click();
    }

    public RegistrationPage clickSignUpButton() {
        logger.info("Clicking 'Sign up' button on authorization page");
        signUpButton.click();
        return new RegistrationPage();
    }

    public HomePage logInWith(String login, String password) {
        setLogin(login);
        setPassword(password);
        clickSignInButton();
        return new HomePage();
    }

    public boolean isErrorMessageShown() {
        return errorMessageAlert.shouldBe(visible).isDisplayed();
    }

    public String getErrorMessageText() {
        return errorMessageAlert.getText();
    }

    public boolean isOpened() {
        SelenideElement label = $(By.xpath("//*[@test-id = 'log_in_label']"));
        label.shouldBe(visible, Duration.ofSeconds(10000));
        return label.isDisplayed();
    }

    public boolean isLoginFieldHighlightedAsInvalid() {
        return SelenideElementHelper.isFieldHighlightedAsInvalid(loginField);
    }

    public boolean isPasswordFieldHighlightedAsInvalid() {
        return SelenideElementHelper.isFieldHighlightedAsInvalid(passwordField);
    }
}