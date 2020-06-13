package ru.iukhimenko.transportsystem.autotesting.ui.pages;

import static com.codeborne.selenide.Condition.*;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.iukhimenko.transportsystem.autotesting.ui.base.IPage;

import static com.codeborne.selenide.Selenide.$;

public class LogInPage implements IPage {
    private Logger logger = LoggerFactory.getLogger(LogInPage.class);
    private SelenideElement loginField = $(By.id("input-1"));
    private SelenideElement passwordField = $(By.id("input-2"));
    private SelenideElement logInButton = $(By.id("signin"));

    public LogInPage setLogin(String login) {
        loginField.setValue(login);
        logger.info("Typed login '" + login + "' on authorization page");
        return this;
    }

    public LogInPage setPassword(String password) {
        passwordField.setValue(password);
        logger.info("Typed password '" + password + "' on authorization page");
        return this;
    }

    public void clickLogInButton() {
        logInButton.click();
    }

    public RegistrationPage clickRegisterButton() {
        $(By.id("signup")).click();
        logger.info("Clicked 'Log in' button on authorization page");
        return new RegistrationPage();
    }

    public void logInWith(String login, String password) {
        setLogin(login);
        setPassword(password);
        clickLogInButton();
    }

    public boolean showsWrongLoginOrPasswordMessage() {
        return $(By.xpath("//div[@role='alert' and text()='Неверный логин или пароль!']"))
                .shouldBe(visible)
                .isDisplayed();
    }

    public boolean hasLogInLegend() {
        SelenideElement legend = $(By.xpath("//legend[text()='Вход в систему']"));
        legend.waitUntil(visible, 10000);
        return legend.isDisplayed();
    }

    public boolean isLoginFieldHighlightedAsInvalid() {
        return isFieldHighlightedAsInvalid(loginField);
    }

    public boolean isPasswordFieldHighlightedAsInvalid() {
        return isFieldHighlightedAsInvalid(passwordField);
    }
}
