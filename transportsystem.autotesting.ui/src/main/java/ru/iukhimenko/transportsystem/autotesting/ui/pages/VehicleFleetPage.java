package ru.iukhimenko.transportsystem.autotesting.ui.pages;

import ru.iukhimenko.transportsystem.autotesting.ui.elements.PageHeader;
import ru.iukhimenko.transportsystem.autotesting.ui.elements.TransportModelsTable;

public class VehicleFleetPage {
    private PageHeader header;
    private TransportModelsTable transportModelsTable;

    public VehicleFleetPage() {
        header = new PageHeader();
        transportModelsTable = new TransportModelsTable();
    }
    public TransportModelsTable getTransportModelsTable() {
        return transportModelsTable;
    }

    public PageHeader getPageHeader() {
        return header;
    }
}
