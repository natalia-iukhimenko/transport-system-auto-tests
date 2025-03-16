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
        if (TRANSPORT_SYSTEM_CONFIG.environment().equals("remote")) {
            Configuration.remote = "http://selenoid:4444/wd/hub";
        }
        Configuration.headless = TRANSPORT_SYSTEM_CONFIG.isHeadless().equals("true");
        Configuration.browser = TRANSPORT_SYSTEM_CONFIG.browser();
        Configuration.browserSize = "1920x1080";
        Configuration.screenshots = false;
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
    }

    @AfterEach
    public void close() {
        Selenide.closeWebDriver();
    }
}