package test;

import canvaswindow.CanvasWindow;
import main.*;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

public class FlowTests {

	private void replaySession(TablrManager mgr, String sessionFile) {
		ViewManager view = new ViewManager(mgr);
		replaySession(view, sessionFile);
	}

	private void replaySession(ViewManager view, String sessionFile) {
		CountDownLatch latch = new CountDownLatch(1);
		java.awt.EventQueue.invokeLater(() -> {
			MyCanvasWindow window = new MyCanvasWindow("My Canvas Window", view);
			window.show();

			CanvasWindow.replayRecording(sessionFile, window);
			latch.countDown();
		});
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	@Test
	public void createTable() {
		TablrManager mgr = new TablrManager();
		replaySession(mgr, "SessionRecordings/createTable/createTable");
		assertTrue(mgr.getTableNames().size() == 1);
	}

	@Test
	public void undoRedoCreateTable() {
		TablrManager mgr = new TablrManager();
		ViewManager view = new ViewManager(mgr);
		replaySession(view, "SessionRecordings/createTable/createTable");
		assertTrue(mgr.getTableNames().size() == 1);
		//undo
		view.handleCtrlZ();
		assertTrue(mgr.getTableNames().size() == 0);
		//redo
		view.handleCtrlShiftZ();
		assertTrue(mgr.getTableNames().size() == 1);
	}

	@Test
	public void editTableName() {
		TablrManager mgr = new TablrManager();

		replaySession(mgr, "SessionRecordings/createTable/createTable");
		mgr = new TablrManager();
		replaySession(mgr, "SessionRecordings/editTableName/editTableName");
		assertEquals("renamed", mgr.getTableNames().get(0));
		assertTrue(mgr.getTableNames().size() == 1);
	}

	@Test
	public void undoRedoEditTableName() {
		TablrManager mgr = new TablrManager();

		replaySession(mgr, "SessionRecordings/createTable/createTable");
		String oldname = mgr.getTableNames().get(0);
		mgr = new TablrManager();
		replaySession(mgr, "SessionRecordings/editTableName/editTableName");
		assertEquals("renamed", mgr.getTableNames().get(0));
		assertTrue(mgr.getTableNames().size() == 1);
		replaySession(mgr, "SessionRecordings/undoEditTableName/undoEditTableName");
		System.out.println(mgr.getTableNames());
		assertEquals(oldname, mgr.getTableNames().get(0));
		replaySession(mgr, "SessionRecordings/redoEditTableName/redoEditTableName");
		System.out.println(mgr.getTableNames());
		assertEquals("renamed", mgr.getTableNames().get(0));

	}

	@Test
	public void undoRedoEditTableName2() {
		TablrManager mgr = new TablrManager();

		replaySession(mgr, "SessionRecordings/createTable/createTable");
		mgr = new TablrManager();
		ViewManager view = new ViewManager(mgr);

		replaySession(view, "SessionRecordings/editTableName/editTableName");
		assertEquals("renamed", mgr.getTableNames().get(0));
		assertTrue(mgr.getTableNames().size() == 1);

		view.handleCtrlZ();
		assertEquals("rename", mgr.getTableNames().get(0));

		view.handleCtrlShiftZ();
		assertEquals("renamed", mgr.getTableNames().get(0));

	}

	@Test
	public void deleteTable() {
		TablrManager mgr = new TablrManager();

		replaySession(mgr, "SessionRecordings/createMultipleTables/createMultipleTables");
		List<String> tables = mgr.getTableNames();
//		delete table 1 and 4
		mgr = new TablrManager();
		replaySession(mgr, "SessionRecordings/deleteMultipleTables/deleteMultipleTables");
		tables.remove(0);
		tables.remove(2);
		assertEquals(mgr.getTableNames(), tables);

	}

	@Test
	public void undoRedoDeleteTable() {
		TablrManager mgr = new TablrManager();

		replaySession(mgr, "SessionRecordings/createMultipleTables/createMultipleTables");
		List<String> tables = mgr.getTableNames();
		List<String> originalTables = mgr.getTableNames();

//		delete table 1 and 4
		mgr = new TablrManager();
		ViewManager view = new ViewManager(mgr);

		replaySession(view, "SessionRecordings/deleteMultipleTables/deleteMultipleTables");
		tables.remove(0);
		tables.remove(2);
		assertEquals(mgr.getTableNames(), tables);

//		undo
		view.handleCtrlZ();
		view.handleCtrlZ();
		List<String> newTables = mgr.getTableNames();
		Collections.sort(newTables);
		assertArrayEquals(originalTables.toArray(), newTables.toArray());

//				redo
		view.handleCtrlShiftZ();
		view.handleCtrlShiftZ();
		newTables = mgr.getTableNames();
		Collections.sort(newTables);
		assertArrayEquals(tables.toArray(), newTables.toArray());

	}

	@Test
	public void openTable() {
//		TablrManager mgr = new TablrManager();
//		replaySession(mgr, "SessionRecordings/openTable/openTable");
//		not much to test
	}

	@Test
	public void addColumn() {
		TablrManager mgr = new TablrManager();
		replaySession(mgr, "SessionRecordings/addColumn/addColumn");
		assertTrue(mgr.getData().get("Table0").containsKey("Column0"));
	}

	@Test
	public void undoRedoAddColumn() {
		TablrManager mgr = new TablrManager();
		ViewManager view = new ViewManager(mgr);
		replaySession(view, "SessionRecordings/addColumn/addColumn");
		assertTrue(mgr.getData().get("Table0").containsKey("Column0"));
		view.handleCtrlZ();
		assertFalse(mgr.getData().get("Table0").containsKey("Column0"));
		view.handleCtrlShiftZ();
		assertTrue(mgr.getData().get("Table0").containsKey("Column0"));

	}

	@Test
	public void editColumnCharacteristic() {
		TablrManager mgr = new TablrManager();

		replaySession(mgr, "SessionRecordings/editColumnCharacteristic/editColumnCharacteristic");
		assertTrue(mgr.getData().get("Table0").keySet().contains("renamed"));
	}

	@Test
	public void deleteColumn() {
		TablrManager mgr = new TablrManager();
		replaySession(mgr, "SessionRecordings/addMultipleColumns/addMultipleColumns");
		Set<String> columns = mgr.getData().get("Table0").keySet();
		// delete column index 1 and 4
		columns.remove("Column0");
		columns.remove("Column3");
		mgr = new TablrManager();
		replaySession(mgr, "SessionRecordings/deleteMultipleColumns/deleteMultipleColumns");
		assertTrue(mgr.getData().get("Table0").keySet().equals(columns));
	}

	@Test
	public void addRow() {
		TablrManager mgr = new TablrManager();
		replaySession(mgr, "SessionRecordings/addRow/addRow");

		// Get the data map for "Table0"
		Map<String, List<String>> tableData = mgr.getData().get("Table0");

		// Assert that "Table0" exists and contains column(s)
		assertTrue("Table0 should exist in the data map", mgr.getData().containsKey("Table0"));
		assertNotNull("The data for Table0 should not be null", tableData);

		for (Map.Entry<String, List<String>> column : tableData.entrySet()) {
			assertTrue("row of" + column.getKey() + " should not be empty after adding a row",
					!column.getValue().isEmpty());
		}
	}

	@Test
	public void undoRedoAddRow() {
		TablrManager mgr = new TablrManager();
		ViewManager view = new ViewManager(mgr);
		replaySession(view, "SessionRecordings/addRow/addRow");

		// Get the data map for "Table0"
		Map<String, List<String>> tableData = mgr.getData().get("Table0");

		// Assert that "Table0" exists and contains column(s)
		assertTrue("Table0 should exist in the data map", mgr.getData().containsKey("Table0"));
		assertNotNull("The data for Table0 should not be null", tableData);

		for (Map.Entry<String, List<String>> column : tableData.entrySet()) {
			assertTrue("row of" + column.getKey() + " should not be empty after adding a row",
					!column.getValue().isEmpty());
		}
		view.handleCtrlZ();
		tableData = mgr.getData().get("Table0");
		for (Map.Entry<String, List<String>> column : tableData.entrySet()) {
			assertTrue("row of" + column.getKey() + " should be empty after undoing adding a row",
					column.getValue().isEmpty());
		}
		view.handleCtrlShiftZ();
		tableData = mgr.getData().get("Table0");
		for (Map.Entry<String, List<String>> column : tableData.entrySet()) {
			assertTrue("row of" + column.getKey() + " should be empty after redoing adding a row",
					!column.getValue().isEmpty());
		}
	}

	@Test
	public void editRowValue() {
		TablrManager mgr = new TablrManager();
		replaySession(mgr, "SessionRecordings/editRowValue/editRowValue");

		// Get the data map for "Table0"
		Map<String, List<String>> tableData = mgr.getData().get("Table0");

		// Assert that "Table0" exists and contains column(s)
		assertTrue("Table0 should exist in the data map", mgr.getData().containsKey("Table0"));
		assertNotNull("The data for Table0 should not be null", tableData);

		assertTrue(tableData.get("Column0").get(0).equals("edited"));

	}

	@Test
	public void undoRedoEditRowValue() {
		TablrManager mgr = new TablrManager();
		ViewManager view = new ViewManager(mgr);
		replaySession(view, "SessionRecordings/editRowValue/editRowValue");

		// Get the data map for "Table0"
		Map<String, List<String>> tableData = mgr.getData().get("Table0");

		// Assert that "Table0" exists and contains column(s)
		assertTrue("Table0 should exist in the data map", mgr.getData().containsKey("Table0"));
		assertNotNull("The data for Table0 should not be null", tableData);

		assertTrue(tableData.get("Column0").get(0).equals("edited"));

//		undo
		view.handleCtrlZ();
		tableData = mgr.getData().get("Table0");
		assertTrue(tableData.get("Column0").get(0).equals("edite"));
//		redo
		view.handleCtrlShiftZ();
		tableData = mgr.getData().get("Table0");
		assertTrue(tableData.get("Column0").get(0).equals("edited"));


	}

	@Test
	public void deleteRow() {

		TablrManager mgr = new TablrManager();
		replaySession(mgr, "SessionRecordings/deleteRow/deleteRow");

		// Get the data map for "Table0"
		Map<String, List<String>> tableData = mgr.getData().get("Table0");

		// Assert that "Table0" exists and contains column(s)
		assertTrue("Table0 should exist in the data map", mgr.getData().containsKey("Table0"));
		assertNotNull("The data for Table0 should not be null", tableData);

		for (Map.Entry<String, List<String>> column : tableData.entrySet()) {
			assertTrue("size" + column.getKey() + " should be 1 after deleting rows", column.getValue().size() == 1);
		}

	}

	@Test
	public void undoRedoDeleteRow() {

		TablrManager mgr = new TablrManager();
		ViewManager view = new ViewManager(mgr);
		replaySession(view, "SessionRecordings/deleteRow/deleteRow");

		// Get the data map for "Table0"
		Map<String, List<String>> tableData = mgr.getData().get("Table0");

		// Assert that "Table0" exists and contains column(s)
		assertTrue("Table0 should exist in the data map", mgr.getData().containsKey("Table0"));
		assertNotNull("The data for Table0 should not be null", tableData);

		for (Map.Entry<String, List<String>> column : tableData.entrySet()) {
			assertTrue("size" + column.getKey() + " should be 1 after deleting rows", column.getValue().size() == 1);
		}

//		undo
		view.handleCtrlZ();
		tableData = mgr.getData().get("Table0");
		for (Map.Entry<String, List<String>> column : tableData.entrySet()) {
			assertTrue("size" + column.getKey() + " should be 2 after undo", column.getValue().size() == 2);
		}
//		redo
		view.handleCtrlShiftZ();
		tableData = mgr.getData().get("Table0");
		for (Map.Entry<String, List<String>> column : tableData.entrySet()) {
			assertTrue("size" + column.getKey() + " should be 1 after redo", column.getValue().size() == 1);
		}
	}

	@Test
	public void editDefaultValue() {
		TablrManager mgr = new TablrManager();
		replaySession(mgr, "SessionRecordings/editDefaultValue/editDefaultValue");

		// Get the data map for "Table0"
		Map<String, List<String>> tableData = mgr.getData().get("Table0");

		// Assert that "Table0" exists and contains column(s)
		assertTrue("Table0 should exist in the data map", mgr.getData().containsKey("Table0"));
		assertNotNull("The data for Table0 should not be null", tableData);

		System.out.println(mgr.getData().get("Table0"));
		UUID tableId = mgr.getTableIds().get(0);
		String defaultValue = mgr.getDefaultValue(tableId, "Column0").toString();
		assertEquals(defaultValue, "abc");

	}

	@Test
	public void undoRedoEditDefaultValue() {
		TablrManager mgr = new TablrManager();
		ViewManager view = new ViewManager(mgr);
		replaySession(view, "SessionRecordings/editDefaultValue/editDefaultValue");

		// Get the data map for "Table0"
		Map<String, List<String>> tableData = mgr.getData().get("Table0");

		// Assert that "Table0" exists and contains column(s)
		assertTrue("Table0 should exist in the data map", mgr.getData().containsKey("Table0"));
		assertNotNull("The data for Table0 should not be null", tableData);

		UUID tableId = mgr.getTableIds().get(0);
		String defaultValue = mgr.getDefaultValue(tableId, "Column0").toString();
		assertEquals(defaultValue, "abc");

//		undo
		view.handleCtrlZ();
		defaultValue = mgr.getDefaultValue(tableId, "Column0").toString();
		assertEquals(defaultValue, "ab");
//		redo
		view.handleCtrlShiftZ();
		defaultValue = mgr.getDefaultValue(tableId, "Column0").toString();
		assertEquals(defaultValue, "abc");
	}

}
