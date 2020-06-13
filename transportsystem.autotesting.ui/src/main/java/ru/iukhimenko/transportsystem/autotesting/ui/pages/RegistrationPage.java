package ru.iukhimenko.transportsystem.autotesting.ui.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.iukhimenko.transportsystem.autotesting.ui.base.IPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class RegistrationPage implements IPage {
    private Logger logger = LoggerFactory.getLogger(RegistrationPage.class);
    private SelenideElement loginField = $(By.id("input-1"));
    private SelenideElement passwordField = $(By.id("input-2"));
    private SelenideElement registerButton = $(By.id("signup"));

    public RegistrationPage setLogin(String login) {
        loginField.setValue(login);
        logger.info("Typed login: " + login);
        return this;
    }

    public RegistrationPage setPassword(String password) {
        passwordField.setValue(password);
        logger.info("Typed password: " + password);
        return this;
    }

    public LogInPage clickRegisterButton() {
        registerButton.click();
        logger.info("Clicked 'Register' button");
        return new LogInPage();
    }

    public LogInPage registerAs(String login, String password) {
        this.setLogin(login)
                .setPassword(password)
                .clickRegisterButton();
        return new LogInPage();
    }

    public String getLoginFieldPromptText() {
        return $(By.id("input-group-1")).text();
    }

    public String getPasswordFieldPromptText() {
        return $(By.id("input-group-2")).text();
    }

    public boolean isLoginFieldHighlightedAsInvalid() {
        return isFieldHighlightedAsInvalid(loginField);
    }

    public boolean isPasswordFieldHighlightedAsInvalid() {
        return isFieldHighlightedAsInvalid(passwordField);
    }

    public boolean showsLoginIsBusy() {
        return $(By.xpath("//div[text()='Логин уже занят!']"))
                .shouldBe(visible)
                .isDisplayed();
    }
}
