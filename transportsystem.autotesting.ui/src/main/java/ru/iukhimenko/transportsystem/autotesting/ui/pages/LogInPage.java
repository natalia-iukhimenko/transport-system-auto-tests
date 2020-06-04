package ru.iukhimenko.transportsystem.autotesting.ui.pages;

import static com.codeborne.selenide.Condition.*;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.$;

public class LogInPage {
    private SelenideElement loginField = $(By.id("input-1"));
    private SelenideElement passwordField = $(By.id("input-2"));;
    private SelenideElement logInButton = $(By.xpath("//button/span[text()='Войти']"));;

    public LogInPage setLogin(String login) {
        loginField.setValue(login);
        return this;
    }

    public LogInPage setPassword(String password) {
        passwordField.setValue(password);
        return this;
    }

    public void clickLogInButton() {
        logInButton.click();
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

    public boolean isLoginFieldHighlightedAsInvalid() {
        return isFieldHighlightedAsInvalid(loginField);
    }

    public boolean isPasswordFieldHighlightedAsInvalid() {
        return isFieldHighlightedAsInvalid(passwordField);
    }

    private boolean isFieldHighlightedAsInvalid(SelenideElement field) {
        return field.attr("aria-invalid") != null;
    }
}
