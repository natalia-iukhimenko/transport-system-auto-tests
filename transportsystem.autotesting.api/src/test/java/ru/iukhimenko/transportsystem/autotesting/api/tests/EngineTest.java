package ru.iukhimenko.transportsystem.autotesting.api.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.iukhimenko.transportsystem.autotesting.api.ApiTest;
import ru.iukhimenko.transportsystem.autotesting.api.service.EngineService;
import ru.iukhimenko.transportsystem.autotesting.core.model.Engine;
import ru.iukhimenko.transportsystem.autotesting.core.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.iukhimenko.transportsystem.autotesting.api.Configs.ADMIN_PASSWORD;
import static ru.iukhimenko.transportsystem.autotesting.api.Configs.ADMIN_USERNAME;

public class EngineTest extends ApiTest {
    final String PETROL = "PETROL", DIESEL = "DIESEL", GAS = "GAS", ELECTRIC = "ELECTRIC", HYBRID = "HYBRID";

    @Test
    @DisplayName("An engine with all specified values can be created")
    @Epic("Engines")
    @Feature("Add Engine")
    @Severity(SeverityLevel.BLOCKER)
    public void canCreateWithAllValuesPopulated() {
        Engine expectedEngine = new Engine("Test Engine", 1700, PETROL);
        EngineService engineService = new EngineService(new User(ADMIN_USERNAME, ADMIN_PASSWORD));
        Integer engineId = engineService.addEngine(expectedEngine);
        assertThat(engineId).as("Created engine has own id").isNotNull();
        expectedEngine.setId(engineId);
        Engine actualEngine = engineService.getEngine(engineId);
        assertThat(actualEngine)
                .as("Created engine stores all specified values")
                .isEqualToComparingFieldByField(expectedEngine);
    }

    @ParameterizedTest(name = "An engine with fuel type = {0} can be created")
    @ValueSource(strings = { PETROL, DIESEL, GAS, ELECTRIC, HYBRID })
    @Epic("Engines")
    @Feature("Add Engine")
    @Severity(SeverityLevel.CRITICAL)
    public void fuelTypeTest(String fuelType) {
        Engine testEngine = new Engine(fuelType + " engine", 1700, fuelType);
        EngineService engineService = new EngineService(new User(ADMIN_USERNAME, ADMIN_PASSWORD));
        Integer engineId = engineService.addEngine(testEngine);
        assertThat(engineId).as("Created engine has own id").isNotNull();
        Engine createdEngine = engineService.getEngine(engineId);
        assertThat(createdEngine.getFuel())
                .as("Created engine stores specified fuel")
                .isEqualTo(fuelType);
    }

    @ParameterizedTest(name = "An engine with volume = {0} can be created")
    @ValueSource(ints = { 1, 500, 10000 })
    @Epic("Engines")
    @Feature("Add Engine")
    @Severity(SeverityLevel.CRITICAL)
    public void volumeTest(Integer volume) {
        Engine testEngine = new Engine("Test Volume", volume, PETROL);
        EngineService engineService = new EngineService(new User(ADMIN_USERNAME, ADMIN_PASSWORD));
        Integer engineId = engineService.addEngine(testEngine);
        assertThat(engineId).as("Created engine has own id").isNotNull();
        Engine createdEngine = engineService.getEngine(engineId);
        assertThat(createdEngine.getVolume())
                .as("Created engine stores specified volume")
                .isEqualTo(volume);
    }

    @Test
    @DisplayName("An engine can be updated")
    @Epic("Engines")
    @Feature("Edit Engine")
    @Severity(SeverityLevel.BLOCKER)
    public void canUpdateEngineTest() {
        Engine sourceEngine = new Engine("Initial Engine", 1000, HYBRID);
        EngineService engineService = new EngineService(new User(ADMIN_USERNAME, ADMIN_PASSWORD));
        Integer engineId = engineService.addEngine(sourceEngine);
        Engine expectedUpdatedEngine = new Engine("Updated Engine", 2500, ELECTRIC);
        expectedUpdatedEngine.setId(engineId);
        engineService.updateEngine(expectedUpdatedEngine);
        Engine actualUpdatedEngine = engineService.getEngine(engineId);
        assertThat(actualUpdatedEngine)
                .as("All engine values have been updated")
                .isEqualToComparingFieldByField(expectedUpdatedEngine);
    }

    @Test
    @DisplayName("An unused engine can be deleted")
    @Epic("Engines")
    @Feature("Delete Engine")
    @Severity(SeverityLevel.BLOCKER)
    public void canDeleteUnusedEngineTest() {
        EngineService engineService = new EngineService(new User(ADMIN_USERNAME, ADMIN_PASSWORD));
        Integer engineId = engineService.addEngine(new Engine("To be removed", 1000, ELECTRIC));
        List<Engine> systemEngines = engineService.getAllEngines();
        assertThat(systemEngines).extracting("id").as("Created engine id exists in the list of engines").contains(engineId);
        engineService.deleteEngine(engineId);
        assertThat(engineService.getAllEngines())
                .extracting("id")
                .as("Deleted engine doesn't exist in the list of engines")
                .doesNotContain(engineId);
    }
}