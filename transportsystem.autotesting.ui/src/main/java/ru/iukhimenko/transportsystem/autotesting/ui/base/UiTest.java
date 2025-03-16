package ru.iukhimenko.transportsystem.autotesting.ui.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static ru.iukhimenko.transportsystem.autotesting.core.TransportSystemConfig.TRANSPORT_SYSTEM_CONFIG;

public class UiTest {
    @BeforeAll
    public static void setUp() {
        //  Configuration.remote = "http://localhost:4444/wd/hub"; // for local
        Configuration.remote = "http://selenoid:4444/wd/hub"; // for remote
        Configuration.browser = TRANSPORT_SYSTEM_CONFIG.browser();
        Configuration.browserVersion = TRANSPORT_SYSTEM_CONFIG.browserVersion();
        Configuration.browserSize = "1920x1080";
        Configuration.screenshots = false;
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
    }

    @AfterEach
    public void close() {
        Selenide.closeWebDriver();
    }
}