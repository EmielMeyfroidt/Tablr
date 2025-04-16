/**
 * 
 */
package test;

import static org.junit.Assert.*;
import org.junit.Test;
import main.TablrManager;
import java.util.List;
import java.util.UUID;

/**
 * 
 */
public class TablrManagerTest {
	
	@Test
	public void addTableTest() {
		TablrManager mgr = new TablrManager();
		mgr.addTable();
		assertEquals(1, mgr.getTableNames().size());
		for (int i = 0; i < 9; i++) {
			mgr.addTable();
		}
		assertEquals(10, mgr.getTableNames().size());
        assertEquals(mgr.getTableNames().stream().distinct().count(), mgr.getTableNames().size());
	}
	
	@Test
	public void removeTableTest() {
		TablrManager mgr = new TablrManager();
		UUID id1 = mgr.addTable();
		UUID id2 = mgr.addTable();
		assertEquals(2, mgr.getTableNames().size());
		assertTrue(mgr.getTableNames().contains("Table0"));
		assertTrue(mgr.getTableNames().contains("Table1"));
		mgr.removeTable(id1);
		assertEquals(1, mgr.getTableNames().size());
		assertFalse(mgr.getTableNames().contains("Table0"));
		assertTrue(mgr.getTableNames().contains("Table1"));
	}
	
	@Test
	public void changeNameTest() {
		TablrManager mgr = new TablrManager();
		UUID id1 = mgr.addTable();
		mgr.changeName(id1, "New Name");
		assertTrue(mgr.getTableNames().contains("New Name"));
		assertFalse(mgr.getTableNames().contains("Table0"));
	}

	@Test
	public void testAddColumn() {
		TablrManager mgr = new TablrManager();
		UUID id1 = mgr.addTable();
		mgr.addColumn(id1);

		assertEquals(1, mgr.getColumnNames(id1).size());
	}

	@Test
	public void testUpdateCell() {
		TablrManager mgr = new TablrManager();
		UUID id1 = mgr.addTable();
		mgr.addColumn(id1);
		mgr.addRow(id1);

		mgr.updateCell(id1, "Column0", 0, "SampleValue");
		assertEquals("SampleValue", mgr.getCell(id1, "Column0", 0));
	}

	@Test
	public void testAddRowToNonExistentTable() {
		TablrManager mgr = new TablrManager();
		assertThrows(Exception.class, () -> mgr.addRow(UUID.randomUUID()));
	}

	@Test
	public void testChangeAllowBlanks() {
		TablrManager mgr = new TablrManager();
		UUID id1 = mgr.addTable();
		mgr.addColumn(id1);

		mgr.changeAllowBlanks(id1, "Column0");
		// Assert internal state or behavior when blanks are disabled or enabled
	}

	@Test
	public void testGetColumnsInfo() {
		TablrManager mgr = new TablrManager();
		UUID id1 = mgr.addTable();

		mgr.addColumn(id1);
		mgr.addColumn(id1);


		List<String> columnInfo = mgr.getColumnsInfo(id1);
		assertEquals(2, columnInfo.size()); // Ensure two columns present
		assertTrue(columnInfo.getFirst().contains("Column0")); // Assert specific columns
		assertTrue(columnInfo.getLast().contains("Column1"));
	}

	@Test
	public void testChangeNameColumn() {
		TablrManager mgr = new TablrManager();
		UUID id1 = mgr.addTable();

		mgr.addColumn(id1); // Add a column
		mgr.changeNameColumn(id1, "Column0", "NewColumnName"); // Change its name

		List<String> columnNames = mgr.getColumnNames(id1);
		assertTrue(columnNames.contains("NewColumnName")); // Old name should be replaced
		assertFalse(columnNames.contains("Column0"));
	}

	@Test
	public void testRemoveColumn() {
		TablrManager mgr = new TablrManager();
		UUID id1 = mgr.addTable();

		mgr.addColumn(id1); // Add first column
		mgr.addColumn(id1); // Add second column

		mgr.removeColumn(id1, "Column0"); // Remove one column

		List<String> remainingColumns = mgr.getColumnNames(id1);
		assertEquals(1, remainingColumns.size()); // Only one column should remain
		assertFalse(remainingColumns.contains("Column0"));
		assertTrue(remainingColumns.contains("Column1"));
	}

	@Test
	public void testGetColumns() {
		TablrManager mgr = new TablrManager();
		UUID id1 = mgr.addTable();

		mgr.addColumn(id1); // Add a column
		mgr.addColumn(id1); // Add another column

		List<List<String>> columns = mgr.getColumns(id1);
		assertEquals(2, columns.size()); // Ensure two columns

		// Verify each column has no data initially
		assertTrue(columns.get(0).isEmpty());
		assertTrue(columns.get(1).isEmpty());
	}

	@Test
	public void testRemoveRow() {
		TablrManager mgr = new TablrManager();
		UUID id1 = mgr.addTable();

		mgr.addColumn(id1);
		mgr.addRow(id1); // Add first row
		mgr.addRow(id1); // Add second row

		mgr.removeRow(id1, 0); // Remove the first row

		// Verify rows are reduced and data is intact
		List<List<String>> columns = mgr.getColumns(id1);
		assertEquals(1, columns.get(0).size()); // Only one row should remain
	}

	@Test
	public void testChangeType() {
		TablrManager mgr = new TablrManager();
		UUID id1 = mgr.addTable();

		mgr.addColumn(id1); // Add a column

		// Assuming there's an observable type associated with the column, trigger the change:
		mgr.changeType(id1, "Column0");
		assertEquals(mgr.getClass(id1, "Column0"), "boolean");
	}
	
	@Test
	public void testSetDefaultValue() {
		TablrManager mgr = new TablrManager();
		UUID id1 = mgr.addTable();
		mgr.addColumn(id1);
		mgr.setDefaultValue(id1, "Column0", "Default");
		assertEquals(mgr.getDefaultValue(id1, "Column0"), "Default");
	}

}
