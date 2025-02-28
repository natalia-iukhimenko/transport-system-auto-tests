package ru.iukhimenko.transportsystem.autotesting.ui.elements;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import ru.iukhimenko.transportsystem.autotesting.ui.SelenideElementHelper;
import ru.iukhimenko.transportsystem.autotesting.ui.pages.TransportDocumentsPage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class AddTransportDocumentForm {
    private final SelenideElement form;
    private final SelenideElement documentTypeField = $(By.xpath("//*[@test-id = 'transportdoc.documenttype']"));
    private final SelenideElement seriesField = $(By.xpath("//*[@test-id = 'transportdoc.series']"));
    private final SelenideElement numberField = $(By.xpath("//*[@test-id = 'transportdoc.number']"));
    private final SelenideElement issuedByField = $(By.xpath("//*[@test-id = 'transportdoc.issuedby']"));
    private final SelenideElement issueDateField = $(By.xpath("//*[@test-id = 'transportdoc.issueddate']"));
    private final SelenideElement validToField = $(By.xpath("//*[@test-id = 'transportdoc.expiredate']"));
    private final SelenideElement notLimitedRadio = $(By.xpath("//input[@name='input-6' and contains(@id, 'option_0')]"));
    private final SelenideElement limitedRadio = $(By.xpath("//input[@name='input-6' and contains(@id, 'option_1')]"));
    private final SelenideElement vehicleDropdown = $(By.xpath("//*[@test-id = 'transportdoc.transportid']"));

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