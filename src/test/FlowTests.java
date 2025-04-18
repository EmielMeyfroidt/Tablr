package test;

import canvaswindow.CanvasWindow;
import main.*;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;


public class FlowTests {

	private void replaySession(TablrManager mgr, String sessionFile) {
		ViewManager view = new ViewManager(mgr);
		replaySession(mgr, view, sessionFile);
	}

	private void replaySession(TablrManager mgr, ViewManager view, String sessionFile) {
		CountDownLatch latch = new CountDownLatch(1);
		java.awt.EventQueue.invokeLater(() -> {
			MyCanvasWindow window = new MyCanvasWindow("My Canvas Window", view);
			view.addListener(window.getTablrManagerListener());
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
	public void test() throws InterruptedException {
//		replaySession("SessionRecordings/test");
	}

	@Test
	public void createTable() {
		TablrManager mgr = new TablrManager();
		replaySession(mgr, "SessionRecordings/createTable/createTable");
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
		//delete column index 1 and 4
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
			assertTrue("row of" + column.getKey() + " should not be empty after adding a row", !column.getValue().isEmpty());
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

}

