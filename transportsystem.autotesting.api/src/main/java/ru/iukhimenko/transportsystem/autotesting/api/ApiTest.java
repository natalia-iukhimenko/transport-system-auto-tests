package ru.iukhimenko.transportsystem.autotesting.api;
import kong.unirest.Unirest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import ru.iukhimenko.transportsystem.autotesting.core.util.FileUtils;
import static ru.iukhimenko.transportsystem.autotesting.core.Configs.*;

public class ApiTest {
    @BeforeAll
    public static void setUp() {
        ADMIN_USERNAME = FileUtils.getValueFromProperties(CONFIG_FILE_PATH, "adminUsername"); // убрать
        ADMIN_PASSWORD = FileUtils.getValueFromProperties(CONFIG_FILE_PATH, "adminPassword"); // убрать
    }

    @AfterAll
    public static void tearDown() {
        Unirest.shutDown();
    }
}
