package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

//Manage tables and their contents

/**
 * TablrManager is responsible for managing a collection of tables and their
 * corresponding operations. It provides methods to manipulate tables, columns,
 * and rows, as well as notifying listeners about changes.
 */
public class TablrManager {
	private List<Table> tables;
	private ArrayList<Command> undoStack = new ArrayList<>();
	private int nbCommandsUndone;

	public void undo() {
		if (undoStack.size() > nbCommandsUndone) {
			undoStack.get(undoStack.size() - ++nbCommandsUndone).undo();
		}
	}

	public void redo() {
		if (nbCommandsUndone > 0)
			undoStack.get(undoStack.size() - nbCommandsUndone--).execute();
	}

	private void execute(Command command) {
		for (; nbCommandsUndone > 0; nbCommandsUndone--) {
			undoStack.remove(undoStack.size() - 1);
		}
		undoStack.add(command);
		command.execute();
	}

	/**
	 * Constructs a new instance of the TablrManager class.
	 * <p>
	 * This constructor initializes the TablrManager with an empty list of tables
	 * and listeners. It also notifies registered listeners of a change in the state
	 * by invoking the `fireContentsChanged` method.
	 */
	public TablrManager() {
		this.tables = new ArrayList<Table>();
	}

	/**
	 * Adds a table, generates a unique name.
	 */
	public UUID addTable() {
		String uniqueName = generateUniqueName();
		Table newTable = new Table(uniqueName);
		UUID uuid = newTable.getId();
		execute(new Command() {
			@Override
			public void execute() {
				tables.add(newTable);
			}

			@Override
			public void undo() {
				tables.remove(newTable);
			}

		});
		return uuid;
	}

	/**
	 * Removes a table with the specified name from the list of tables managed by
	 * the TablrManager. If a table with the specified name exists, it is removed,
	 * and all registered listeners are notified of the content change.
	 *
	 * @param table The name of the table to be removed.
	 */
	public void removeTable(UUID tableId) {
		Table table = findTable(tableId);
		execute(new Command() {

			@Override
			public void execute() {
				tables.remove(table);

			}

			@Override
			public void undo() {
				tables.add(table);

			}

		});
	}

	/**
	 * Retrieves a list of table names managed by the TablrManager.
	 *
	 * @return a list of strings representing the names of all tables.
	 */
	public List<String> getTableNames() {
		return tables.stream().map(t -> t.getName()).collect(Collectors.toList());
	}

	public List<UUID> getTableIds() {
		return tables.stream().map(t -> t.getId()).collect(Collectors.toList());
	}

