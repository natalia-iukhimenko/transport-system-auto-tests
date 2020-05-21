package ru.iukhimenko.transportsystem.autotesting.api.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.iukhimenko.transportsystem.autotesting.api.ApiTest;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.iukhimenko.transportsystem.autotesting.api.Configs.ADMIN_USERNAME;
import static ru.iukhimenko.transportsystem.autotesting.api.Configs.ADMIN_PASSWORD;
import ru.iukhimenko.transportsystem.autotesting.api.service.TransportModelService;
import ru.iukhimenko.transportsystem.autotesting.core.model.TransportModel;
import ru.iukhimenko.transportsystem.autotesting.core.model.User;

public class TransportModelTest extends ApiTest {
    @Test
    @DisplayName("A transport model can be created")
    @Epic("Transport Models")
    @Feature("Add transport model")
    @Severity(SeverityLevel.BLOCKER)
    public void addTransportModelTest() {
        TransportModelService modelService = new TransportModelService(new User(ADMIN_USERNAME, ADMIN_PASSWORD));
        TransportModel modelToAdd = new TransportModel.TransportModelBuilder()
                .setName("Fluence")
                .setProducer("Renault")
                .setMaxWeight(1323)
                .setWidth(1809)
                .setHeight(1479)
                .setLength(4620)
                .build();
        Integer createdModelId = modelService.addTransportModel(modelToAdd);
        assertThat(createdModelId).as("Created transport model has its own id").isNotNull();
        TransportModel createdModel = modelService.getTransportModel(createdModelId);
        assertThat(createdModel)
                .as("Created transport model stores all specified values")
                .isEqualToIgnoringGivenFields(modelToAdd, "id");
    }
}
