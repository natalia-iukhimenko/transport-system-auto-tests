package ru.iukhimenko.transportsystem.autotesting.core.util;

import com.github.javafaker.Faker;

public class TestDataManager {
    public static String getValidPassword() {
        int minimalLength = 6;
        return new Faker().number().digits(minimalLength);
    }

    // A valid username can contain only latin letters, underscores and points
    public static String getValidUsername() {
        String validUsername = "_" + new Faker().name().username();
        return validUsername;
    }
}
