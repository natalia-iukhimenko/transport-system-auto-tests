package ru.iukhimenko.transportsystem.autotesting.ui.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.iukhimenko.transportsystem.autotesting.core.util.FileUtils;

import static ru.iukhimenko.transportsystem.autotesting.core.Configs.*;

public class UiTest {
    @BeforeAll
    public static void setUp() {
        Configuration.remote = "http://localhost:4444/wd/hub";
        Configuration.browser = FileUtils.getValueFromProperties(CONFIG_FILE_PATH, "browser");
        Configuration.browserSize = "1920x1080";
        Configuration.screenshots = false;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
        BASE_URI = FileUtils.getValueFromProperties(CONFIG_FILE_PATH, "baseURL");
        ADMIN_USERNAME = FileUtils.getValueFromProperties(CONFIG_FILE_PATH, "adminUsername");
        ADMIN_PASSWORD = FileUtils.getValueFromProperties(CONFIG_FILE_PATH, "adminPassword");
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
    }
}