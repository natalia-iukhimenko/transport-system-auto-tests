package ru.iukhimenko.transportsystem.autotesting.ui.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import org.junit.jupiter.api.BeforeAll;
import ru.iukhimenko.transportsystem.autotesting.core.util.FileUtils;
import static ru.iukhimenko.transportsystem.autotesting.core.Configs.*;
import static ru.iukhimenko.transportsystem.autotesting.core.Configs.CONFIG_FILE_PATH;
import io.qameta.allure.selenide.AllureSelenide;

public class UiTest {
    @BeforeAll
    public static void setUp() {
        BASE_URI = FileUtils.getValueFromProperties(CONFIG_FILE_PATH, "baseURL");
        ADMIN_USERNAME = FileUtils.getValueFromProperties(CONFIG_FILE_PATH, "adminUsername");
        ADMIN_PASSWORD = FileUtils.getValueFromProperties(CONFIG_FILE_PATH, "adminPassword");
        Configuration.browser = FileUtils.getValueFromProperties(CONFIG_FILE_PATH, "browser");
        Configuration.screenshots = false;
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
    }
}