package ru.iukhimenko.transportsystem.autotesting.ui.elements;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.iukhimenko.transportsystem.autotesting.ui.SelenideElementHelper;
import ru.iukhimenko.transportsystem.autotesting.ui.model.TransportDocumentInfo;
import ru.iukhimenko.transportsystem.autotesting.ui.pages.TransportDocumentsPage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class AddTransportDocumentForm {
    Logger logger = LoggerFactory.getLogger(AddTransportDocumentForm.class);
    private final SelenideElement form;
    private final SelenideElement documentTypeField = $(By.xpath("//*[@test-id = 'transportdoc.documenttype']"));
    private final SelenideElement seriesField = $(By.xpath("//*[@test-id = 'transportdoc.series']"));
    private final SelenideElement numberField = $(By.xpath("//*[@test-id = 'transportdoc.number']"));
    private final SelenideElement issuedByField = $(By.xpath("//*[@test-id = 'transportdoc.issuedby']"));
    private final SelenideElement issueDateField = $(By.xpath("//*[@test-id = 'transportdoc.issueddate']"));
    private final SelenideElement validToField = $(By.xpath("//*[@test-id = 'transportdoc.expiredate']"));
    private final SelenideElement vehicleDropdown = $(By.xpath("//*[@test-id = 'transportdoc.transportid']"));
    private final SelenideElement submitButton = $(By.xpath("//button[@test-id = 'transportdoc.submit']"));

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
        logger.info("Entering transport document document type: {}", type);
        documentTypeField.setValue(type);
        return this;
    }

    public AddTransportDocumentForm setSeries(String series) {
        logger.info("Entering transport document series: {}", series);
        seriesField.setValue(series);
        return this;
    }

    public AddTransportDocumentForm setNumber(String number) {
        logger.info("Entering transport document number: {}", number);
        numberField.setValue(number);
        return this;
    }

    public AddTransportDocumentForm setIssuedBy(String issuedBy) {
        logger.info("Entering transport issuing authority for transport document: {}", issuedBy);
        issuedByField.setValue(issuedBy);
        return this;
    }

    public AddTransportDocumentForm setIssueDate(String issueDate) {
        logger.info("Entering transport document issue date: {}", issueDate);
        issueDateField.setValue(issueDate);
        return this;
    }

    public AddTransportDocumentForm setValidTo(String validTo) {
        logger.info("Entering transport document 'Valid to' date: {}", validTo);
        validToField.setValue(validTo);
        return this;
    }

    public AddTransportDocumentForm selectVehicle(String value) {
        logger.info("Selecting transport for transport document: {}", value);
        vehicleDropdown.selectOptionContainingText(value);
        return this;
    }

    public AddTransportDocumentForm fillForm(TransportDocumentInfo transportDocumentInfo) {
        setDocumentType(transportDocumentInfo.getDocumentType());
        setSeries(transportDocumentInfo.getSeries());
        setNumber(transportDocumentInfo.getNumber());
        setIssuedBy(transportDocumentInfo.getIssuedBy());
        setIssueDate(transportDocumentInfo.getIssuedDate());
        setValidTo(transportDocumentInfo.getExpireDate());
        selectVehicle(transportDocumentInfo.getTransportNumber());
        return this;
    }

    public TransportDocumentsPage clickOnSubmitButton() {
        logger.info("Clicking on 'Submit' button");
        submitButton.click();
        form.shouldNot(exist);
        return new TransportDocumentsPage();
    }

    public TransportDocumentsPage close() {
        logger.info("Clicking on 'Close' button");
        $(".close").click();
        form.shouldNot(exist);
        return new TransportDocumentsPage();
    }

    public boolean isDocumentTypeFieldEmpty() {
        return SelenideElementHelper.isInputFieldEmpty(documentTypeField);
    }

    public boolean isSeriesFieldEmpty() {
        return SelenideElementHelper.isInputFieldEmpty(seriesField);
    }

    public boolean isNumberFieldEmpty() {
        return SelenideElementHelper.isInputFieldEmpty(numberField);
    }

    public boolean isIssuedByFieldEmpty() {
        return SelenideElementHelper.isInputFieldEmpty(issuedByField);
    }

    public boolean isIssueDateFieldEmpty() {
        return SelenideElementHelper.isInputFieldEmpty(issueDateField);
    }

    public boolean isValidToFieldEmpty() {
        return SelenideElementHelper.isInputFieldEmpty(validToField);
    }

    public boolean isVehicleSelected() {
        return SelenideElementHelper.isValueSelectedInDropdown(vehicleDropdown);
    }
}