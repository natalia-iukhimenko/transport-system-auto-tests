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
import ru.iukhimenko.transportsystem.autotesting.api.objectpools.VehiclesPool;
import ru.iukhimenko.transportsystem.autotesting.api.service.EngineService;
import ru.iukhimenko.transportsystem.autotesting.api.service.TransportModelService;
import ru.iukhimenko.transportsystem.autotesting.api.service.VehicleService;
import ru.iukhimenko.transportsystem.autotesting.api.tags.ApiRegression;
import ru.iukhimenko.transportsystem.autotesting.api.tags.ApiSmoke;
import ru.iukhimenko.transportsystem.autotesting.core.model.TransportModel;
import ru.iukhimenko.transportsystem.autotesting.core.model.Vehicle;
import ru.iukhimenko.transportsystem.autotesting.core.util.TestDataManager;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("api_vehicle")
@ApiRegression
public class VehicleTest extends ApiTest {
    private VehicleService vehicleService = new VehicleService();
    private Integer testEngineId;
    private Integer testTransportModelId;
    private final int poolSize = 1;
    private VehiclesPool vehiclesPool = new VehiclesPool(poolSize);

    @BeforeEach
    public void createEngineAndTransportModel() {
        testEngineId = createTestEngine();
        testTransportModelId = createTestTransportModel();
    }

    @Test
    @ApiSmoke
    @DisplayName("A vehicle with only mandatory values specified can be created")
    @Epic("Transport")
    @Feature("Add Transport")
    @Severity(SeverityLevel.BLOCKER)
    public void canCreateVehicleWithMandatoryValuesTest() {
        Vehicle testVehicle = withMandatoryValues();
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
    @Severity(SeverityLevel.CRITICAL)
    public void canCreateVehicleWithAllValuesTest() {
        Vehicle testVehicle = withAllValues();
        Integer vehicleId = vehicleService.addVehicle(testVehicle);
        assertThat(vehicleId).as("Created vehicle has own id").isNotNull();
        Vehicle newVehicle = vehicleService.getVehicle(vehicleId);
        assertThat(newVehicle)
                .as("Created vehicle stores all specified values")
                .isEqualToIgnoringGivenFields(testVehicle, "id");
    }

    @Test
    @DisplayName("Can not create a vehicle with not unique VIN")
    @Epic("Transport")
    @Feature("Add Transport")
    @Severity(SeverityLevel.CRITICAL)
    public void canNotCreateVehicleWithNotUniqueVinTest() {
        Vehicle existingVehicle = vehiclesPool.get();
        Vehicle newVehicle = withVin(existingVehicle.getVin());
        Integer newVehicleId = vehicleService.addVehicle(newVehicle);
        assertThat(newVehicleId).as("A vehicle with not unique VIN is not created, id should be null").isNull();
    }

    @Test
    @DisplayName("Can not create a vehicle with not unique number")
    @Epic("Transport")
    @Feature("Add Transport")
    @Severity(SeverityLevel.CRITICAL)
    public void canNotCreateVehicleWithNotUniqueNumberTest() {
        Vehicle existingVehicle = vehiclesPool.get();
        Vehicle withNotUniqueNumber = withNumber(existingVehicle.getNumber());
        Integer newVehicleId = vehicleService.addVehicle(withNotUniqueNumber);
        assertThat(newVehicleId).as("A vehicle with not unique number is not created, id should be null").isNull();
    }

    private Vehicle withMandatoryValues() {
        return TestDataManager.getVehicleBuilderWithMandatoryValues(testEngineId, testTransportModelId).build();
    }

    private Vehicle withAllValues() {
        return TestDataManager.getVehicleBuilderWithMandatoryValues(testEngineId, testTransportModelId)
                .writeOffDate("2020-01-12")
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

    private int createTestEngine() {
        EngineService engineService = new EngineService();
        int id = engineService.addEngine(TestDataManager.getTestEngine());
        assertThat(id).isNotNull();
        return id;
    }

    private int createTestTransportModel() {
        TransportModelService modelService = new TransportModelService();
        TransportModel testModel = TestDataManager.getTestTransportModel();
        int id = modelService.addTransportModel(testModel);
        assertThat(id).isNotNull();
        return id;
    }
}