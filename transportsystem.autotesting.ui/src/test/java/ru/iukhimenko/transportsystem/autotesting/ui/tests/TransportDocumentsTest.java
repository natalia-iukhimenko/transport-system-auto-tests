package ru.iukhimenko.transportsystem.autotesting.ui.tests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.iukhimenko.transportsystem.autotesting.api.objectpools.TransportDocumentsPool;
import ru.iukhimenko.transportsystem.autotesting.ui.base.UiTest;
import ru.iukhimenko.transportsystem.autotesting.ui.elements.AddTransportDocumentForm;
import ru.iukhimenko.transportsystem.autotesting.ui.elements.TransportDocumentsTable;
import ru.iukhimenko.transportsystem.autotesting.ui.pages.HomePage;
import ru.iukhimenko.transportsystem.autotesting.ui.pages.LogInPage;
import ru.iukhimenko.transportsystem.autotesting.ui.pages.TransportDocumentsPage;

import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.iukhimenko.transportsystem.autotesting.core.TransportSystemConfig.TRANSPORT_SYSTEM_CONFIG;

@Tag("ui_transport_documents")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransportDocumentsTest extends UiTest {
    TransportDocumentsPage transportDocumentsPage;
    TransportDocumentsPool pool;
    final int DOCUMENT_TYPE_COLUMN = 0, VEHICLE_COLUMN = 5;

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

    @ParameterizedTest(name = "Can sort values ascending in column {0}")
    @ValueSource(ints = { DOCUMENT_TYPE_COLUMN, VEHICLE_COLUMN })
    @Severity(SeverityLevel.NORMAL)
    public void canSortAscTest(int columnIndex) {
        TransportDocumentsTable table = transportDocumentsPage.getTransportDocumentsTable();
        table.clickColumnHeader(columnIndex);
        assertThat(table.isColumnSortedAsc(columnIndex))
                .as("Table entries are sorted ascending on column header {0} first click", getColumnNameByIndex(columnIndex))
                .isTrue();
    }

    @ParameterizedTest(name = "Can sort values descending in column {0}")
    @ValueSource(ints = { DOCUMENT_TYPE_COLUMN, VEHICLE_COLUMN })
    @Severity(SeverityLevel.NORMAL)
    public void canSortDescTest(int columnIndex) {
        TransportDocumentsTable table = transportDocumentsPage.getTransportDocumentsTable();
        table.clickColumnHeader(columnIndex);
        table.clickColumnHeader(columnIndex);
        assertThat(table.isColumnSortedDesc(columnIndex))
                .as("Table entries are sorted descending on column header {0} second click", getColumnNameByIndex(columnIndex))
                .isTrue();
    }

    @Test
    @DisplayName("Check mandatory fields on 'Add Transport Document' form")
    @Severity(SeverityLevel.CRITICAL)
    public void mandatoryFieldsOnAddDocumentFormTest() {
        AddTransportDocumentForm form = transportDocumentsPage.clickAddButton();
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(form.isDocumentTypeFieldMandatory()).as("'Document Type' field is mandatory").isTrue();
        softly.assertThat(form.isSeriesFieldMandatory()).as("'Series' field is mandatory").isTrue();
        softly.assertThat(form.isNumberFieldMandatory()).as("'Number' field is mandatory").isTrue();
        softly.assertThat(form.isIssuedByMandatory()).as("'Issued by' field is mandatory").isTrue();
        softly.assertThat(form.isIssueDateMandatory()).as("'Issue Date' field is mandatory").isTrue();
        softly.assertThat(form.isVehicleDropdownMandatory()).as("'Vehicle' dropdown is mandatory").isTrue();
        softly.assertAll();
        form.close();
    }

    @Test
    @DisplayName("'Validity = not limited' is selected by default on 'Add Document' form")
    @Severity(SeverityLevel.CRITICAL)
    public void validityIsNotLimitedSelectedByDefaultTest() {
        AddTransportDocumentForm form = transportDocumentsPage.clickAddButton();
        assertThat(form.isNotLimitedRadioSelected()).as("'Validity = not limited' is selected by default on 'Add Document' form").isTrue();
        form.close();
    }

    @Test
    @DisplayName("'Expiration date' field is mandatory when 'Specify' radio button selected on 'Add Document' form")
    @Severity(SeverityLevel.CRITICAL)
    public void expirationDateFieldIsMandatoryTest() {
        AddTransportDocumentForm form = transportDocumentsPage.clickAddButton();
        form.selectSpecifyValidityRadioButton();
        assertThat(form.isValidToDateMandatory()).as("'Valid To' field is mandatory if 'Validity = Specify' was selected").isTrue();
        form.close();
    }

    @AfterEach
    public void goToMainMenu() {
        transportDocumentsPage.getPageHeader().toHomePage();
    }

    private String getColumnNameByIndex(int columnIndex) {
        switch(columnIndex) {
            case DOCUMENT_TYPE_COLUMN:
                return "'Type of Document'";
            case VEHICLE_COLUMN:
                return "'Vehicle Number'";
        }
        return "";
    }
}