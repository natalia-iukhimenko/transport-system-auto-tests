package ru.iukhimenko.transportsystem.autotesting.ui.elements;

import static com.codeborne.selenide.Condition.*;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;

public class VehiclesTable extends Table {
    private Logger logger = LoggerFactory.getLogger(VehiclesTable.class);

    public VehiclesTable() {
        super($(By.id("vehicleTable")));
    }

    public VehiclesTable expandRow(int rowIndex) {
        List<SelenideElement> rows = getRows();
        if (rowIndex < rows.size()) {
            SelenideElement row = rows.get(rowIndex);
            if (!isRowSelected(row)) {
                getRowCells(rowIndex).get(0).click();
                row.shouldHave(attribute("aria-selected", "true"));
                logger.info("Row {} has been expanded", rowIndex);
            }
        }
        return this;
    }

    public VehiclesTable collapseRow(int rowIndex) {
        List<SelenideElement> rows = getRows();
        if (rowIndex < rows.size()) {
            SelenideElement row = rows.get(rowIndex);
            if (isRowSelected(row)) {
                getRowCells(rowIndex).get(0).click();
                row.shouldHave(attribute("aria-selected", "false"));
                logger.info("Row {} has been collapsed", rowIndex);
            }
        }
        return this;
    }
}
