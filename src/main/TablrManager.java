package main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//Manage tables and their contents
public class TablrManager {
	private List<Table> tables;
	private List<TablrManagerListener> listeners;

	/**
	 * 
	 */
	public TablrManager() {
		this.tables = new ArrayList<Table>();
		listeners = new ArrayList<TablrManagerListener>();
		this.fireContentsChanged();
	}

	/**
	 * 
	 * @param listener
	 */
	public void addListener(TablrManagerListener listener) {
		listeners.add(listener);
	}

	/**
	 * 
	 * @param listener
	 */
	public void removeListener(TablrManagerListener listener) {
		listeners.remove(listener);
	}

	/**
	 * 
	 */
	private void fireContentsChanged() {
		for (TablrManagerListener listener : listeners) {
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
	 * 
	 * @param table The name of the table.
	 */
	public void removeTable(String table) {
		tables.removeIf(t -> t.getName().equals(table));
		fireContentsChanged();
	}

	/**
	 * 
	 * @return A list with all the table names.
	 */
	public List<String> getTableNames() {
		return tables.stream().map(t -> t.getName()).collect(Collectors.toList());
	}

	private String generateUniqueName() {
		int n = 0;
		while (getTableNames().contains("Table" + n)) {
			n++;
		}
		return "Table" + n;
	}

	/**
	 * 
	 * @param tableName The name of the table.
	 * @return A list with the info of the columns.
	 */
	public List<String> getColumnsInfo(String tableName) {
		Table t = findTable(tableName);
		return t.getColumnsInfo();
	}

	/**
	 * 
	 * @param table   The old name of the table.
	 * @param newName The new name of the table.
	 */
	public void changeName(String table, String newName) {
		Table t = findTable(table);
		t.setName(newName);
		fireContentsChanged();
	}

	/**
	 *
	 * @param table   The name of the table.
	 * @param newName The new name of the table.
	 */
	public void changeNameColumn(String table, String column, String newName) {
		Table t = findTable(table);
		t.renameColumn(column, newName);
		fireContentsChanged();
	}

	/**
	 * 
	 * @param table The name of the table.
	 */
	public void addColumn(String table) {
		Table t = findTable(table);
		t.addColumn();
		fireContentsChanged();
	}

	/**
	 * 
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

	public List<List<String>> getColumns(String table) {
		Table t = findTable(table);
		return t.getColumns();
	}

	public void addRow(String table) {
		Table t = findTable(table);
		t.addRow();
		fireContentsChanged();
	}

	public void removeRow(String table, int row) {
		Table t = findTable(table);
		t.removeRow(row);
		fireContentsChanged();
	}

	public void updateCell(String nameTable, String nameColumn, Integer rowIndex, String value) {
		Table t = findTable(nameTable);
		t.updateCell(nameColumn, rowIndex, value);
		fireContentsChanged();
	}

	public String getCell(String nameTable, String nameColumn, Integer rowIndex) {
		Table t = findTable(nameTable);
		return t.getCell(nameColumn, rowIndex);

	}

	public void changeAllowBlanks(String tableName, String columnName) {
		Table t = findTable(tableName);
		t.changeAllowBlanks(columnName);
		fireContentsChanged();
	}

	private Table findTable(String tableName) {
		for (Table t : tables) {
			if (t.getName().equals(tableName)) {
				return t;
			}
		}
		return null;
	}

	/**
	 * @param table
	 * @param string
	 */
	public void changeType(String table, String column) {
		Table t = findTable(table);
		t.changeType(column);
		fireContentsChanged();
	}
	
	public Object getDefaultValue(String table, String column) {
		Table t = findTable(table);
		return t.getDefaultValue(column);
	}

	/**
	 * @param table
	 * @param column
	 * @return
	 */
	public Class<?> getClass(String table, String column) {
		Table t = findTable(table);
		return t.getClass(column);
	}

	/**
	 * @param table
	 * @param column
	 * @param value
	 */
	public void setDefaultValue(String table, String column, String value) {
		Table t = findTable(table);
		t.setDefaultValue(column, value);
		fireContentsChanged();
	}
	
	
}
