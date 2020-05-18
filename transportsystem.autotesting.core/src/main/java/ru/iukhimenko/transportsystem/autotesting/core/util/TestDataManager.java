package ru.iukhimenko.transportsystem.autotesting.core.util;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import java.util.Locale;

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

    public static String getCarNumber() {
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("ru-RU"), new RandomService());
        String carNumber = fakeValuesService.regexify("[АВЕКМНОРСТУХ]\\d{3}[АВЕКМНОРСТУХ]{2}\\d{2,3}");
        return carNumber;
    }

    public static String getVinNumber() {
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-GB"), new RandomService());
        String vinNumber = fakeValuesService.regexify("[ABCDEFGHJKLMNPRSTUVWXYZ0-9]{16}");
        return vinNumber;
    }
}
