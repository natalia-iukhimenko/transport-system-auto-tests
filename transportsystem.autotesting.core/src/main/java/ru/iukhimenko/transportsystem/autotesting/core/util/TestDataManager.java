package ru.iukhimenko.transportsystem.autotesting.core.util;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import ru.iukhimenko.transportsystem.autotesting.core.model.Engine;
import ru.iukhimenko.transportsystem.autotesting.core.model.TransportModel;
import ru.iukhimenko.transportsystem.autotesting.core.model.Vehicle;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

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

    public static Stream<String> engineFuelTypeProvider() {
        return Stream.of( "PETROL", "DIESEL", "GAS", "ELECTRIC", "HYBRID");
    }

    public static String getAnySupportedFuelType() {
        return engineFuelTypeProvider().findAny().get();
    }

    public static Engine getTestEngine() {
        return new Engine("auto-gen", 1200, getAnySupportedFuelType());
    }

    public static Vehicle.VehicleBuilder getVehicleBuilderWithMandatoryValues(int engineId, int transportModelId) {
        return new Vehicle.VehicleBuilder()
                .setNumber(TestDataManager.getUniqueCarNumber())
                .setVin(TestDataManager.getUniqueVinNumber())
                .setProducedYear(2012)
                .setColor("black")
                .setEnginePower(1500)
                .setStartupDate("2014-10-12")
                .setEngineId(engineId)
                .setTransportModelId(transportModelId);
    }

    public static TransportModel.TransportModelBuilder getTestTransportModelBuilder() {
        return new TransportModel.TransportModelBuilder()
                .setName("CX-5")
                .setProducer("Mazda")
                .setWidth(1840)
                .setHeight(1675)
                .setLength(4550)
                .setMaxWeight(1617);
    }
}