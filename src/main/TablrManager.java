package main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//Manage tables and their contents

/**
 * TablrManager is responsible for managing a collection of tables and their corresponding operations.
 * It provides methods to manipulate tables, columns, and rows, as well as notifying listeners about changes.
 */
public class TablrManager {
	private List<Table> tables;
	private List<paintListener> listeners;

	/**
	 * Constructs a new instance of the TablrManager class.
	 * <p>
	 * This constructor initializes the TablrManager with an empty list of tables
	 * and listeners. It also notifies registered listeners of a change in the
	 * state by invoking the `fireContentsChanged` method.
	 */
	public TablrManager() {
		this.tables = new ArrayList<Table>();
		listeners = new ArrayList<paintListener>();
		this.fireContentsChanged();
	}

	/**
	 * Registers a new listener to the TablrManager. The listener will be notified
	 * of content changes in the TablrManager.
	 *
	 * @param listener The listener to be added. Must implement the TablrManagerListener interface.
	 */
	public void addListener(paintListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes a listener from the TablrManager. The listener will no longer be notified
	 * of content changes in the TablrManager.
	 *
	 * @param listener The listener to be removed. Must implement the TablrManagerListener interface.
	 */
	public void removeListener(paintListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Notifies all registered listeners that the contents of the TablrManager
	 * have been modified. This method iterates through the list of TablrManagerListener
	 * instances and invokes their {@link paintListener#contentsChanged()} method.
	 */
	private void fireContentsChanged() {
		for (paintListener listener : listeners) {
			listener.contentsChanged();
		}
	}

	/**
	 * Adds a table, generates a unique name.
	 */
	public void addTable() {
		String uniqueName = generateUniqueName();
		Table newTable = new Table(uniqueName);
		tables.add(newTable);
		fireContentsChanged();
	}

	/**
	 * Removes a table with the specified name from the list of tables managed by the TablrManager.
	 * If a table with the specified name exists, it is removed, and all registered listeners
	 * are notified of the content change.
	 *
	 * @param table The name of the table to be removed.
	 */
	public void removeTable(String table) {
		tables.removeIf(t -> t.getName().equals(table));
		fireContentsChanged();
	}

	/**
	 * Retrieves a list of table names managed by the TablrManager.
	 *
	 * @return a list of strings representing the names of all tables.
	 */
	public List<String> getTableNames() {
		return tables.stream().map(t -> t.getName()).collect(Collectors.toList());
	}

	/**
	 * Generates a unique name for a new table.
	 *
	 * @return a unique string representing a new table name.
	 */
	private String generateUniqueName() {
		int n = 0;
		while (getTableNames().contains("Table" + n)) {
			n++;
		}
		return "Table" + n;
	}

	/**
	 * @param tableName The name of the table.
	 * @return A list with the info of the columns.
	 */
	public List<String> getColumnsInfo(String tableName) {
		Table t = findTable(tableName);
		return t.getColumnsInfo();
	}

	/**
	 * @param table   The old name of the table.
	 * @param newName The new name of the table.
	 */
	public void changeName(String table, String newName) {
		Table t = findTable(table);
		t.setName(newName);
		fireContentsChanged();
	}

	/**
	 * @param table   The name of the table.
	 * @param newName The new name of the table.
	 */
	public void changeNameColumn(String table, String column, String newName) {
		Table t = findTable(table);
		t.renameColumn(column, newName);
		fireContentsChanged();
	}

	/**
	 * @param table The name of the table.
	 */
	public void addColumn(String table) {
		Table t = findTable(table);
		t.addColumn();
		fireContentsChanged();
	}

	/**
	 * @param table The name of the table.
	 * @return A list of all the names of the columns.
	 */
	public List<String> getColumnNames(String table) {
		Table t = findTable(table);
		return t.getColumnNames();
	}

	/**
	 * @param table The name of the table wherein the column is.
	 * @param c     The name of the column to remove.
	 */
	public void removeColumn(String table, String c) {
		Table t = findTable(table);
		t.removeColumn(c);
		fireContentsChanged();
	}

	/**
	 * Retrieves all columns of the specified table.
	 *
	 * @param table The name of the table from which the columns are to be retrieved.
	 * @return A list of lists where each inner list contains the values of a single column.
	 */
	public List<List<String>> getColumns(String table) {
		Table t = findTable(table);
		return t.getColumns();
	}

	/**
	 * Adds a new row to the specified table, identified by its name. Once the row
	 * is added, all registered listeners are notified of the change.
	 *
	 * @param table The name of the table where a new row will be added.
	 */
	public void addRow(String table) {
		Table t = findTable(table);
		t.addRow();
		fireContentsChanged();
	}

	/**
	 * Removes a row from the specified table and notifies listeners of the change.
	 *
	 * @param table The name of the table from which to remove the row.
	 * @param row   The index of the row to be removed.
	 */
	public void removeRow(String table, int row) {
		Table t = findTable(table);
		t.removeRow(row);
		fireContentsChanged();
	}

	/**
	 * Updates the value of a cell in a specified table and column at the given row index.
	 * After updating the cell, this method notifies all registered listeners about the change.
	 *
	 * @param nameTable  The name of the table containing the cell to update.
	 * @param nameColumn The name of the column containing the cell to update.
	 * @param rowIndex   The index of the row containing the cell to update.
	 * @param value      The new value to set in the specified cell.
	 */
	public void updateCell(String nameTable, String nameColumn, Integer rowIndex, String value) {
		Table t = findTable(nameTable);
		t.updateCell(nameColumn, rowIndex, value);
		fireContentsChanged();
	}

	/**
	 * Retrieves the value of a specific cell in a table.
	 *
	 * @param nameTable  The name of the table containing the desired cell.
	 * @param nameColumn The name of the column containing the desired cell.
	 * @param rowIndex   The index of the row containing the desired cell.
	 * @return The value of the cell as a String.
	 */
	public String getCell(String nameTable, String nameColumn, Integer rowIndex) {
		Table t = findTable(nameTable);
		return t.getCell(nameColumn, rowIndex);

	}

	/**
	 * Toggles the ability to allow blank values in a specific column of a specified table.
	 * This method updates the blank allowance setting for the given column and notifies
	 * all registered listeners about the change.
	 *
	 * @param tableName  The name of the table containing the column to update.
	 * @param columnName The name of the column whose blank allowance setting will be changed.
	 */
	public void changeAllowBlanks(String tableName, String columnName) {
		Table t = findTable(tableName);
		t.changeAllowBlanks(columnName);
		fireContentsChanged();
	}

	/**
	 * Searches for a table with the specified name in the list of managed tables.
	 *
	 * @param tableName The name of the table to search for.
	 * @return The Table object that matches the specified name,
	 * or null if no table with the given name is found.
	 */
	private Table findTable(String tableName) {
		for (Table t : tables) {
			if (t.getName().equals(tableName)) {
				return t;
			}
		}
		return null;
	}

	/**
	 * Changes the type of the specified column in the specified table.
	 * This method locates the table by its name, updates the type of the given column,
	 * and notifies all registered listeners of the change.
	 *
	 * @param table  The name of the table containing the column whose type is to be changed.
	 * @param column The name of the column whose type will be modified.
	 */
	public void changeType(String table, String column) {
		Table t = findTable(table);
		t.changeType(column);
		fireContentsChanged();
	}

	/**
	 * Retrieves the default value of a specific column in a specified table.
	 *
	 * @param table  The name of the table containing the column.
	 * @param column The name of the column for which the default value is to be retrieved.
	 * @return The default value of the specified column in the table, or null if no default value is set.
	 */
	public Object getDefaultValue(String table, String column) {
		Table t = findTable(table);
		return t.getDefaultValue(column);
	}

	/**
	 * Retrieves the class type of the specified column in the specified table.
	 *
	 * @param table  The name of the table containing the column.
	 * @param column The name of the column whose class type is to be retrieved.
	 * @return The Class object representing the type of the specified column in the table.
	 */
	public String getClass(String table, String column) {
		Table t = findTable(table);
		return t.getClass(column);
	}

	/**
	 * Sets the default value for a specified column in a specified table.
	 * This method locates the table by its name, updates the default value
	 * of the given column, and notifies all registered listeners of the change.
	 *
	 * @param table  The name of the table containing the column.
	 * @param column The name of the column for which the default value is to be set.
	 * @param value  The default value to assign to the specified column.
	 */
	public void setDefaultValue(String table, String column, String value) {
		Table t = findTable(table);
		t.setDefaultValue(column, value);
		fireContentsChanged();
	}
}
