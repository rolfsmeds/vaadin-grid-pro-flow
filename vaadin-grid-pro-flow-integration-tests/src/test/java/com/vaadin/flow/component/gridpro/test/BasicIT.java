package com.vaadin.flow.component.gridpro.test;

import com.vaadin.flow.component.gridpro.testbench.GridProElement;
import com.vaadin.flow.component.gridpro.testbench.GridTHTDElement;
import com.vaadin.testbench.annotations.RunLocally;
import com.vaadin.testbench.parallel.Browser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

@RunLocally(Browser.CHROME)
public class BasicIT extends AbstractParallelTest {

    private GridProElement grid;

    @Before
    public void init() {
        getDriver().get(getBaseURL());
        grid = $(GridProElement.class).waitForFirst();
    }

    @Test
    public void editColumnsAdded() {
        List<WebElement> columns = grid.findElements(By.tagName("vaadin-grid-pro-edit-column"));
        Assert.assertEquals(columns.size(), 3);
    }

    @Test
    public void textEditorIsUsedForTextColumn() {
        AssertCellEnterEditModeOnDoubleClick(0, 1, "vaadin-grid-pro-edit-column-text-field");
    }

    @Test
    public void checkboxEditorIsUsedForTextColumn() {
        AssertCellEnterEditModeOnDoubleClick(0, 2, "vaadin-grid-pro-edit-column-checkbox");
    }

    @Test
    public void selectEditorIsUsedForTextColumn() {
        AssertCellEnterEditModeOnDoubleClick(0, 3, "vaadin-grid-pro-edit-column-select-wrapper");
    }

    @Test
    public void selectEditorOptionsAreSet() {
        GridTHTDElement cell = grid.getCell(0, 3);
        ArrayList optionsList = cell.getColumn().getOptionsList();
        Assert.assertTrue(optionsList.contains("Male"));
        Assert.assertTrue(optionsList.contains("Female"));
        Assert.assertTrue(optionsList.contains("Unknown"));
    }

    private void AssertCellEnterEditModeOnDoubleClick(Integer rowIndex, Integer colIndex, String editorTag) {
        GridTHTDElement cell = grid.getCell(rowIndex, colIndex);

        // Not in edit mode initially
        Assert.assertFalse(cell.innerHTMLContains(editorTag));

        // Entering edit mode with double click
        doubleClickOn(cell);
        WebElement editor = cell.getFirstSlottedElement();

        Assert.assertEquals(editor.getTagName(), editorTag);
    }
}
