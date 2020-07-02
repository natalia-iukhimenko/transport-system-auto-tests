package ru.iukhimenko.transportsystem.autotesting.ui.elements;

import static com.codeborne.selenide.Condition.*;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;

public class TransportModelsTable extends Table {
    private Logger logger = LoggerFactory.getLogger(TransportModelsTable.class);

    public TransportModelsTable() {
        super($(By.id("vehicleTable")));
    }

    public TransportModelsTable expandRow(int rowIndex) {
        selectRow(rowIndex);
        logger.info("Row {} has been expanded", rowIndex);
        return this;
    }

    public TransportModelsTable collapseRow(int rowIndex) {
        unselectRow(rowIndex);
        logger.info("Row {} has been expanded", rowIndex);
        return this;
    }
}
