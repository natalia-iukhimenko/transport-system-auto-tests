package ru.iukhimenko.transportsystem.autotesting.ui.elements;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import ru.iukhimenko.transportsystem.autotesting.ui.SelenideElementHelper;
import ru.iukhimenko.transportsystem.autotesting.ui.pages.TransportDocumentsPage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class AddTransportDocumentForm {
    private SelenideElement form;
    private SelenideElement documentTypeField = $("#input-1");
    private SelenideElement seriesField = $("#input-2");
    private SelenideElement numberField = $("#input-3");
    private SelenideElement issuedByField = $("#input-4");
    private SelenideElement issueDateField = $("#input-5");
    private SelenideElement validToField = $("#input-7");
    private SelenideElement notLimitedRadio = $(By.xpath("//input[@name='input-6' and contains(@id, 'option_0')]"));
    private SelenideElement limitedRadio = $(By.xpath("//input[@name='input-6' and contains(@id, 'option_1')]"));
    private SelenideElement vehicleDropdown = $("#input-8");

    public AddTransportDocumentForm() {
        form = $("#modal-form");
        form.should(exist);
    }

    public boolean isDocumentTypeFieldMandatory() {
        return SelenideElementHelper.isMandatory(documentTypeField);
    }

    public boolean isSeriesFieldMandatory() {
        return SelenideElementHelper.isMandatory(seriesField);
    }

    public boolean isNumberFieldMandatory() {
        return SelenideElementHelper.isMandatory(numberField);
    }

    public boolean isIssuedByMandatory() {
        return SelenideElementHelper.isMandatory(issuedByField);
    }

    public boolean isIssueDateMandatory() {
        return SelenideElementHelper.isMandatory(issueDateField);
    }

    public boolean isVehicleDropdownMandatory() {
        return SelenideElementHelper.isMandatory(vehicleDropdown);
    }

    public AddTransportDocumentForm selectSpecifyValidityRadioButton() {
        limitedRadio.sibling(0).click();
        validToField.should(exist);
        return this;
    }

    public boolean isValidToDateMandatory() {
        return SelenideElementHelper.isMandatory(validToField);
    }

    public AddTransportDocumentForm setDocumentType(String type) {
        documentTypeField.setValue(type);
        return this;
    }

    public AddTransportDocumentForm setSeries(String series) {
        seriesField.setValue(series);
        return this;
    }

    public AddTransportDocumentForm setNumber(String number) {
        numberField.setValue(number);
        return this;
    }

    public AddTransportDocumentForm setIssuedBy(String issuedBy) {
        issuedByField.setValue(issuedBy);
        return this;
    }

    public AddTransportDocumentForm setIssueDate(String issueDate) {
        issueDateField.setValue(issueDate);
        return this;
    }

    public AddTransportDocumentForm setValidTo(String validTo) {
        validToField.setValue(validTo);
        return this;
    }

    public AddTransportDocumentForm selectVehicle(int optionIndex) {
        vehicleDropdown.selectOption(optionIndex);
        return this;
    }

    public TransportDocumentsPage clickAddButton() {
        form.find("button[type='submit']").click();
        form.shouldNot(exist);
        return new TransportDocumentsPage();
    }

    public TransportDocumentsPage close() {
        $(".close").click();
        form.shouldNot(exist);
        return new TransportDocumentsPage();
    }

    public boolean isNotLimitedRadioSelected() {
        return notLimitedRadio.isSelected();
    }
}