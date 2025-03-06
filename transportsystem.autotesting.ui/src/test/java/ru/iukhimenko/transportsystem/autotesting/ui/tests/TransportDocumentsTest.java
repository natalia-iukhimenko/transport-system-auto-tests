package ru.iukhimenko.transportsystem.autotesting.ui.tests;

import com.github.javafaker.Faker;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import ru.iukhimenko.transportsystem.autotesting.api.objectpools.VehiclesPool;
import ru.iukhimenko.transportsystem.autotesting.api.service.TransportDocumentService;
import ru.iukhimenko.transportsystem.autotesting.core.util.TestDataManager;
import ru.iukhimenko.transportsystem.autotesting.ui.base.UiTest;
import ru.iukhimenko.transportsystem.autotesting.ui.elements.AddTransportDocumentForm;
import ru.iukhimenko.transportsystem.autotesting.ui.model.TransportDocumentInfo;
import ru.iukhimenko.transportsystem.autotesting.ui.pages.HomePage;
import ru.iukhimenko.transportsystem.autotesting.ui.pages.LogInPage;
import ru.iukhimenko.transportsystem.autotesting.ui.pages.TransportDocumentsPage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.iukhimenko.transportsystem.autotesting.core.TransportSystemConfig.TRANSPORT_SYSTEM_CONFIG;

@Tag("ui_transport_documents")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransportDocumentsTest extends UiTest {
    TransportDocumentsPage transportDocumentsPage;

    @BeforeEach
    public void goToTransportDocumentsPage() {
        open(TRANSPORT_SYSTEM_CONFIG.baseUrl(), LogInPage.class).logInWith(TRANSPORT_SYSTEM_CONFIG.adminUsername(), TRANSPORT_SYSTEM_CONFIG.adminPassword());
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

    @Test
    @DisplayName("Added transport document is saved")
    @Severity(SeverityLevel.CRITICAL)
    public void addedTransportDocumentIsSaved() {
        TransportDocumentInfo documentToCreate = generateTestTransportDocument();
        transportDocumentsPage.clickAddButton()
                .fillForm(documentToCreate)
                .clickOnSubmitButton();
        TransportDocumentService transportDocumentService = new TransportDocumentService();
        assertThat(transportDocumentService.getTransportDocuments())
                .as("List of existing transport documents contains the added document")
                .filteredOn(document -> document.getDocumentType().equals(documentToCreate.getDocumentType()))
                .isNotEmpty();
    }

    @Test
    @DisplayName("Added transport document is displayed in the table")
    @Severity(SeverityLevel.CRITICAL)
    public void addedTransportDocumentIsDisplayedInTable() {
        TransportDocumentInfo documentToCreate = generateTestTransportDocument();
        List<TransportDocumentInfo> transportDocumentList = transportDocumentsPage.clickAddButton()
                .fillForm(documentToCreate)
                .clickOnSubmitButton()
                .getTransportDocumentsFromTable();
        assertThat(transportDocumentList)
                .as("Added transport document is displayed in the table")
                .filteredOn(document -> document.getDocumentType().equals(documentToCreate.getDocumentType()))
                .isNotEmpty();
    }

    @Test
    @DisplayName("'Add transport document' form is cleared after closing")
    @Severity(SeverityLevel.NORMAL)
    public void formIsClearedAfterClosing() {
        TransportDocumentInfo documentToCreate = generateTestTransportDocument();
        AddTransportDocumentForm form = transportDocumentsPage.clickAddButton()
                .fillForm(documentToCreate)
                .close()
                .clickAddButton();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(form.isDocumentTypeFieldEmpty()).as("'Document Type' field is empty").isTrue();
        softly.assertThat(form.isSeriesFieldEmpty()).as("'Series' field is empty").isTrue();
        softly.assertThat(form.isNumberFieldEmpty()).as("'Number' field is empty").isTrue();
        softly.assertThat(form.isIssuedByFieldEmpty()).as("'Issued by' field is empty").isTrue();
        softly.assertThat(form.isIssueDateFieldEmpty()).as("'Issue Date' field is empty").isTrue();
        softly.assertThat(form.isValidToFieldEmpty()).as("'Valid To' field is empty").isTrue();
        softly.assertThat(form.isVehicleSelected()).as("Vehicle is selected in dropdown").isFalse();
        softly.assertAll();
    }

    private TransportDocumentInfo generateTestTransportDocument() {
        LocalDate issuedDate = LocalDate.now();
        DateTimeFormatter template = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String series = TestDataManager.getUniqueNumericCombination("###");
        String number = TestDataManager.getUniqueNumericCombination("#########");
        String docType = "test_" + series + "_" + number;

        TransportDocumentInfo transportDocumentInfo = new TransportDocumentInfo();
        transportDocumentInfo.setDocumentType(docType);
        transportDocumentInfo.setSeries(series);
        transportDocumentInfo.setNumber(number);
        transportDocumentInfo.setIssuedDate(issuedDate.format(template));
        transportDocumentInfo.setIssuedBy(new Faker().company().name());
        transportDocumentInfo.setExpireDate(issuedDate.plusYears(5).format(template));
        transportDocumentInfo.setTransportNumber(new VehiclesPool().get().getNumber());
        return transportDocumentInfo;
    }
}