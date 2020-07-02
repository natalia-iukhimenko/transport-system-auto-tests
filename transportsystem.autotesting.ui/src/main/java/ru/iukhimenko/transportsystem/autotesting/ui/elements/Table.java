package ru.iukhimenko.transportsystem.autotesting.ui.elements;

import static com.codeborne.selenide.Condition.*;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.conditions.AttributeWithValue;
import org.openqa.selenium.By;
import java.util.LinkedList;
import java.util.List;

public class Table {
    protected SelenideElement table;

    public Table(SelenideElement table) {
        this.table = table;
        waitTableLoaded(10000);
    }

    protected void waitTableLoaded(long timeout) {
        table.waitUntil(attribute("aria-busy", "false"), timeout);
    }

    public List<SelenideElement> getRows() {
        return table.findAll(By.xpath("tbody/tr"));
    }

    public boolean isRowSelected(SelenideElement row) {
        return row.has(attribute("aria-selected","true"));
    }

    public List<SelenideElement> getRowCells(int rowIndex) {
        return getRows().get(rowIndex).findAll(By.tagName("td"));
    }

    public void selectRow(int rowIndex) {
        List<SelenideElement> rows = getRows();
        if (rowIndex < rows.size()) {
            SelenideElement row = rows.get(rowIndex);
            if (!isRowSelected(row)) {
                getRowCells(rowIndex).get(0).click();
                row.shouldHave(attribute("aria-selected", "true"));
            }
        }
    }

    public void unselectRow(int rowIndex) {
        List<SelenideElement> rows = getRows();
        if (rowIndex < rows.size()) {
            SelenideElement row = rows.get(rowIndex);
            if (isRowSelected(row)) {
                getRowCells(rowIndex).get(0).click();
                row.shouldHave(attribute("aria-selected", "false"));
            }
        }
    }

    public String getCellValue(int rowIndex, int columnIndex) {
        return getRowCells(rowIndex).get(columnIndex).text();
    }

    public List<SelenideElement> getColumnHeaders() {
        return table.findAll(By.xpath("thead/tr/th"));
    }

    public void clickColumnHeader(int columnIndex) {
        List<SelenideElement> columnHeaders = getColumnHeaders();
        if (columnIndex <= columnHeaders.size()) {
            columnHeaders.get(columnIndex).click();
        }
    }

    public List<String> getColumnValues(int columnIndex) {
        List<String> columnValues = new LinkedList<>();
        for (int rowIndex = 0; rowIndex < getRows().size(); rowIndex++) {
            columnValues.add(getCellValue(rowIndex, columnIndex));
        }
        return columnValues;
    }

    public boolean isColumnSortedAsc(int columnIndex) {
        AttributeWithValue sortedAscAttribute = new AttributeWithValue("aria-sort", "ascending");
        SelenideElement column = getColumnHeaders().get(columnIndex);
        return column.shouldHave(sortedAscAttribute).has(sortedAscAttribute);
    }

    public boolean isColumnSortedDesc(int columnIndex) {
        AttributeWithValue sortedDescAttribute = new AttributeWithValue("aria-sort", "descending");
        SelenideElement column = getColumnHeaders().get(columnIndex);
        return column.shouldHave(sortedDescAttribute).has(sortedDescAttribute);
    }
}