	public String getTableName(UUID id) {
		Table table = findTable(id);
		return table != null ? table.getName() : null;
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
	public List<String> getColumnsInfo(UUID tableId) {
		Table t = findTable(tableId);
		return t.getColumnsInfo();
	}

	/**
	 * @param table   The old name of the table.
	 * @param newName The new name of the table.
	 */
	public void changeName(UUID tableId, String newName) {
		Table t = findTable(tableId);
		final String oldName = t.getName();
		execute(new Command() {

			@Override
			public void execute() {
				t.setName(newName);
			}

			@Override
			public void undo() {
				t.setName(oldName);
			}

		});

	}

	/**
	 * @param table   The name of the table.
	 * @param newName The new name of the table.
	 */
	public void changeNameColumn(UUID table, String column, String newName) {
		Table t = findTable(table);
		final String oldName = t.findColumn(column).getName();
		execute(new Command() {

			@Override
			public void execute() {
				t.renameColumn(column, newName);

			}

			@Override
			public void undo() {
				t.renameColumn(column, oldName);

			}

		});

	}

	/**
	 * @param table The name of the table.
	 */
	public void addColumn(UUID table) {
		Table t = findTable(table);
		Column column = t.newColumn();
		execute(new Command() {

			@Override
			public void execute() {
				t.addColumn(column);

			}

			@Override
			public void undo() {
				t.removeColumn(column.getName());
			}

		});

	}

	/**
	 * @param table The name of the table.
	 * @return A list of all the names of the columns.
	 */
	public List<String> getColumnNames(UUID table) {
		Table t = findTable(table);
		return t.getColumnNames();
	}

	/**
	 * @param table The name of the table wherein the column is.
	 * @param c     The name of the column to remove.
	 */
	public void removeColumn(UUID table, String c) {
		Table t = findTable(table);
		Column column = t.findColumn(c);
		execute(new Command() {

			@Override
			public void execute() {
				t.removeColumn(c);
			}

			@Override
			public void undo() {
				t.addColumn(column);
			}
		});
	}

	/**
	 * Retrieves all columns of the specified table.
	 *
	 * @param table The name of the table from which the columns are to be
	 *              retrieved.
	 * @return A list of lists where each inner list contains the values of a single
	 * column.
	 */
	public List<List<String>> getColumns(UUID table) {
		Table t = findTable(table);
		return t.getColumns();
	}

	/**
	 * Adds a new row to the specified table, identified by its name. Once the row
	 * is added, all registered listeners are notified of the change.
	 *
	 * @param table The name of the table where a new row will be added.
	 */
	public void addRow(UUID table) {
		Table t = findTable(table);
		execute(new Command() {

			@Override
			public void execute() {
				t.addRow();
			}

			@Override
			public void undo() {
				t.removeRow(t.getNumberOfRows() - 1);
			}
		});
	}

	/**
	 * Removes a row from the specified table and notifies listeners of the change.
	 *
	 * @param table   The name of the table from which to remove the row.
	 * @param rowIndx The index of the row to be removed.
	 */
	public void removeRow(UUID table, int rowIndx) {
		Table t = findTable(table);
		List<String> row = t.getRow(rowIndx);
		execute(new Command() {

			@Override
			public void execute() {
				t.removeRow(rowIndx);
			}

			@Override
			public void undo() {
				t.insertRow(rowIndx);
				t.updateRow(rowIndx, row);
			}

		});

	}

	/**
	 * Updates the value of a cell in a specified table and column at the given row
	 * index. After updating the cell, this method notifies all registered listeners
	 * about the change.
	 *
	 * @param nameTable  The name of the table containing the cell to update.
	 * @param nameColumn The name of the column containing the cell to update.
	 * @param rowIndex   The index of the row containing the cell to update.
	 * @param value      The new value to set in the specified cell.
	 */
	public void updateCell(UUID nameTable, String nameColumn, Integer rowIndex, String newValue) {
		Table t = findTable(nameTable);
		final String oldValue = t.getCell(nameColumn, rowIndex);
		execute(new Command() {

			@Override
			public void execute() {
				t.updateCell(nameColumn, rowIndex, newValue);
			}

			@Override
			public void undo() {
				t.updateCell(nameColumn, rowIndex, oldValue);
			}

		});

	}

	/**
	 * Retrieves the value of a specific cell in a table.
	 *
	 * @param nameTable  The name of the table containing the desired cell.
	 * @param nameColumn The name of the column containing the desired cell.
	 * @param rowIndex   The index of the row containing the desired cell.
	 * @return The value of the cell as a String.
	 */
	public String getCell(UUID nameTable, String nameColumn, Integer rowIndex) {
		Table t = findTable(nameTable);
		return t.getCell(nameColumn, rowIndex);

	}

	/**
	 * Toggles the ability to allow blank values in a specific column of a specified
	 * table. This method updates the blank allowance setting for the given column
	 * and notifies all registered listeners about the change.
	 *
	 * @param tableName  The name of the table containing the column to update.
	 * @param columnName The name of the column whose blank allowance setting will
	 *                   be changed.
	 */
	public void changeAllowBlanks(UUID tableName, String columnName) {
		Table t = findTable(tableName);
		execute(new Command() {

			@Override
			public void execute() {
				t.changeAllowBlanks(columnName);

			}

			@Override
			public void undo() {
				t.changeAllowBlanks(columnName);

			}

		});

	}

	/**
	 * Searches for a table with the specified name in the list of managed tables.
	 *
	 * @param tableName The name of the table to search for.
	 * @return The Table object that matches the specified name, or null if no table
	 * with the given name is found.
	 */
	private Table findTable(UUID tableId) {
		for (Table t : tables) {
			if (t.getId().equals(tableId)) {
				return t;
			}
		}
		return null;
	}

	/**
	 * Changes the type of the specified column in the specified table. This method
	 * locates the table by its name, updates the type of the given column, and
	 * notifies all registered listeners of the change.
	 *
	 * @param table  The name of the table containing the column whose type is to be
	 *               changed.
	 * @param column The name of the column whose type will be modified.
	 */
	public void changeType(UUID table, String column) {
		Table t = findTable(table);
		execute(new Command() {

			@Override
			public void execute() {
				t.changeType(column);
			}

			@Override
			public void undo() {
				// TODO

			}

		});
	}

	/**
	 * Retrieves the default value of a specific column in a specified table.
	 *
	 * @param table  The name of the table containing the column.
	 * @param column The name of the column for which the default value is to be
	 *               retrieved.
	 * @return The default value of the specified column in the table, or null if no
	 * default value is set.
	 */
	public Object getDefaultValue(UUID table, String column) {
		Table t = findTable(table);
		return t.getDefaultValue(column);
	}

	/**
	 * Retrieves the class type of the specified column in the specified table.
	 *
	 * @param table  The name of the table containing the column.
	 * @param column The name of the column whose class type is to be retrieved.
	 * @return The Class object representing the type of the specified column in the
	 * table.
	 */
	public String getClass(UUID table, String column) {
		Table t = findTable(table);
		return t.getClass(column);
	}

	/**
	 * Sets the default value for a specified column in a specified table. This
	 * method locates the table by its name, updates the default value of the given
	 * column, and notifies all registered listeners of the change.
	 *
	 * @param table  The name of the table containing the column.
	 * @param column The name of the column for which the default value is to be
	 *               set.
	 * @param value  The default value to assign to the specified column.
	 */
	public void setDefaultValue(UUID table, String column, String newValue) {
		Table t = findTable(table);
		final String oldValue = t.getDefaultValue(column);
		execute(new Command() {

			@Override
			public void execute() {
				t.setDefaultValue(column, newValue);

			}

			@Override
			public void undo() {
				t.setDefaultValue(column, oldValue);

			}

		});
	}

	/**
	 * for testing flows returns a map of table names to a map of columns
	 */
	public HashMap<String, HashMap<String, List<String>>> getData() {
		HashMap<String, HashMap<String, List<String>>> data = new HashMap<>();
		for (Table table : tables) {
			data.put(table.getName(), table.getData());
		}
		return data;
	}
}
