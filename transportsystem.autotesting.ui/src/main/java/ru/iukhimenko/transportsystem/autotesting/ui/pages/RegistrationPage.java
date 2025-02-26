package ru.iukhimenko.transportsystem.autotesting.ui.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.iukhimenko.transportsystem.autotesting.ui.SelenideElementHelper;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class RegistrationPage {
    private final Logger logger = LoggerFactory.getLogger(RegistrationPage.class);
    private final SelenideElement loginField = $(By.xpath("//*[@test-id = 'username']"));
    private final SelenideElement passwordField = $(By.xpath("//*[@test-id = 'password']"));
    private final SelenideElement confirmSignUpButton = $(By.xpath("//button[@test-id = 'confirm_signup']"));
    private final SelenideElement errorMessageAlert = $(By.xpath("//*[@test-id = 'error_message']"));

    public RegistrationPage setLogin(String login) {
        logger.info("Typing login: {}", login);
        loginField.setValue(login);
        return this;
    }

    public RegistrationPage setPassword(String password) {
        logger.info("Typing password: {}", password);
        passwordField.setValue(password);
        return this;
    }

    public LogInPage clickOnConfirmSignUpButton() {
        logger.info("Clicking on 'Confirm Sign Up' button");
        confirmSignUpButton.click();
        return new LogInPage();
    }

    public LogInPage registerAs(String login, String password) {
        this.setLogin(login)
                .setPassword(password)
                .clickOnConfirmSignUpButton();
        return new LogInPage();
    }

    public String getLoginFieldPromptText() {
        return $(By.xpath("//*[@test-id = 'username_prompt']")).text();
    }

    public String getPasswordFieldPromptText() {
        return $(By.xpath("//*[@test-id = 'password_prompt']")).text();
    }

    public boolean isLoginFieldHighlightedAsInvalid() {
        return SelenideElementHelper.isFieldHighlightedAsInvalid(loginField);
    }

    public boolean isPasswordFieldHighlightedAsInvalid() {
        return SelenideElementHelper.isFieldHighlightedAsInvalid(passwordField);
    }

    public boolean isErrorMessageShown() {
        return errorMessageAlert.should(exist)
                .shouldBe(visible)
                .isDisplayed();
    }

    public String getErrorMessageText() {
        return errorMessageAlert.getText();
    }
}
