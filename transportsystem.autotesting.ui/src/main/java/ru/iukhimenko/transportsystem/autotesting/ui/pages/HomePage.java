package ru.iukhimenko.transportsystem.autotesting.ui.pages;

import ru.iukhimenko.transportsystem.autotesting.ui.elements.SideMenu;
import ru.iukhimenko.transportsystem.autotesting.ui.elements.PageHeader;

public class HomePage {
    private final PageHeader header;
    private final SideMenu sideMenu;

    public HomePage() {
        header = new PageHeader();
        sideMenu = new SideMenu();
    }

    public PageHeader getPageHeader() {
        return header;
    }

    public SideMenu getMenu() {
        return sideMenu;
    }
}
