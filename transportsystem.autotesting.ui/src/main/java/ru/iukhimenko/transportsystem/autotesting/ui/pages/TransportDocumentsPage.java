package ru.iukhimenko.transportsystem.autotesting.ui.pages;

import ru.iukhimenko.transportsystem.autotesting.ui.elements.AddTransportDocumentForm;
import ru.iukhimenko.transportsystem.autotesting.ui.elements.PageHeader;
import ru.iukhimenko.transportsystem.autotesting.ui.elements.TransportDocumentsTable;
import ru.iukhimenko.transportsystem.autotesting.ui.elements.TransportModelsTable;

import static com.codeborne.selenide.Selenide.$;

public class TransportDocumentsPage {
    private PageHeader header;
    private TransportDocumentsTable transportModelsTable;

    public TransportDocumentsPage() {
        header = new PageHeader();
        transportModelsTable = new TransportDocumentsTable();
    }

    public TransportDocumentsTable getTransportDocumentsTable() {
        return transportModelsTable;
    }

    public AddTransportDocumentForm clickAddButton() {
        $("#addDocument").click();
        return new AddTransportDocumentForm();
    }

    public void clickEditButton() {
        $("#editDocument").click();
    }
}
