package ru.iukhimenko.transportsystem.autotesting.ui.pages;

import ru.iukhimenko.transportsystem.autotesting.ui.elements.AddTransportDocumentForm;
import ru.iukhimenko.transportsystem.autotesting.ui.elements.PageHeader;
import ru.iukhimenko.transportsystem.autotesting.ui.elements.TransportDocumentsTable;

import static com.codeborne.selenide.Selenide.$;

public class TransportDocumentsPage {
    private final PageHeader header;
    private final TransportDocumentsTable transportDocumentsTable;

    public TransportDocumentsPage() {
        header = new PageHeader();
        transportDocumentsTable = new TransportDocumentsTable();
    }

    public PageHeader getPageHeader() {
        return header;
    }

    public TransportDocumentsTable getTransportDocumentsTable() {
        return transportDocumentsTable;
    }

    public AddTransportDocumentForm clickAddButton() {
        $("#addDocument").click();
        return new AddTransportDocumentForm();
    }
}