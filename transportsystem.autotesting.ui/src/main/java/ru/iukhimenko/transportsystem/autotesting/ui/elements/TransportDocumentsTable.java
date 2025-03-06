package ru.iukhimenko.transportsystem.autotesting.ui.elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;

public class TransportDocumentsTable extends Table {
    public TransportDocumentsTable() {
        super($("#listTable"));
    }
}
