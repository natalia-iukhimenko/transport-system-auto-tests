package ru.iukhimenko.transportsystem.autotesting.api.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.iukhimenko.transportsystem.autotesting.api.ApiTest;
import ru.iukhimenko.transportsystem.autotesting.api.http.Http;
import ru.iukhimenko.transportsystem.autotesting.api.objectpools.VehiclesPool;
import ru.iukhimenko.transportsystem.autotesting.api.service.EngineService;
import ru.iukhimenko.transportsystem.autotesting.api.service.TransportModelService;
import ru.iukhimenko.transportsystem.autotesting.api.service.VehicleService;
import ru.iukhimenko.transportsystem.autotesting.api.tags.ApiRegression;
import ru.iukhimenko.transportsystem.autotesting.api.tags.ApiSmoke;
import ru.iukhimenko.transportsystem.autotesting.core.model.TransportModel;
import ru.iukhimenko.transportsystem.autotesting.core.model.Vehicle;
import ru.iukhimenko.transportsystem.autotesting.core.util.TestDataManager;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("api_vehicle")
@ApiRegression
public class VehicleTest extends ApiTest {
    private final VehicleService vehicleService = new VehicleService();
    private Integer testEngineId;
    private Integer testTransportModelId;
    private final int POOL_SIZE = 1;
    private final VehiclesPool vehiclesPool = new VehiclesPool(POOL_SIZE);

    @BeforeEach
    public void createEngineAndTransportModel() {
        testEngineId = createTestEngine();
        testTransportModelId = createTestTransportModel();
    }

    @Test
    @ApiSmoke
    @DisplayName("A vehicle with only mandatory values can be created")
    @Epic("Transport")
    @Feature("Add Transport")
    @Severity(SeverityLevel.BLOCKER)
    public void canCreateVehicleWithMandatoryValuesTest() {
        Vehicle testVehicle = withMandatoryValues();
        Integer vehicleId = vehicleService.addVehicle(testVehicle);
        Vehicle createdVehicle = vehicleService.getVehicle(vehicleId);
        assertThat(createdVehicle)
                .as("Created vehicle stores only specified mandatory values")
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(testVehicle);
    }

    @Test
    @DisplayName("A vehicle with all specified values can be created")
    @Epic("Transport")
    @Feature("Add Transport")
    @Severity(SeverityLevel.CRITICAL)
    public void canCreateVehicleWithAllValuesTest() {
        Vehicle testVehicle = withAllValues();
        Integer vehicleId = vehicleService.addVehicle(testVehicle);
        Vehicle newVehicle = vehicleService.getVehicle(vehicleId);
        assertThat(newVehicle)
                .as("Created vehicle stores all specified values")
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(testVehicle);
    }

    @Test
    @DisplayName("Can not create a vehicle with not unique VIN - 401 (Bad Request)")
    @Epic("Transport")
    @Feature("Add Transport")
    @Severity(SeverityLevel.CRITICAL)
    public void canNotCreateVehicleWithNotUniqueVinTest() {
        Vehicle existingVehicle = vehiclesPool.get();
        Vehicle newVehicle = withVin(existingVehicle.getVin());
        int actualStatusCode = vehicleService.getAddVehicleResponseStatusCode(newVehicle);
        assertThat(actualStatusCode)
                .as("A vehicle with not unique VIN is not created - 01 (Bad Request) is expected")
                .isEqualTo(Http.BAD_REQUEST);
    }

    @Test
    @DisplayName("Can not create a vehicle with not unique number - 401 (Bad Request)")
    @Epic("Transport")
    @Feature("Add Transport")
    @Severity(SeverityLevel.CRITICAL)
    public void canNotCreateVehicleWithNotUniqueNumberTest() {
        Vehicle existingVehicle = vehiclesPool.get();
        Vehicle withNotUniqueNumber = withNumber(existingVehicle.getNumber());
        int actualStatusCode = vehicleService.getAddVehicleResponseStatusCode(withNotUniqueNumber);
        assertThat(actualStatusCode)
                .as("A vehicle with not unique number is not created - 401 (Bad Request)")
                .isEqualTo(Http.BAD_REQUEST);
    }

    private Vehicle withMandatoryValues() {
        return TestDataManager.getVehicleBuilderWithMandatoryValues(testEngineId, testTransportModelId).build();
    }

    private Vehicle withAllValues() {
        return TestDataManager.getVehicleBuilderWithMandatoryValues(testEngineId, testTransportModelId)
                .writeOffDate(LocalDate.now().toString())
                .build();
    }

    private Vehicle withNumber(String number) {
        return TestDataManager.getVehicleBuilderWithMandatoryValues(testEngineId, testTransportModelId)
                .number(number)
                .build();
    }

    private Vehicle withVin(String vin) {
        return TestDataManager.getVehicleBuilderWithMandatoryValues(testEngineId, testTransportModelId)
                .vin(vin)
                .build();
    }

    private Integer createTestEngine() {
        return new EngineService().addEngine(TestDataManager.getTestEngine());
    }

    private Integer createTestTransportModel() {
        TransportModel testModel = TestDataManager.getTestTransportModel();
        return new TransportModelService().addTransportModel(testModel);
    }
}