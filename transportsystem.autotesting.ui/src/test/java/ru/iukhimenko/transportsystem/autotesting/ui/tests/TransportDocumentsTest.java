package ru.iukhimenko.transportsystem.autotesting.ui.tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.iukhimenko.transportsystem.autotesting.api.objectpools.TransportDocumentsPool;
import ru.iukhimenko.transportsystem.autotesting.ui.base.UiTest;
import ru.iukhimenko.transportsystem.autotesting.ui.elements.TransportDocumentsTable;
import ru.iukhimenko.transportsystem.autotesting.ui.pages.HomePage;
import ru.iukhimenko.transportsystem.autotesting.ui.pages.LogInPage;
import ru.iukhimenko.transportsystem.autotesting.ui.pages.TransportDocumentsPage;

import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.iukhimenko.transportsystem.autotesting.core.Configs.*;

@Tag("ui_transport_documents")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransportDocumentsTest extends UiTest {
    TransportDocumentsPage transportDocumentsPage;
    TransportDocumentsPool pool;
    final int DOCUMENT_TYPE_COLUMN = 0, VEHICLE_COLUMN = 5;

    @BeforeAll
    public void createTestDataAndLogIn() {
        pool = new TransportDocumentsPool(3);
        open(BASE_URI, LogInPage.class).logInWith(ADMIN_USERNAME, ADMIN_PASSWORD);
    }

    @BeforeEach
    public void goToTransportDocumentsPage() {
        transportDocumentsPage = new HomePage().getMenu()
                .expandVehicleMenuItem()
                .selectTransportDocuments();
    }

    @ParameterizedTest(name = "Can sort values ascending in column {0}")
    @ValueSource(ints = { DOCUMENT_TYPE_COLUMN, VEHICLE_COLUMN })
    public void canSortAscTest(int columnIndex) {
        TransportDocumentsTable table = transportDocumentsPage.getTransportDocumentsTable();
        table.clickColumnHeader(columnIndex);
        assertThat(table.isColumnSortedAsc(columnIndex))
                .as("Table entries are sorted ascending on column header {0} first click", getColumnNameByIndex(columnIndex))
                .isTrue();
    }

    @ParameterizedTest(name = "Can sort values descending in column {0}")
    @ValueSource(ints = { DOCUMENT_TYPE_COLUMN, VEHICLE_COLUMN })
    public void canSortDescTest(int columnIndex) {
        TransportDocumentsTable table = transportDocumentsPage.getTransportDocumentsTable();
        table.clickColumnHeader(columnIndex);
        table.clickColumnHeader(columnIndex);
        assertThat(table.isColumnSortedDesc(columnIndex))
                .as("Table entries are sorted descending on column header {0} second click", getColumnNameByIndex(columnIndex))
                .isTrue();
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
