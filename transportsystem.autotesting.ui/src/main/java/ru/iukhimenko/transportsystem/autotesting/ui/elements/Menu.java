package ru.iukhimenko.transportsystem.autotesting.ui.elements;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.iukhimenko.transportsystem.autotesting.ui.SelenideElementHelper;
import static com.codeborne.selenide.Selenide.$;

public class Menu {
    Logger logger = LoggerFactory.getLogger(Menu.class);
    private SelenideElement vehicleMenuItem = $(By.xpath("//a[text()='Транспортные средства']"));

    public VehicleMenuItem expandVehicleMenuItem() {
        SelenideElementHelper.expand(vehicleMenuItem);
        logger.info("Vehicle menu has been expanded");
        return new VehicleMenuItem();
    }
}
