package test;

import static org.junit.Assert.*;

import main.Column;
import org.junit.Test;

import java.util.List;

public class ColumnTest {

	@Test
	public void testConstructorAndGetInfo() {
		Column column = new Column("TestColumn", "string", true, "Default");

		assertEquals("TestColumn", column.getName());
		assertEquals("string", column.getType());
		assertEquals(column.getInfo(),("TestColumn string true Default"));
	}

	@Test
	public void testAddCellAndSize() {
		Column column = new Column("NumberColumn", "int", false, "42");

		assertEquals(0, column.getSize());

		column.addCell();
		assertEquals(1, column.getSize());

		column.addCell();
		assertEquals(2, column.getSize());
	}

	@Test
	public void testRemoveCell() {
		Column column = new Column("StringColumn", "string", true, "Default");

		column.addCell();
		column.addCell();
		assertEquals(2, column.getSize());

		column.removeRow(0);
		assertEquals(1, column.getSize());
	}

	@Test
	public void testGetAndSetCell() {
		Column column = new Column("StringColumn", "string", false, "");

		column.addCell();
		column.updateCell(0, "stringValue");
		assertEquals("stringValue", column.getCell(0));
	}

	@Test
	public void testChangeAllowsBlanks() {
		Column column = new Column("BlankColumn", "string", false, "Default");

		assertFalse(column.getInfo().contains("true"));

		column.changeAllowBlanks();
		assertTrue(column.getInfo().contains("true"));

		column.changeAllowBlanks();
		assertFalse(column.getInfo().contains("true"));
	}

	@Test
	public void testSetName() {
		Column column = new Column("OriginalName", "string", true, "Default");

		column.setName("NewName");
		assertEquals("NewName", column.getName());
	}

	@Test
	public void testGetColumnContent() {
		Column column = new Column("BooleanColumn", "bool", false, "false");

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
		Column column = new Column("DefaultTest", "string", true, "DefaultValue");

		column.addCell();
		assertEquals("DefaultValue", column.getCell(0));
	}
	
	@Test
	public void testUpdateCellForDifferentTypes() {
		Column string = new Column("String", "string", true, "DefaultValue");
		string.addCell();
		string.updateCell(0, "UpdatedValue");
		assertEquals(string.getCell(0), "UpdatedValue");
		Column bool = new Column("Bool", "bool", true, "true");
		bool.addCell();
		bool.updateCell(0, "false");
		assertEquals(bool.getCell(0), "false");
		Column integer = new Column("Integer", "int", true, "0");
		integer.addCell();
		integer.updateCell(0, "1");
		assertEquals(integer.getCell(0), "1");
	}
	
	@Test
	public void testSetDefaultValueForDifferentTypes() {
		Column string = new Column("String", "string", true, "DefaultValue");
		string.addCell();
		string.setDefaultValue("UpdatedValue");
		assertEquals(string.getDefaultValue(), "UpdatedValue");
		Column bool = new Column("Bool", "bool", true, "true");
		bool.addCell();
		bool.setDefaultValue("false");
		assertEquals(bool.getDefaultValue(), "false");
		Column integerCol = new Column("Integer", "int", true, "0");
		integerCol.addCell();
		integerCol.setDefaultValue("1");
		assertEquals("1", integerCol.getDefaultValue());
	}
}
