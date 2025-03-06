package ru.iukhimenko.transportsystem.autotesting.ui;

import com.codeborne.selenide.SelenideElement;

import java.util.Objects;

import static com.codeborne.selenide.Condition.attribute;

public class SelenideElementHelper {
    public static boolean isFieldHighlightedAsInvalid(SelenideElement field) {
        return field.attr("aria-invalid") != null;
    }

    public static void expand(SelenideElement element) {
        if (!Objects.equals(element.attr("aria-expanded"), "true")) {
            element.click();
            element.shouldHave(attribute("aria-expanded", "true"));

        }
    }

    public static void collapse(SelenideElement element) {
        if (!Objects.equals(element.attr("aria-expanded"), "false")) {
            element.click();
            element.shouldHave(attribute("aria-expanded", "false"));
        }
    }

    public static boolean isMandatory(SelenideElement element) {
        return element.has(attribute("aria-required", "true"));
    }

    public static boolean isInputFieldEmpty(SelenideElement element) {
        return Objects.requireNonNull(element.getValue()).isEmpty();
    }

    public static boolean isValueSelectedInDropdown(SelenideElement element) {
        return !Objects.requireNonNull(element.getSelectedOptionText()).isEmpty();
    }
}
