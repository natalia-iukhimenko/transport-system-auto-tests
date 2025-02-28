package ru.iukhimenko.transportsystem.autotesting.ui.elements;

import static com.codeborne.selenide.Condition.*;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class Table {
    protected SelenideElement table;

    public Table(SelenideElement table) {
        this.table = table;
        waitTableLoaded(10000);
    }

    protected void waitTableLoaded(long timeout) {
       table.shouldBe(attribute("aria-busy", "false"), Duration.ofSeconds(timeout));
    }

    public List<SelenideElement> getRows() {
        return table.findAll(By.xpath("tbody/tr")).stream().collect(Collectors.toList());
    }

    public boolean isRowSelected(SelenideElement row) {
        return row.has(attribute("aria-selected","true"));
    }

    public List<SelenideElement> getRowCells(int rowIndex) {
        return getRows().get(rowIndex).findAll(By.tagName("td")).stream().collect(Collectors.toList());
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
}
