/**
 * 
 */
package test;

import static org.junit.Assert.*;

import org.junit.Test;

import main.Column;
import main.Table;
/**
 * 
 */
public class TableTest {

	@Test
	public void addColumnTest() {
		Table t = new Table("name");
		assertEquals(t.getName(), "name");
		t.addColumn(t.newColumn());
		assertEquals(t.getColumns().size(), 1);
		for (int i = 0; i < 9; i++) {
			t.addColumn(t.newColumn());
		}
		assertEquals(t.getColumnNames().size(), 10);
		assertTrue(t.getColumnNames().stream().distinct().count() == t.getColumnNames().size());
	}

	@Test
	public void removeColumnTest() {
		Table t = new Table("name");
		for (int i = 0; i < 2; i++) {
			t.addColumn(t.newColumn());
		}
		assertEquals(t.getColumnNames().size(), 2);
		assertTrue(t.getColumnNames().contains("Column0"));
		assertTrue(t.getColumnNames().contains("Column1"));
		t.removeColumn("Column0");
		assertEquals(t.getColumnNames().size(), 1);
		assertFalse(t.getColumnNames().contains("Column0"));
		assertTrue(t.getColumnNames().contains("Column1"));

	}

	@Test
	public void renameColumnTest() {
		Table t = new Table("name");
		t.addColumn(t.newColumn());
		t.renameColumn(t.getColumnNames().get(0), "newName");
		assertEquals(t.getColumnNames().get(0), "newName");
		t.addColumn(t.newColumn());
		t.renameColumn(t.getColumnNames().get(1), "newName");
		assertEquals(t.getColumnNames().get(1), "Column0");
		t.renameColumn(t.getColumnNames().get(1), null);
		assertEquals(t.getColumnNames().get(1), "Column0");
	}

	@Test
	public void changeColumnTest() {
		Table t = new Table("name");
		t.addColumn(t.newColumn());
		Column col = new Column("newCol", "string", false, "default");
		assertEquals(t.getColumnNames().get(0), "Column0");
		t.changeColumn("Column0", col);
		assertEquals(t.getColumnNames().get(0), "newCol");
	}

	@Test
	public void addRowTest() {
		Table t = new Table("name");
		t.addColumn(t.newColumn());
		t.addRow();
		assertEquals(t.getCell("Column0", 0), t.getDefaultValue("Column0"));
		t.addColumn(t.newColumn());
		t.addRow();
		assertEquals(t.getCell("Column0", 0), t.getDefaultValue("Column0"));
		assertEquals(t.getCell("Column1", 0), t.getDefaultValue("Column1"));
		assertEquals(t.getCell("Column0", 1), t.getDefaultValue("Column0"));
		assertEquals(t.getCell("Column1", 1), t.getDefaultValue("Column1"));
		
	}

	@Test
	public void removeRowTest() {
		Table t = new Table("name");
		t.addColumn(t.newColumn());
		t.addRow();
		t.addColumn(t.newColumn());
		t.addRow();
		t.removeRow(1);
		assertThrows(IndexOutOfBoundsException.class, () -> t.getCell("Column0", 1));
		assertEquals(t.getCell("Column0", 0), t.getDefaultValue("Column0"));
	}

	@Test
	public void updateCellTest() {
		Table t = new Table("name");
		t.addColumn(t.newColumn());
		t.addRow();
		String cell = t.getCell("Column0", 0);
		t.updateCell("Column0", 0, "newValue");
		assertNotEquals(t.getCell("Column0", 0), cell);
		assertEquals(t.getCell("Column0", 0), "newValue");
	}

	@Test
	public void changeAllowBlanksTest() {
		Table t = new Table("name");
		t.addColumn(t.newColumn());
		String[] parts = t.getColumnsInfo().get(0).split(" ");
		String allowBlanks = parts[2];
		assertEquals(allowBlanks, String.valueOf(true));
		t.changeAllowBlanks("Column0");
		String[] parts2 = t.getColumnsInfo().get(0).split(" ");
		String allowBlanks2 = parts2[2];
		assertEquals(allowBlanks2, String.valueOf(false));
	}

	@Test
	public void changeTypeTest() {
		Table t = new Table("name");
		t.addColumn(t.newColumn());
		assertEquals(t.getClass("Column0"), "string");
		t.changeType("Column0");
		assertEquals(t.getClass("Column0"), "boolean");
		t.changeType("Column0");
		assertEquals(t.getClass("Column0"), "int");
		t.changeType("Column0");
		assertEquals(t.getClass("Column0"), "string");
	}

	@Test
	public void setDefaultValueTest() {
		Table t = new Table("name");
		t.addColumn(t.newColumn());
		assertEquals(t.getDefaultValue("Column0"), "x");
		t.setDefaultValue("Column0", "Default");
		assertEquals(t.getDefaultValue("Column0"), "Default");

	}
	
	@Test
	public void getWrongColumnTest() {
		Table t = new Table("name");
		assertThrows(Exception.class, () -> t.changeAllowBlanks("notColumn"));
	}

}
