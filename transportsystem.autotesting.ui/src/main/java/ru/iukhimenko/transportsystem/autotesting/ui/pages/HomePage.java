package ru.iukhimenko.transportsystem.autotesting.ui.pages;

import ru.iukhimenko.transportsystem.autotesting.ui.elements.Menu;
import ru.iukhimenko.transportsystem.autotesting.ui.elements.PageHeader;

public class HomePage {
    private final PageHeader header;
    private final Menu menu;

    public HomePage() {
        header = new PageHeader();
        menu = new Menu();
    }

    public PageHeader getPageHeader() {
        return header;
    }

    public Menu getMenu() {
        return menu;
    }
}
