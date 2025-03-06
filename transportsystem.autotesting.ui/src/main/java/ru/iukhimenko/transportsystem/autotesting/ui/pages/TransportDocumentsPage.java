package ru.iukhimenko.transportsystem.autotesting.ui.pages;

import com.codeborne.selenide.SelenideElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.iukhimenko.transportsystem.autotesting.ui.elements.AddTransportDocumentForm;
import ru.iukhimenko.transportsystem.autotesting.ui.elements.PageHeader;
import ru.iukhimenko.transportsystem.autotesting.ui.elements.TransportDocumentsTable;
import ru.iukhimenko.transportsystem.autotesting.ui.model.TransportDocumentInfo;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;

public class TransportDocumentsPage {
    Logger logger = LoggerFactory.getLogger(TransportDocumentsPage.class);
    private final PageHeader header;
    private final TransportDocumentsTable transportDocumentsTable;
    private final int DOCUMENT_TYPE_COLUMN = 0;
    private final int SERIES_COLUMN = 1;
    private final int NUMBER_COLUMN = 2;
    private final int ISSUED_BY_COLUMN = 3;
    private final int ISSUE_DATE_COLUMN = 4;
    private final int TRANSPORT_NUMBER_COLUMN = 5;

    public TransportDocumentsPage() {
        header = new PageHeader();
        transportDocumentsTable = new TransportDocumentsTable();
    }

    public List<TransportDocumentInfo> getTransportDocumentsFromTable() {
        List<TransportDocumentInfo> transportDocuments = new ArrayList<>();
        for (int i = 0; i < transportDocumentsTable.getRows().size(); i++) {
            List<SelenideElement> cells = transportDocumentsTable.getRowCells(i);
            TransportDocumentInfo transportDocumentInfo = new TransportDocumentInfo();
            transportDocumentInfo.setDocumentType(cells.get(DOCUMENT_TYPE_COLUMN).getText());
            transportDocumentInfo.setSeries(cells.get(SERIES_COLUMN).getText());
            transportDocumentInfo.setNumber(cells.get(NUMBER_COLUMN).getText());
            transportDocumentInfo.setIssuedBy(cells.get(ISSUED_BY_COLUMN).getText());
            transportDocumentInfo.setIssuedDate(cells.get(ISSUE_DATE_COLUMN).getText());
            transportDocumentInfo.setTransportNumber(cells.get(TRANSPORT_NUMBER_COLUMN).getText());
            transportDocuments.add(transportDocumentInfo);
        }
        return transportDocuments;
    }

    public AddTransportDocumentForm clickAddButton() {
        logger.info("Clicking on 'Add Transport Document' button");
        $("#addDocument").click();
        return new AddTransportDocumentForm();
    }

    public HomePage clickHomeButton() {
        return header.goToHomePage();
    }
}