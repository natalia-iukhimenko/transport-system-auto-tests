package ru.iukhimenko.transportsystem.autotesting.ui.elements;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class VehicleMenuItem {
    public void selectEngines() {
        $(By.linkText("/engines")).click();
    }

    public void selectVehicleDocuments() {
        $(By.linkText("/transportdocs")).click();
    }

    public void selectAddVehicle() {
        $(By.linkText("/transports/add")).click();
    }

    public void selectListOfVehicles() {
        $(By.linkText("/transports")).click();
    }
}
