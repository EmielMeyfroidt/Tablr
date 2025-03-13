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
		for (Table t : tables) {
			if (t.getName().equals(tableName)) {
				return t.getColumnsInfo();

			}
		}
		return new ArrayList<String>();
	}

	/**
	 * 
	 * @param table   The old name of the table.
	 * @param newName The new name of the table.
	 */
	public void changeName(String table, String newName) {
		for (Table t : tables) {
			if (t.getName().equals(table)) {
				t.setName(newName);
				break;
			}
		}
		fireContentsChanged();
	}

	/**
	 *
	 * @param table   The name of the table.
	 * @param newName The new name of the table.
	 */
	public void changeNameColumn(String table, String column, String newName) {
		for (Table t : tables) {
			if (t.getName().equals(table)) {
				t.renameColumn(column, newName);
				break;
			}
		}
		fireContentsChanged();
	}

	/**
	 * 
	 * @param table The name of the table.
	 */
	public void addColumn(String table) {
		for (Table t : tables) {
			if (t.getName().equals(table)) {
				t.addColumn();
				break;
			}
		}
		fireContentsChanged();
	}

	/**
	 * 
	 * @param table The name of the table.
	 * @return A list of all the names of the columns.
	 */
	public List<String> getColumnNames(String table) {
		for (Table t : tables) {
			if (t.getName().equals(table)) {
				return t.getColumnNames();
			}
		}
		return new ArrayList<String>();
	}

	/**
	 * @param table The name of the table wherein the column is.
	 * @param c     The name of the column to remove.
	 */
	public void removeColumn(String table, String c) {
		for (Table t : tables) {
			t.removeColumn(c);
			break;
		}
		fireContentsChanged();
	}

	public List<List<String>> getColumns(String table) {
		List<List<String>> columns = new ArrayList<>();
		for (Table t : tables) {
			if (t.getName().equals(table)) {
				return t.getColumns();
			}
		}
		return null;
	}

	public void addRow(String table) {
		for (Table t : tables) {
			if (t.getName().equals(table)) {
				t.addRow();
				break;
			}
		}
		fireContentsChanged();
	}

	public void removeRow(String table, int row) {
		for (Table t : tables) {
			if (t.getName().equals(table)) {
				t.removeRow(row);
				break;
			}
		}
		fireContentsChanged();
	}

	public void updateCell(String nameTable, String nameColumn, Integer rowIndex, String value) {
		for (Table t : tables) {
			if (t.getName().equals(nameTable)) {
				t.updateCell(nameColumn, rowIndex, value);
			}
		}
		fireContentsChanged();
	}

	public String getCell(String nameTable, String nameColumn, Integer rowIndex) {
		for (Table t : tables) {
			if (t.getName().equals(nameTable)) {
				return t.getCell(nameColumn, rowIndex);
			}
		}
		return null;
	}
}
