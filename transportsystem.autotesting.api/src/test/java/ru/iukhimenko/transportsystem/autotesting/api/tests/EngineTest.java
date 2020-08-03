package ru.iukhimenko.transportsystem.autotesting.api.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.iukhimenko.transportsystem.autotesting.api.ApiTest;
import ru.iukhimenko.transportsystem.autotesting.api.service.EngineService;
import ru.iukhimenko.transportsystem.autotesting.api.tags.ApiRegression;
import ru.iukhimenko.transportsystem.autotesting.api.tags.ApiSmoke;
import ru.iukhimenko.transportsystem.autotesting.core.model.Engine;
import ru.iukhimenko.transportsystem.autotesting.core.model.User;
import ru.iukhimenko.transportsystem.autotesting.core.util.TestDataManager;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.iukhimenko.transportsystem.autotesting.core.Configs.ADMIN_PASSWORD;
import static ru.iukhimenko.transportsystem.autotesting.core.Configs.ADMIN_USERNAME;

@Tag("api_engines")
@ApiRegression
public class EngineTest extends ApiTest {
    private User asAdmin = new User(ADMIN_USERNAME, ADMIN_PASSWORD);
    private EngineService engineService = new EngineService(asAdmin);

    @ApiSmoke
    @Epic("Engines")
    @Feature("Add Engine")
    @Severity(SeverityLevel.BLOCKER)
    @ParameterizedTest(name = "An engine with supported fuel types can be created")
    @MethodSource("ru.iukhimenko.transportsystem.autotesting.core.util.TestDataManager#engineFuelTypeProvider")
    public void canCreateWithAllValuesPopulated(String fuelType) {
        Engine expectedEngine = new Engine(fuelType + " engine", 1700, fuelType);
        Integer engineId = engineService.addEngine(expectedEngine);
        assertThat(engineId).as("Created engine has own id").isNotNull();

        Engine actualEngine = engineService.getEngine(engineId);
        assertThat(actualEngine)
                .as("Created engine stores all specified values")
                .isEqualToIgnoringGivenFields(expectedEngine, "id");
    }

    @ParameterizedTest(name = "An engine with volume = {0} can be created")
    @ValueSource(ints = { 1, 500, 10000 })
    @Epic("Engines")
    @Feature("Add Engine")
    @Severity(SeverityLevel.CRITICAL)
    public void volumeValueTest(Integer volume) {
        Engine testEngine = new Engine("Test Volume", volume, getTestFuel());
        Integer engineId = engineService.addEngine(testEngine);
        assertThat(engineId).as("Created engine has own id").isNotNull();

        Engine createdEngine = engineService.getEngine(engineId);
        assertThat(createdEngine.getVolume())
                .as("Created engine stores specified volume")
                .isEqualTo(volume);
    }

    @Test
    @Epic("Engines")
    @Feature("Add Engine")
    @Severity(SeverityLevel.NORMAL)
    public void unableToCreateEngineWithUnsupportedFuelType() {
        Engine testEngine = new Engine("Test Wrong Fuel", 1500, "TEST_TYPE");
        Integer engineId = engineService.addEngine(testEngine);
        assertThat(engineId).as("An engine with unsupported fuel type is not created").isNull();
    }

    @Test
    @ApiSmoke
    @DisplayName("An engine can be updated")
    @Epic("Engines")
    @Feature("Edit Engine")
    @Severity(SeverityLevel.BLOCKER)
    public void canUpdateEngineTest() {
        Engine sourceEngine = new Engine("Initial Engine", 1000, getTestFuel());
        Integer engineId = engineService.addEngine(sourceEngine);
        Engine expectedUpdatedEngine = new Engine("Updated Engine", 2500, getTestFuel());
        expectedUpdatedEngine.setId(engineId);

        engineService.updateEngine(expectedUpdatedEngine);
        Engine actualUpdatedEngine = engineService.getEngine(engineId);
        assertThat(actualUpdatedEngine)
                .as("All engine values have been updated")
                .isEqualToComparingFieldByField(expectedUpdatedEngine);
    }

    @Test
    @ApiSmoke
    @DisplayName("An unused engine can be deleted")
    @Epic("Engines")
    @Feature("Delete Engine")
    @Severity(SeverityLevel.BLOCKER)
    public void canDeleteUnusedEngineTest() {
        Integer engineId = engineService.addEngine(new Engine("To be removed", 1000, getTestFuel()));
        List<Engine> systemEngines = engineService.getAllEngines();
        assertThat(systemEngines).extracting("id").as("Created engine id exists in the list of engines").contains(engineId);

        engineService.deleteEngine(engineId);
        assertThat(engineService.getAllEngines())
                .extracting("id")
                .as("Deleted engine doesn't exist in the list of engines")
                .doesNotContain(engineId);
    }

    private String getTestFuel() {
        return TestDataManager.engineFuelTypeProvider().findAny().get();
    }
}