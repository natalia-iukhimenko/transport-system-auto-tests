package ru.iukhimenko.transportsystem.autotesting.ui.tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.iukhimenko.transportsystem.autotesting.ui.base.UiTest;
import ru.iukhimenko.transportsystem.autotesting.ui.elements.VehiclesTable;
import ru.iukhimenko.transportsystem.autotesting.ui.pages.HomePage;
import ru.iukhimenko.transportsystem.autotesting.ui.pages.LogInPage;
import ru.iukhimenko.transportsystem.autotesting.ui.pages.VehicleFleetPage;
import ru.iukhimenko.transportsystem.autotesting.ui.tags.UiRegression;

import static com.codeborne.selenide.Selenide.open;
import static ru.iukhimenko.transportsystem.autotesting.core.Configs.*;
import static org.assertj.core.api.Assertions.*;

@Tag("ui_vehicle_fleet")
@UiRegression
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VehicleFleetPageTest extends UiTest {
    VehicleFleetPage vehicleFleetPage;
    VehiclesTable table;
    final int NAME_COLUMN = 0, MANUFACTURER_COLUMN = 1, MAX_WEIGHT_COLUMN = 2;

    @BeforeAll
    public void logIn() {
        open(BASE_URI, LogInPage.class)
                .logInWith(ADMIN_USERNAME, ADMIN_PASSWORD);
    }

    @BeforeEach
    public void goToVehicleFleetPage() {
        vehicleFleetPage = new HomePage().getMenu()
                .expandVehicleMenuItem()
                .selectVehicleFleet();
        table = vehicleFleetPage.getVehiclesTable();
    }

    @ParameterizedTest(name = "Can sort values ascending in column {0}")
    @ValueSource(ints = { NAME_COLUMN, MANUFACTURER_COLUMN, MAX_WEIGHT_COLUMN })
    public void checkCanSortAscendingTest(Integer columnIndex) {
        table.clickColumnHeader(columnIndex);
        assertThat(table.isColumnSortedAsc(columnIndex))
                .as("Table entries are sorted ascending on column header {0} first click", getColumnNameByIndex(columnIndex))
                .isTrue();
    }

    @ParameterizedTest(name = "Can sort values ascending in column {0}")
    @ValueSource(ints = { NAME_COLUMN, MANUFACTURER_COLUMN, MAX_WEIGHT_COLUMN })
    public void checkCanSortDescendingTest(Integer columnIndex) {
        table.clickColumnHeader(columnIndex);
        table.clickColumnHeader(columnIndex);
        assertThat(table.isColumnSortedDesc(columnIndex))
                .as("Table entries are sorted descending on column header {0} second click", getColumnNameByIndex(columnIndex))
                .isTrue();
    }

    @AfterEach
    public void goToMainMenu() {
        vehicleFleetPage.getPageHeader().toHomePage();
    }

    private String getColumnNameByIndex(int columnIndex) {
        switch(columnIndex) {
            case NAME_COLUMN:
                return "'Name'";
            case MANUFACTURER_COLUMN:
                return "'Manufacturer'";
            case MAX_WEIGHT_COLUMN:
                return "'Max Weight'";
        }
        return "";
    }
}
