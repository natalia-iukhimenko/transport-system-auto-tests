package ru.iukhimenko.transportsystem.autotesting.api;

import kong.unirest.Unirest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import ru.iukhimenko.transportsystem.autotesting.core.util.FileUtils;
import static ru.iukhimenko.transportsystem.autotesting.api.Configs.*;

public class ApiTest {
    @BeforeAll
    public static void setUp() {
        Unirest.config().addDefaultHeader("Content-Type", "application/json");
        BASE_URI = FileUtils.getValueFromProperties(CONFIG_FILE_PATH, "baseURI");
        ADMIN_USERNAME = FileUtils.getValueFromProperties(CONFIG_FILE_PATH, "adminUsername");
        ADMIN_PASSWORD = FileUtils.getValueFromProperties(CONFIG_FILE_PATH, "adminPassword");
    }

    @AfterAll
    public static void tearDown() {
        Unirest.shutDown();
    }
}