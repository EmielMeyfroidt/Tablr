package test;

import static org.junit.Assert.*;

import main.BoolValue;
import main.Cell;
import main.IntValue;
import main.StringValue;

import org.junit.Test;

/**
 *
 */
public class CellTest {

    @Test
    public void cellConstructor_SetsValueCorrectly() {
    	StringValue s = new StringValue("test");
        Cell cell = new Cell(s);
        assertEquals("test", cell.getValue());
    }

    @Test
    public void setValue_ChangesCellValueSuccessfully() {
    	StringValue s = new StringValue("testValue");
        Cell cell = new Cell(s);
        assertEquals("testValue", cell.getValue());
        StringValue s2 = new StringValue("changedValue");
        cell.setValue(s2);
        assertEquals("changedValue", cell.getValue());
    }

    @Test
    public void cell_HandlesDifferentDataTypes() {
        // Integer type
    	IntValue i = new IntValue("42");
        Cell intCell = new Cell(i);
        assertEquals("42", intCell.getValue());

//        // Double type
//        Cell<Double> doubleCell = new Cell<>(3.14);
//        assertEquals(Double.valueOf(3.14), doubleCell.getValue());

        // Boolean type
        BoolValue b = new BoolValue("true");
        Cell booleanCell = new Cell(b);
        assertEquals("true", booleanCell.getValue());
    }

    @Test
    public void cell_AllowsNullValues() {
    	StringValue s = new StringValue(null);
        Cell cell = new Cell(s);
        assertNull(cell.getValue()); // Check initial null value

        StringValue s2 = new StringValue("notNull");
        cell.setValue(s2);
        assertEquals("notNull", cell.getValue()); // Check updated value

        cell.setValue(null);
        assertNull(cell.getValue()); // Check reset to null
    }

}
