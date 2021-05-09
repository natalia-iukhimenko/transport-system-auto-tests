package ru.iukhimenko.transportsystem.autotesting.api;

import kong.unirest.Unirest;
import org.junit.jupiter.api.AfterAll;

public class ApiTest {
    @AfterAll
    public static void tearDown() {
        Unirest.shutDown();
    }
}
