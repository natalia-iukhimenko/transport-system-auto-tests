package ru.iukhimenko.transportsystem.autotesting.ui.elements;

import static com.codeborne.selenide.Condition.*;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.iukhimenko.transportsystem.autotesting.ui.pages.LogInPage;
import static com.codeborne.selenide.Selenide.$;

public class PageHeader {
    Logger logger = LoggerFactory.getLogger(PageHeader.class);
    SelenideElement homePageLink = $(By.id("home"));
    SelenideElement userMenu = $(By.xpath("//li[@id='user_menu']/a"));
    SelenideElement logoutLink = $(By.id("logout"));

    public String getDisplayedUsername() {
        return userMenu.find(By.tagName("em")).getText();
    }

    public PageHeader expandUserMenu() {
        if (userMenu.attr("aria-expanded").equals("false")) {
            userMenu.click();
            userMenu.shouldHave(attribute("aria-expanded", "true"));
            logger.info("User menu has been expanded");
        }
        return this;
    }

    public PageHeader collapseUserMenu() {
        if (userMenu.attr("aria-expanded").equals("true")) {
            userMenu.click();
            userMenu.shouldHave(attribute("aria-expanded", "false"));
            logger.info("User menu has been collapsed");
        }
        return this;
    }

    public LogInPage clickLogOutButton() {
        logoutLink.click();
        logger.info("Clicked 'Log out' button");
        return new LogInPage();
    }
}
