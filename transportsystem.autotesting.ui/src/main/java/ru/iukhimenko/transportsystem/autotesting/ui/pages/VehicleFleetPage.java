package ru.iukhimenko.transportsystem.autotesting.ui.pages;

import ru.iukhimenko.transportsystem.autotesting.ui.elements.PageHeader;
import ru.iukhimenko.transportsystem.autotesting.ui.elements.VehiclesTable;

public class VehicleFleetPage {
    private PageHeader header;
    private VehiclesTable vehiclesTable;

    public VehicleFleetPage() {
        header = new PageHeader();
        vehiclesTable = new VehiclesTable();
    }
    public VehiclesTable getVehiclesTable() {
        return vehiclesTable;
    }

    public PageHeader getPageHeader() {
        return header;
    }
}
