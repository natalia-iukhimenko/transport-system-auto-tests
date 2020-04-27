package ru.iukhimenko.transportsystem.autotesting.core.util;

import com.github.javafaker.Faker;

public class TestDataManager {
    public static String getValidPassword() {
        int minimalLength = 6;
        return new Faker().number().digits(minimalLength);
    }

    public static String getValidUsername() {
        return new Faker().name().username();
    }
}
