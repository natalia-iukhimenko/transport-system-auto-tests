package ru.iukhimenko.transportsystem.autotesting.ui;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.attribute;

public class SelenideElementHelper {
    public static boolean isFieldHighlightedAsInvalid(SelenideElement field) {
        return field.attr("aria-invalid") != null;
    }

    public static void expand(SelenideElement element) {
        if (!element.attr("aria-expanded").equals("true")) {
            element.click();
            element.shouldHave(attribute("aria-expanded", "true"));

        }
    }

    public static void collapse(SelenideElement element) {
        if (!element.attr("aria-expanded").equals("false")) {
            element.click();
            element.shouldHave(attribute("aria-expanded", "false"));
        }
    }

    public static boolean isMandatory(SelenideElement element) {
        return element.has(attribute("aria-required", "true"));
    }
}
