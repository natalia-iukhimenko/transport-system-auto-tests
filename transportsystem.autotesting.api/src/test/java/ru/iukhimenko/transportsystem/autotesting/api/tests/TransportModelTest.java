package ru.iukhimenko.transportsystem.autotesting.api.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import ru.iukhimenko.transportsystem.autotesting.api.ApiTest;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.iukhimenko.transportsystem.autotesting.core.Configs.ADMIN_USERNAME;
import static ru.iukhimenko.transportsystem.autotesting.core.Configs.ADMIN_PASSWORD;
import ru.iukhimenko.transportsystem.autotesting.api.service.TransportModelService;
import ru.iukhimenko.transportsystem.autotesting.api.tags.ApiRegression;
import ru.iukhimenko.transportsystem.autotesting.api.tags.ApiSmoke;
import ru.iukhimenko.transportsystem.autotesting.core.model.TransportModel;
import ru.iukhimenko.transportsystem.autotesting.core.model.User;

@Tag("api_transport_model")
@ApiRegression
public class TransportModelTest extends ApiTest {
    private TransportModelService modelService = new TransportModelService(new User(ADMIN_USERNAME, ADMIN_PASSWORD));

    @Test
    @ApiSmoke
    @DisplayName("A transport model can be created")
    @Epic("Transport Models")
    @Feature("Add transport model")
    @Severity(SeverityLevel.BLOCKER)
    public void addTransportModelTest() {
        TransportModel modelToAdd = TransportModel.builder()
                .name("Fluence")
                .producer("Renault")
                .maxWeight(1323)
                .width(1809)
                .height(1479)
                .length(4620)
                .build();
        Integer createdModelId = modelService.addTransportModel(modelToAdd);
        assertThat(createdModelId).as("Created transport model has its own id").isNotNull();

        TransportModel createdModel = modelService.getTransportModel(createdModelId);
        assertThat(createdModel)
                .as("Created transport model stores all specified values")
                .isEqualToIgnoringGivenFields(modelToAdd, "id");
    }
}