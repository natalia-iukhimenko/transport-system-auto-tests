package ru.iukhimenko.transportsystem.autotesting.ui.elements;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.iukhimenko.transportsystem.autotesting.ui.SelenideElementHelper;
import static com.codeborne.selenide.Selenide.$;

public class SideMenu {
    Logger logger = LoggerFactory.getLogger(SideMenu.class);
    private final SelenideElement vehicleMenuItem = $(By.xpath("//a[@test-id = 'vehicles_menu']"));

    public VehicleMenuItem expandVehicleMenuItem() {
        logger.info("Expanding 'Vehicle' menu item");
        SelenideElementHelper.expand(vehicleMenuItem);
        return new VehicleMenuItem();
    }
}
