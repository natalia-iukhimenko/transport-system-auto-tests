package ru.iukhimenko.transportsystem.autotesting.ui.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

public class UiTest {
    @BeforeAll
    public static void setUp() {
        Configuration.browserSize = "1920x1080";
        Configuration.screenshots = false;
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
    }

    @AfterEach
    public void close() {
        Selenide.closeWebDriver();
    }
}