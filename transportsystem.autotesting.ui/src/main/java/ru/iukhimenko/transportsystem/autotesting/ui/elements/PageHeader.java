package ru.iukhimenko.transportsystem.autotesting.ui.elements;

import static com.codeborne.selenide.Condition.*;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.iukhimenko.transportsystem.autotesting.ui.SelenideElementHelper;
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
        SelenideElementHelper.expand(userMenu);
        return this;
    }

    public PageHeader collapseUserMenu() {
        SelenideElementHelper.collapse(userMenu);
        return this;
    }

    public LogInPage clickLogOutButton() {
        logoutLink.click();
        logger.info("Clicked 'Log out' button");
        return new LogInPage();
    }
}
