package ru.iukhimenko.transportsystem.autotesting.ui.elements;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.iukhimenko.transportsystem.autotesting.ui.pages.HomePage;
import static com.codeborne.selenide.Selenide.$;

public class PageHeader {
    Logger logger = LoggerFactory.getLogger(PageHeader.class);
    SelenideElement homePageLink = $(By.xpath("//*[@test-id='home_link']"));

    public String getDisplayedUsername() {
        return $(By.xpath("//*[@test-id='username']/a")).getText();
    }

    public HomePage goToHomePage() {
        logger.info("Clicking on home page link");
        homePageLink.click();
        return new HomePage();
    }
}
