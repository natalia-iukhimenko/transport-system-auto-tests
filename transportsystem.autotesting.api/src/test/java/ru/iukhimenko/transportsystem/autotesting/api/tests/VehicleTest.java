package ru.iukhimenko.transportsystem.autotesting.api.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.iukhimenko.transportsystem.autotesting.api.ApiTest;
import ru.iukhimenko.transportsystem.autotesting.api.service.EngineService;
import ru.iukhimenko.transportsystem.autotesting.api.service.VehicleService;
import ru.iukhimenko.transportsystem.autotesting.core.model.Engine;
import ru.iukhimenko.transportsystem.autotesting.core.model.User;
import ru.iukhimenko.transportsystem.autotesting.core.model.Vehicle;
import ru.iukhimenko.transportsystem.autotesting.core.util.TestDataManager;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.iukhimenko.transportsystem.autotesting.api.Configs.ADMIN_PASSWORD;
import static ru.iukhimenko.transportsystem.autotesting.api.Configs.ADMIN_USERNAME;

public class VehicleTest extends ApiTest {
    public Integer testEngineId;
    public Integer testTransportModelId;

    @BeforeEach
    public void createEngineAndTransportModel() {
        EngineService testEngine = new EngineService(new User(ADMIN_USERNAME, ADMIN_PASSWORD));
        testEngineId = testEngine.addEngine(new Engine("Auto Engine", 1500, "GAS"));
        assertThat(testEngineId).isNotNull();
        testTransportModelId = 2; // TODO: replace with a created one
    }

    @Test
    @DisplayName("A vehicle with only mandatory values specified can be created")
    @Epic("Transport")
    @Feature("Add Transport")
    @Severity(SeverityLevel.BLOCKER)
    public void canCreateVehicleWithMandatoryValuesTest() {
        Vehicle testVehicle = new Vehicle.VehicleBuilder()
                .setNumber(TestDataManager.getUniqueCarNumber())
                .setVin(TestDataManager.getUniqueVinNumber())
                .setProducedYear(2012)
                .setColor("Чёрный")
                .setEnginePower(1500)
                .setStartupDate("2014-10-12")
                .setEngineId(testEngineId)
                .setTransportModelId(testTransportModelId)
                .build();
        VehicleService vehicleService = new VehicleService(new User(ADMIN_USERNAME, ADMIN_PASSWORD));
        Integer vehicleId = vehicleService.addVehicle(testVehicle);
        assertThat(vehicleId).as("Created vehicle has own id").isNotNull();
        Vehicle createdVehicle = vehicleService.getVehicle(vehicleId);
        assertThat(createdVehicle)
                .as("Created vehicle stores all specified values")
                .isEqualToIgnoringGivenFields(testVehicle, "id");
    }

    @Test
    @DisplayName("A vehicle with all specified values can be created")
    @Epic("Transport")
    @Feature("Add Transport")
    @Severity(SeverityLevel.BLOCKER)
    public void canCreateVehicleWithAllValuesTest() {
        Vehicle testVehicle = new Vehicle.VehicleBuilder()
                .setNumber(TestDataManager.getUniqueCarNumber())
                .setVin(TestDataManager.getUniqueVinNumber())
                .setProducedYear(2015)
                .setColor("Чёрный")
                .setEnginePower(1700)
                .setStartupDate("2017-03-22")
                .setWriteOffDate("2019-07-14")
                .setEngineId(testEngineId)
                .setTransportModelId(testTransportModelId)
                .build();
        VehicleService vehicleService = new VehicleService(new User(ADMIN_USERNAME, ADMIN_PASSWORD));
        Integer vehicleId = vehicleService.addVehicle(testVehicle);
        assertThat(vehicleId).as("Created vehicle has own id").isNotNull();
        Vehicle createdVehicle = vehicleService.getVehicle(vehicleId);
        assertThat(createdVehicle)
                .as("Created vehicle stores all specified values")
                .isEqualToIgnoringGivenFields(testVehicle, "id");
    }

    @Test
    @DisplayName("Can not create a vehicle with not unique VIN")
    @Epic("Transport")
    @Feature("Add Transport")
    @Severity(SeverityLevel.CRITICAL)
    public void canNotCreateVehicleWithNotUniqueVinTest() {
        Vehicle.VehicleBuilder vehicleBuilder = new Vehicle.VehicleBuilder()
                .setNumber(TestDataManager.getUniqueCarNumber())
                .setVin(TestDataManager.getUniqueVinNumber())
                .setProducedYear(2012)
                .setColor("Чёрный")
                .setEnginePower(1500)
                .setStartupDate("2014-10-12")
                .setEngineId(testEngineId)
                .setTransportModelId(testTransportModelId);
        VehicleService vehicleService = new VehicleService(new User(ADMIN_USERNAME, ADMIN_PASSWORD));
        Integer vehicleId = vehicleService.addVehicle(vehicleBuilder.build());
        assertThat(vehicleId).as("Created vehicle has own id").isNotNull();
        Vehicle withNotUniqueVin = vehicleBuilder.setNumber(TestDataManager.getUniqueCarNumber()).build();
        Integer newVehicleId = vehicleService.addVehicle(withNotUniqueVin);
        assertThat(newVehicleId).as("A vehicle with not unique VIN is not created").isNull();
    }

    @Test
    @DisplayName("Can not create a vehicle with not unique number")
    @Epic("Transport")
    @Feature("Add Transport")
    @Severity(SeverityLevel.CRITICAL)
    public void canNotCreateVehicleWithNotUniqueNumberTest() {
        Vehicle.VehicleBuilder vehicleBuilder = new Vehicle.VehicleBuilder()
                .setNumber(TestDataManager.getUniqueCarNumber())
                .setVin(TestDataManager.getUniqueVinNumber())
                .setProducedYear(2012)
                .setColor("Чёрный")
                .setEnginePower(1500)
                .setStartupDate("2014-10-12")
                .setEngineId(testEngineId)
                .setTransportModelId(testTransportModelId);
        VehicleService vehicleService = new VehicleService(new User(ADMIN_USERNAME, ADMIN_PASSWORD));
        Integer vehicleId = vehicleService.addVehicle(vehicleBuilder.build());
        assertThat(vehicleId).as("Created vehicle has own id").isNotNull();
        Vehicle withNotUniqueNumber = vehicleBuilder.setVin(TestDataManager.getUniqueVinNumber()).build();
        Integer newVehicleId = vehicleService.addVehicle(withNotUniqueNumber);
        assertThat(newVehicleId).as("A vehicle with not unique number is not created").isNull();
    }
}