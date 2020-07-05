package ru.iukhimenko.transportsystem.autotesting.core.util;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import ru.iukhimenko.transportsystem.autotesting.core.model.TransportModel;
import java.io.File;
import java.util.List;
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

    public static String getUniqueCarNumber() {
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("ru-RU"), new RandomService());
        String carNumber = fakeValuesService.regexify("[АВЕКМНОРСТУХ]\\d{3}[АВЕКМНОРСТУХ]{2}\\d{2,3}");
        return carNumber;
    }

    public static String getUniqueVinNumber() {
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-GB"), new RandomService());
        String vinNumber = fakeValuesService.regexify("[ABCDEFGHJKLMNPRSTUVWXYZ0-9]{16}");
        return vinNumber;
    }

    public static String getUniqueNumericCombination(String numberString) {
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-GB"), new RandomService());
        String number = fakeValuesService.numerify(numberString);
        return number;
    }

    public static List<TransportModel> getTransportModelsFromJson() {
        File file = new File("src/test/resources/test_data/transport_models.json");
        return FileUtils.readJsonObjectsFromFile(file.getAbsolutePath(), TransportModel.class);
    }
}