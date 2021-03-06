package ru.iukhimenko.transportsystem.autotesting.ui.elements;

import org.openqa.selenium.By;
import ru.iukhimenko.transportsystem.autotesting.ui.pages.TransportDocumentsPage;
import ru.iukhimenko.transportsystem.autotesting.ui.pages.VehicleFleetPage;

import static com.codeborne.selenide.Selenide.$;

public class VehicleMenuItem {
    public VehicleFleetPage selectVehicleFleet() {
        $(By.xpath("//a[@href='/transports']")).click();
        return new VehicleFleetPage();
    }

    public void selectAddVehicle() {
        $(By.xpath("//a[@href='/transports/add']")).click();
    }

    public TransportDocumentsPage selectTransportDocuments() {
        $(By.xpath("//a[@href='/transportdocs']")).click();
        return new TransportDocumentsPage();
    }

    public void selectEngines() {
        $(By.xpath("//a[@href='/engines']")).click();
    }
 }
