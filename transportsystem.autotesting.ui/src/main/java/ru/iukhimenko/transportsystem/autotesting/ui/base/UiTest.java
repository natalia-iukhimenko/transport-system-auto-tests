package ru.iukhimenko.transportsystem.autotesting.ui.base;

import org.junit.jupiter.api.BeforeAll;
import ru.iukhimenko.transportsystem.autotesting.core.util.FileUtils;

import static ru.iukhimenko.transportsystem.autotesting.core.Configs.*;
import static ru.iukhimenko.transportsystem.autotesting.core.Configs.CONFIG_FILE_PATH;

public class UiTest {
    @BeforeAll
    public static void setUp() {
        BASE_URI = FileUtils.getValueFromProperties(CONFIG_FILE_PATH, "baseURI");
        ADMIN_USERNAME = FileUtils.getValueFromProperties(CONFIG_FILE_PATH, "adminUsername");
        ADMIN_PASSWORD = FileUtils.getValueFromProperties(CONFIG_FILE_PATH, "adminPassword");
    }
}
