package ru.iukhimenko.transportsystem.autotesting.api;
import kong.unirest.Unirest;
import kong.unirest.jackson.JacksonObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import ru.iukhimenko.transportsystem.autotesting.core.util.FileUtils;
import static ru.iukhimenko.transportsystem.autotesting.core.Configs.*;

public class ApiTest {
    @BeforeAll
    public static void setUp(){
        BASE_URI = FileUtils.getValueFromProperties(CONFIG_FILE_PATH, "baseURI");
        ADMIN_USERNAME = FileUtils.getValueFromProperties(CONFIG_FILE_PATH, "adminUsername");
        ADMIN_PASSWORD = FileUtils.getValueFromProperties(CONFIG_FILE_PATH, "adminPassword");
        configureUnirest();
    }

    private static void configureUnirest() {
        Unirest.config()
                .addDefaultHeader("Content-Type", "application/json")
                .setObjectMapper(new JacksonObjectMapper());
    }

    @AfterAll
    public static void tearDown() {
        Unirest.shutDown();
    }
}
