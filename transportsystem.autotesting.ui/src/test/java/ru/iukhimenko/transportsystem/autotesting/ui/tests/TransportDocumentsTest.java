package ru.iukhimenko.transportsystem.autotesting.ui.tests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import ru.iukhimenko.transportsystem.autotesting.api.objectpools.TransportDocumentsPool;
import ru.iukhimenko.transportsystem.autotesting.ui.base.UiTest;
import ru.iukhimenko.transportsystem.autotesting.ui.elements.AddTransportDocumentForm;
import ru.iukhimenko.transportsystem.autotesting.ui.pages.HomePage;
import ru.iukhimenko.transportsystem.autotesting.ui.pages.LogInPage;
import ru.iukhimenko.transportsystem.autotesting.ui.pages.TransportDocumentsPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.iukhimenko.transportsystem.autotesting.core.TransportSystemConfig.TRANSPORT_SYSTEM_CONFIG;

@Tag("ui_transport_documents")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransportDocumentsTest extends UiTest {
    TransportDocumentsPage transportDocumentsPage;
    TransportDocumentsPool pool;

    @BeforeAll
    public void createTestDataAndLogIn() {
        pool = new TransportDocumentsPool(4);
        open(TRANSPORT_SYSTEM_CONFIG.baseUrl(), LogInPage.class).logInWith(TRANSPORT_SYSTEM_CONFIG.adminUsername(), TRANSPORT_SYSTEM_CONFIG.adminPassword());
    }

    @BeforeEach
    public void goToTransportDocumentsPage() {
        transportDocumentsPage = new HomePage().getMenu()
                .expandVehicleMenuItem()
                .selectTransportDocuments();
    }

    @Test
    @DisplayName("Check mandatory fields on 'Add Transport Document' form")
    @Severity(SeverityLevel.CRITICAL)
    public void mandatoryFieldsOnAddDocumentForm() {
        AddTransportDocumentForm form = transportDocumentsPage.clickAddButton();
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(form.isDocumentTypeFieldMandatory()).as("'Document Type' field is mandatory").isTrue();
        softly.assertThat(form.isSeriesFieldMandatory()).as("'Series' field is mandatory").isTrue();
        softly.assertThat(form.isNumberFieldMandatory()).as("'Number' field is mandatory").isTrue();
        softly.assertThat(form.isIssuedByMandatory()).as("'Issued by' field is mandatory").isTrue();
        softly.assertThat(form.isIssueDateMandatory()).as("'Issue Date' field is mandatory").isTrue();
        softly.assertThat(form.isVehicleDropdownMandatory()).as("'Vehicle' dropdown is mandatory").isTrue();
        softly.assertAll();
    }
}