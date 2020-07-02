package ru.iukhimenko.transportsystem.autotesting.ui.elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;

public class TransportDocumentsTable extends Table {
    private Logger logger = LoggerFactory.getLogger(TransportDocumentsTable.class);

    public TransportDocumentsTable() {
        super($("#listTable"));
    }

    public TransportDocumentsTable select(int rowIndex) {
        selectRow(rowIndex);
        logger.info("Row {} has been selected", rowIndex);
        return this;
    }

    public TransportDocumentsTable unselect(int rowIndex) {
        unselectRow(rowIndex);
        logger.info("Row {} has been unselected", rowIndex);
        return this;
    }
}
