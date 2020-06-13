package ru.iukhimenko.transportsystem.autotesting.ui.base;

import com.codeborne.selenide.SelenideElement;

public interface IPage {
    default boolean isFieldHighlightedAsInvalid(SelenideElement field) {
        return field.attr("aria-invalid") != null;
    }
}
