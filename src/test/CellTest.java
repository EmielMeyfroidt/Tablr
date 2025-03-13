/**
 * 
 */
package test;

import static org.junit.Assert.*;

import main.Cell;
import org.junit.Test;

/**
 * 
 */
public class CellTest {

	@Test
	public void cellConstructor_SetsValueCorrectly() {
		Cell<String> cell = new Cell<>("test");
		assertEquals("test", cell.getValue());
	}

	@Test
	public void setValue_ChangesCellValueSuccessfully() {
		Cell<String> cell = new Cell<>("testValue");
		assertEquals("testValue", cell.getValue());
		cell.setValue("changedValue");
		assertEquals("changedValue", cell.getValue());
	}

	@Test
	public void cell_HandlesDifferentDataTypes() {
		// Integer type
		Cell<Integer> intCell = new Cell<>(42);
		assertEquals(Integer.valueOf(42), intCell.getValue());

		// Double type
		Cell<Double> doubleCell = new Cell<>(3.14);
		assertEquals(Double.valueOf(3.14), doubleCell.getValue());

		// Boolean type
		Cell<Boolean> booleanCell = new Cell<>(true);
		assertEquals(Boolean.TRUE, booleanCell.getValue());
	}

	@Test
	public void cell_AllowsNullValues() {
		Cell<String> cell = new Cell<>(null);
		assertNull(cell.getValue()); // Check initial null value

		cell.setValue("notNull");
		assertEquals("notNull", cell.getValue()); // Check updated value

		cell.setValue(null);
		assertNull(cell.getValue()); // Check reset to null
	}

}
