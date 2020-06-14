package ru.iukhimenko.transportsystem.autotesting.ui.pages;

import ru.iukhimenko.transportsystem.autotesting.ui.elements.Menu;
import ru.iukhimenko.transportsystem.autotesting.ui.elements.PageHeader;

public class HomePage {
    public PageHeader header;
    public Menu menu;

    public HomePage() {
        header = new PageHeader();
        menu = new Menu();
    }
}
