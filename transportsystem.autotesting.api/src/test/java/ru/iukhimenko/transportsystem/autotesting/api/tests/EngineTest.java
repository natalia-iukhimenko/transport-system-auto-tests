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
import ru.iukhimenko.transportsystem.autotesting.api.http.Http;
import ru.iukhimenko.transportsystem.autotesting.api.service.AuthService;
import ru.iukhimenko.transportsystem.autotesting.api.service.EngineService;
import ru.iukhimenko.transportsystem.autotesting.api.tags.ApiRegression;
import ru.iukhimenko.transportsystem.autotesting.api.tags.ApiSmoke;
import ru.iukhimenko.transportsystem.autotesting.core.model.Engine;
import ru.iukhimenko.transportsystem.autotesting.core.model.User;
import ru.iukhimenko.transportsystem.autotesting.core.util.TestDataManager;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.iukhimenko.transportsystem.autotesting.core.TransportSystemConfig.TRANSPORT_SYSTEM_CONFIG;

@Tag("api_engines")
@ApiRegression
public class EngineTest extends ApiTest {
    private final User asAdmin = new User(TRANSPORT_SYSTEM_CONFIG.adminUsername(), TRANSPORT_SYSTEM_CONFIG.adminPassword());
    private final EngineService engineService = new EngineService(asAdmin);
    private final Integer ENGINE_VOLUME = 1700;

    @ApiSmoke
    @DisplayName("Status code = 201 (Created) when admin user adds an engine")
    @Epic("Engines")
    @Feature("Add Engine")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void statusCodeIsCreated() {
        Engine engine = new Engine("Test engine", ENGINE_VOLUME, getTestFuel());
        int actualStatusCode = engineService.getAddEngineResponseStatusCode(engine);
        assertThat(actualStatusCode)
                .as("Status code = 201 (Created) when admin user adds an engine")
                .isEqualTo(Http.CREATED);
    }

    @ApiSmoke
    @DisplayName("Status code = 403 (Forbidden) when newly registered user adds an engine")
    @Epic("Engines")
    @Feature("Add Engine")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void statusCodeIsForbidden() {
        User testUser = new User(TestDataManager.getValidUsername(), TestDataManager.getValidPassword());
        new AuthService().registerUser(testUser);

        Engine engine = new Engine("Test engine", ENGINE_VOLUME, getTestFuel());
        int actualStatusCode = new EngineService(testUser).getAddEngineResponseStatusCode(engine);
        assertThat(actualStatusCode)
                .as("Status code = 403 (Forbidden) when newly registered user adds an engine")
                .isEqualTo(Http.FORBIDDEN);
    }

    @ApiSmoke
    @Epic("Engines")
    @Feature("Add Engine")
    @Severity(SeverityLevel.BLOCKER)
    @ParameterizedTest(name = "An engine with supported fuel type can be created")
    @MethodSource("ru.iukhimenko.transportsystem.autotesting.core.util.TestDataManager#engineFuelTypeProvider")
    public void canCreateWithAllValuesPopulated(String fuelType) {
        final String engineName = fuelType + " engine";
        Engine expectedEngine = new Engine(engineName, ENGINE_VOLUME, fuelType);
        Integer createdEngineId = engineService.addEngine(expectedEngine);
        Engine actualEngine = engineService.getEngine(createdEngineId);
        assertThat(actualEngine)
                .as("Created engine stores all specified values")
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedEngine);
    }

    @ParameterizedTest(name = "An engine with volume = {0} can be created")
    @ValueSource(ints = { 1, 500, 10000 })
    @Epic("Engines")
    @Feature("Add Engine")
    @Severity(SeverityLevel.CRITICAL)
    public void volumeValueTest(Integer volume) {
        Engine testEngine = new Engine("Test Volume", volume, getTestFuel());
        Integer engineId = engineService.addEngine(testEngine);
        Engine createdEngine = engineService.getEngine(engineId);
        assertThat(createdEngine.getVolume())
                .as("Created engine stores specified volume")
                .isEqualTo(volume);
    }

    @Test
    @DisplayName("Status code = 400 (Bad Request) is returned when adding an engine with wrong fuel type")
    @Epic("Engines")
    @Feature("Add Engine")
    @Severity(SeverityLevel.NORMAL)
    public void unableToCreateEngineWithUnsupportedFuelType() {
        Engine testEngine = new Engine("Test Wrong Fuel", ENGINE_VOLUME, "TEST_TYPE");
        int actualStatusCode = engineService.getAddEngineResponseStatusCode(testEngine);
        assertThat(actualStatusCode)
                .as("Status code = 400 (Bad Request) is returned when adding an engine with wrong fuel type")
                .isEqualTo(Http.BAD_REQUEST);
    }

    @Test
    @ApiSmoke
    @DisplayName("An engine can be updated")
    @Epic("Engines")
    @Feature("Edit Engine")
    @Severity(SeverityLevel.BLOCKER)
    public void canUpdateEngineTest() {
        Engine sourceEngine = new Engine("Initial Engine", ENGINE_VOLUME, getTestFuel());
        Integer engineId = engineService.addEngine(sourceEngine);
        Engine expectedUpdatedEngine = new Engine("Updated Engine", 2500, getTestFuel());
        expectedUpdatedEngine.setId(engineId);

        engineService.updateEngine(expectedUpdatedEngine);
        Engine actualUpdatedEngine = engineService.getEngine(engineId);
        assertThat(actualUpdatedEngine)
                .as("All engine values have been updated")
                .usingRecursiveComparison()
                .isEqualTo(expectedUpdatedEngine);
    }

    @Test
    @ApiSmoke
    @DisplayName("An unused engine can be deleted")
    @Epic("Engines")
    @Feature("Delete Engine")
    @Severity(SeverityLevel.BLOCKER)
    public void canDeleteUnusedEngineTest() {
        Integer engineId = engineService.addEngine(new Engine("To be removed", ENGINE_VOLUME, getTestFuel()));
        assertThat(engineService.getAllEngines())
                .extracting(Engine::getId)
                .as("Created engine id exists in the list of engines")
                .contains(engineId);

        engineService.deleteEngine(engineId);
        assertThat(engineService.getAllEngines())
                .extracting(Engine::getId)
                .as("Deleted engine doesn't exist in the list of engines")
                .doesNotContain(engineId);
    }

    private String getTestFuel() {
        return TestDataManager.engineFuelTypeProvider().findAny().orElseThrow(() -> new NoSuchElementException("Test fuel wasn't found"));
    }
}