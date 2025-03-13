package test;

import static org.junit.Assert.*;

import main.Column;
import org.junit.Test;

import java.util.List;

public class ColumnTest {

	@Test
	public void testConstructorAndGetInfo() {
		Column<String> column = new Column<>("TestColumn", String.class, true, "Default");

		assertEquals("TestColumn", column.getName());
		assertEquals(String.class, column.getType());
		assertTrue(column.getInfo().contains("TestColumn String true Default"));
	}

	@Test
	public void testAddCellAndSize() {
		Column<Integer> column = new Column<>("NumberColumn", Integer.class, false, 42);

		assertEquals(0, column.getSize());

		column.addCell();
		assertEquals(1, column.getSize());

		column.addCell();
		assertEquals(2, column.getSize());
	}

	@Test
	public void testRemoveCell() {
		Column<String> column = new Column<>("StringColumn", String.class, true, "Default");

		column.addCell();
		column.addCell();
		assertEquals(2, column.getSize());

		column.removeRow(0);
		assertEquals(1, column.getSize());
	}

	@Test
	public void testGetAndSetCell() {
		Column<String> column = new Column<>("StringColumn", String.class, false, "");

		column.addCell();
		column.updateCell(0, "stringValue");
		assertEquals("stringValue", column.getCell(0));
	}

	@Test
	public void testChangeAllowsBlanks() {
		Column<String> column = new Column<>("BlankColumn", String.class, false, "Default");

		assertFalse(column.getInfo().contains("true"));

		column.changeAllowBlanks();
		assertTrue(column.getInfo().contains("true"));

		column.changeAllowBlanks();
		assertFalse(column.getInfo().contains("true"));
	}

	@Test
	public void testSetName() {
		Column<String> column = new Column<>("OriginalName", String.class, true, "Default");

		column.setName("NewName");
		assertEquals("NewName", column.getName());
	}

	@Test
	public void testGetColumnContent() {
		Column<Boolean> column = new Column<>("BooleanColumn", Boolean.class, false, false);

		column.addCell();
		column.addCell();
		column.updateCell(0, "true");
		column.updateCell(1, "false");

		List<String> columnValues = column.getColumn();
		assertEquals(2, columnValues.size());
		assertEquals("true", columnValues.get(0));
		assertEquals("false", columnValues.get(1));
	}

	@Test
	public void testDefaultCellContent() {
		Column<String> column = new Column<>("DefaultTest", String.class, true, "DefaultValue");

		column.addCell();
		assertEquals("DefaultValue", column.getCell(0));
	}
	
	@Test
	public void testUpdateCellForDifferentTypes() {
		Column<String> string = new Column<>("String", String.class, true, "DefaultValue");
		string.addCell();
		string.updateCell(0, "UpdatedValue");
		assertEquals(string.getCell(0), "UpdatedValue");
		Column<Boolean> bool = new Column<>("Bool", Boolean.class, true, true);
		bool.addCell();
		bool.updateCell(0, "false");
		assertEquals(bool.getCell(0), "false");
		Column<Integer> integer = new Column<>("Integer", Integer.class, true, 0);
		integer.addCell();
		integer.updateCell(0, "1");
		assertEquals(integer.getCell(0), "1");
	}
	
	@Test
	public void testSetDefaultValueForDifferentTypes() {
		Column<String> string = new Column<>("String", String.class, true, "DefaultValue");
		string.addCell();
		string.setDefaultValue("UpdatedValue");
		assertEquals(string.getDefaultValue(), "UpdatedValue");
		Column<Boolean> bool = new Column<>("Bool", Boolean.class, true, true);
		bool.addCell();
		bool.setDefaultValue("false");
		assertEquals(bool.getDefaultValue(), false);
		Column<Integer> integerCol = new Column<>("Integer", Integer.class, true, 0);
		integerCol.addCell();
		integerCol.setDefaultValue("1");
		assertEquals((Integer) 1, integerCol.getDefaultValue());
	}
}
