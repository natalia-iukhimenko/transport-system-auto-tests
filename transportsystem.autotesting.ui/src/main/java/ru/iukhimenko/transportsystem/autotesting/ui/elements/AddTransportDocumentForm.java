package ru.iukhimenko.transportsystem.autotesting.ui.elements;

import static com.codeborne.selenide.Condition.exist;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class AddTransportDocumentForm {
    private SelenideElement form;

    public AddTransportDocumentForm() {
        $("#modal-form").should(exist);
    }
}