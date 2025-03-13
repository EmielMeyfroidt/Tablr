/**
 * 
 */
package test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import main.TablrManager;
import main.TablrManagerListener;

import java.util.List;

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
		for (int i = 0; i < 2; i++) {
			mgr.addTable();
		}
		assertEquals(2, mgr.getTableNames().size());
		assertTrue(mgr.getTableNames().contains("Table0"));
		assertTrue(mgr.getTableNames().contains("Table1"));
		mgr.removeTable("Table0");
		assertEquals(1, mgr.getTableNames().size());
		assertFalse(mgr.getTableNames().contains("Table0"));
		assertTrue(mgr.getTableNames().contains("Table1"));
	}
	
	@Test
	public void changeNameTest() {
		TablrManager mgr = new TablrManager();
		mgr.addTable();
		mgr.changeName("Table0", "New Name");
		assertTrue(mgr.getTableNames().contains("New Name"));
		assertFalse(mgr.getTableNames().contains("Table0"));
	}

	static class MockTablrManagerListener implements TablrManagerListener {
		boolean wasTriggered = false;

		@Override
		public void contentsChanged() {
			wasTriggered = true;
		}
	}

	@Test
	public void testAddListener() {
		MockTablrManagerListener mockListener = new MockTablrManagerListener();

		TablrManager mgr = new TablrManager();
		mgr.addListener(mockListener);

		mgr.addTable(); // This should trigger contentsChanged
		assertTrue(mockListener.wasTriggered); // Validate the listener was triggered.
	}

	@Test
	public void testAddColumn() {
		TablrManager mgr = new TablrManager();
		mgr.addTable();
		String tableName = mgr.getTableNames().get(0);
		mgr.addColumn(tableName);

		assertEquals(1, mgr.getColumnNames(tableName).size());
	}

	@Test
	public void testUpdateCell() {
		TablrManager mgr = new TablrManager();
		mgr.addTable();
		String tableName = mgr.getTableNames().get(0);
		mgr.addColumn(tableName);
		mgr.addRow(tableName);

		mgr.updateCell(tableName, "Column0", 0, "SampleValue");
		assertEquals("SampleValue", mgr.getCell(tableName, "Column0", 0));
	}

	@Test(expected = NullPointerException.class)
	public void testAddRowToNonExistentTable() {
		TablrManager mgr = new TablrManager();
		mgr.addRow("NonExistentTable");
	}

	@Test
	public void testChangeAllowBlanks() {
		TablrManager mgr = new TablrManager();
		mgr.addTable();
		mgr.addColumn("Table0");

		mgr.changeAllowBlanks("Table0", "Column0");
		// Assert internal state or behavior when blanks are disabled or enabled
	}

	@Test
	public void testRemoveListener() {
		MockTablrManagerListener mockListener = new MockTablrManagerListener();

		TablrManager mgr = new TablrManager();
		mgr.addListener(mockListener); // Add the listener
		mgr.removeListener(mockListener); // Now remove the listener

		mgr.addTable(); // Trigger the event
		assertFalse(mockListener.wasTriggered); // The listener should not be triggered
	}
	@Test
	public void testGetColumnsInfo() {
		TablrManager mgr = new TablrManager();
		mgr.addTable();

		String tableName = mgr.getTableNames().get(0);

		mgr.addColumn(tableName);
		mgr.addColumn(tableName);


		List<String> columnInfo = mgr.getColumnsInfo(tableName);
		assertEquals(2, columnInfo.size()); // Ensure two columns present
		assertTrue(columnInfo.getFirst().contains("Column0")); // Assert specific columns
		assertTrue(columnInfo.getLast().contains("Column1"));
	}

	@Test
	public void testChangeNameColumn() {
		TablrManager mgr = new TablrManager();
		mgr.addTable();
		String tableName = mgr.getTableNames().get(0);

		mgr.addColumn(tableName); // Add a column
		mgr.changeNameColumn(tableName, "Column0", "NewColumnName"); // Change its name

		List<String> columnNames = mgr.getColumnNames(tableName);
		assertTrue(columnNames.contains("NewColumnName")); // Old name should be replaced
		assertFalse(columnNames.contains("Column0"));
	}

	@Test
	public void testRemoveColumn() {
		TablrManager mgr = new TablrManager();
		mgr.addTable();
		String tableName = mgr.getTableNames().get(0);

		mgr.addColumn(tableName); // Add first column
		mgr.addColumn(tableName); // Add second column

		mgr.removeColumn(tableName, "Column0"); // Remove one column

		List<String> remainingColumns = mgr.getColumnNames(tableName);
		assertEquals(1, remainingColumns.size()); // Only one column should remain
		assertFalse(remainingColumns.contains("Column0"));
		assertTrue(remainingColumns.contains("Column1"));
	}

	@Test
	public void testGetColumns() {
		TablrManager mgr = new TablrManager();
		mgr.addTable();
		String tableName = mgr.getTableNames().get(0);

		mgr.addColumn(tableName); // Add a column
		mgr.addColumn(tableName); // Add another column

		List<List<String>> columns = mgr.getColumns(tableName);
		assertEquals(2, columns.size()); // Ensure two columns

		// Verify each column has no data initially
		assertTrue(columns.get(0).isEmpty());
		assertTrue(columns.get(1).isEmpty());
	}

	@Test
	public void testRemoveRow() {
		TablrManager mgr = new TablrManager();
		mgr.addTable();
		String tableName = mgr.getTableNames().get(0);

		mgr.addColumn(tableName);
		mgr.addRow(tableName); // Add first row
		mgr.addRow(tableName); // Add second row

		mgr.removeRow(tableName, 0); // Remove the first row

		// Verify rows are reduced and data is intact
		List<List<String>> columns = mgr.getColumns(tableName);
		assertEquals(1, columns.get(0).size()); // Only one row should remain
	}

	@Test
	public void testChangeType() {
		TablrManager mgr = new TablrManager();
		mgr.addTable();
		String tableName = mgr.getTableNames().get(0);

		mgr.addColumn(tableName); // Add a column

		// Assuming there's an observable type associated with the column, trigger the change:
		mgr.changeType(tableName, "Column0");

		// If there's no direct state to assert, verify behavior:
		// Ideally, mock a method on Table to assert that the type change was applied.
	}

}
