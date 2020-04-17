package ru.iukhimenko.transportsystem.autotesting.api;

import kong.unirest.Unirest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import ru.iukhimenko.transportsystem.autotesting.core.util.FileUtils;

import java.io.InputStream;

public class ApiTest {
    @BeforeAll
    public static void setUp() {
        Unirest.config().addDefaultHeader("Content-Type", "application/json");
        AppEndpoints.BASE_URI = FileUtils.getValueFromProperties("src/main/resources/config.properties", "baseURI");
    }

    @AfterAll
    public static void tearDown() {
        Unirest.shutDown();
    }
}
