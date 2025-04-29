package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Represents a table object that contains a collection of columns.
 */
public class Table {
	private final UUID id;
	private String name;
	private List<Column> columns;

	/**
	 * Constructs a new Table with the specified name.
	 *
	 * @param name The name of the table.
	 */
	public Table(String name) {
		this.setName(name);
		columns = new ArrayList<Column>();
		this.id = UUID.randomUUID();
	}

	/**
	 * Adds a new column to the table. The column is assigned a unique name,
	 * initialized with a default value, and configured to allow blank values.
	 * If the table already contains columns with cells, the new column is
	 * populated with the same number of cells, each initialized with the default value.
	 * <p>
	 * Throws:
	 * - Exception: If any part of column creation or manipulation fails.
	 */
	public void addColumn(Column column) {
		try {
			columns.add(column);
			if (columns.getFirst().getSize() > 0) {
				for (int i = 0; i < columns.getFirst().getSize(); i++) {
					column.addCell();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Column newColumn() {
		String name = generateUniqueName();
		Column column = new Column(name, "string", true, "x");
		return column;
	}

	/**
	 * Renames an existing column in the table.
	 *
	 * @param name    The current name of the column to be renamed.
	 * @param newName The new name to be assigned to the column. It must be non-null
	 *                and unique among all column names in the table.
	 */
	public void renameColumn(String name, String newName) {
		for (Column col : columns) {
			if (col.getName().equals(name)) {
				if (newName != null && !this.getColumnNames().contains(newName)) {
					col.setName(newName);
					return;
				}
			}
		}
	}

	/**
	 * Replaces an existing column in the table with a new column.
	 *
	 * @param <T>       The type of the values in the new column.
	 * @param name      The name of the column to be replaced. It must match the name of an existing column in the table.
	 * @param newColumn The new column to replace the existing one. It must be compatible with the table structure.
	 * @throws IllegalArgumentException if no column with the specified name is found.
	 */
	public void changeColumn(String name, Column newColumn) {
		Column col = findColumn(name);
		int index = columns.indexOf(col);
		columns.set(index, newColumn);
	}

	/**
	 * Adds a new row to the table by appending a new cell with the default value to each column.
	 */
	public void addRow() {
		for (Column col : columns) {
			col.addCell();
		}
	}

	/**
	 * Removes a row from all columns in the table at the specified index.
	 *
	 * @param rowIndx The index of the row to be removed. It should be within the valid bounds
	 *                of the row indices in the table.
	 */
	public void removeRow(int rowIndx) {
		for (Column col : columns) {
			col.removeRow(rowIndx);
		}
	}

	/**
	 * Retrieves the name of the table.
	 *
	 * @return The name of the table as a string.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the table to the specified value.
	 *
	 * @param name The new name to be assigned to the table. It must be a non-null string.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Retrieves information about all columns in the table.
	 *
	 * @return A list of strings: column's name, type,
	 * whether it allows blank values, and the default value.
	 */
	// TODO: split in different getters.
	public List<String> getColumnsInfo() {
		List<String> columnsInfo = new ArrayList<String>();
		for (Column i : columns) {
			columnsInfo.add(i.getInfo());
		}
		return columnsInfo;
	}

	/**
	 * @return A list of all column names.
	 */
	public List<String> getColumnNames() {
		return columns.stream().map(c -> c.getName()).collect(Collectors.toList());
	}

	/**
	 * Removes a column from the table based on the specified column name.
	 *
	 * @param colName The name of the column to be removed. It must match the name of an existing column in the table.
	 */
	public void removeColumn(String colName) {
		columns.removeIf(t -> t.getName().equals(colName));
	}

	/**
	 * Generates a unique column name for the table.
	 *
	 * @return A unique column name as a string.
	 */
	private String generateUniqueName() {
		int n = 0;
		while (getColumnNames().contains("Column" + n)) {
			n++;
		}
		return "Column" + n;
	}

	/**
	 * Retrieves the data of all columns in the table.
	 *
	 * @return A list of lists, where each inner list represents the data
	 * of a single column in the table.
	 */
	public List<List<String>> getColumns() {
		return columns.stream().map(Column::getColumn).toList();
	}

	/**
	 * Updates the cell value in the specified column and row within the table.
	 *
	 * @param nameColumn The name of the column containing the cell to update.
	 *                   Must correspond to an existing column in the table.
	 * @param rowIndex   The zero-based index of the row containing the cell to update.
	 *                   Must be within the valid range of row indices for the column.
	 * @param value      The new value to set in the specified cell. Must be compatible with the column's data type.
	 */
	public void updateCell(String nameColumn, Integer rowIndex, String value) {
		Column col = findColumn(nameColumn);
		col.updateCell(rowIndex, value);
	}

	/**
	 * Retrieves the value of a specific cell in the table.
	 *
	 * @param nameColumn The name of the column containing the desired cell.
	 *                   Must correspond to an existing column in the table.
	 * @param rowIndex   The zero-based index of the row containing the desired cell.
	 *                   Must be within the valid range of row indices for the column.
	 * @return The value of the specified cell as a string.
	 */
	public String getCell(String nameColumn, Integer rowIndex) {
		Column col = findColumn(nameColumn);
		return col.getCell(rowIndex);
	}

	/**
	 * Toggles the allowance of blank (null or empty) values for the specified column in the table.
	 *
	 * @param columnName The name of the column whose "allow blanks" setting is to be changed.
	 *                   Must correspond to an existing column in the table.
	 */
	public void changeAllowBlanks(String columnName) {
		Column col = findColumn(columnName);
		col.changeAllowBlanks();
	}

	/**
	 * Changes the type of the specified column in the table, cycling between String, Boolean, and Integer types.
	 * If the current type of the column is String, it changes to Boolean.
	 * If the current type is Boolean, it changes to Integer.
	 * If the current type is Integer, it changes to String.
	 *
	 * @param columnName The name of the column whose type is to be changed. The column must exist in the table.
	 */
	public void changeType(String columnName) {
		Column col = findColumn(columnName);
		if (col.getType() == "string") {
			Column newCol = new Column(columnName, "boolean", true, "true");
			changeColumn(columnName, newCol);
		} else if (col.getType() == "boolean") {
			Column newCol = new Column(columnName, "int", true, "0");
			changeColumn(columnName, newCol);
		} else if (col.getType() == "int") {
			Column newCol = new Column(columnName, "string", true, "");
			changeColumn(columnName, newCol);
		}
	}

	/**
	 * Retrieves the default value for a specified column in the table.
	 *
	 * @param column The name of the column for which to retrieve the default value.
	 *               Must correspond to a valid column name in the table.
	 * @return The default value of the specified column as an Object.
	 */
	public String getDefaultValue(String column) {
		Column col = findColumn(column);
		return col.getDefaultValue();
	}

	/**
	 * Retrieves a column from the table based on the specified column name.
	 *
	 * @param columnName The name of the column to find. Must correspond to an existing column in the table.
	 * @return The column with the specified name, or null if no column with the given name exists.
	 */
	public Column findColumn(String columnName) {
		for (Column col : columns) {
			if (col.getName().equals(columnName)) {
				return col;
			}
		}
		return null;
	}

	/**
	 * Retrieves the data type class of a specified column in the table.
	 *
	 * @param column The name of the column whose data type is to be retrieved.
	 *               Must correspond to an existing column in the table.
	 * @return The Class object representing the data type of the specified column.
	 */
	public String getClass(String column) {
		Column col = findColumn(column);
		return col.getType();
	}

	/**
	 * Sets the default value for a specified column in the table.
	 * The column must exist in the table, and the value must be compatible
	 * with the column's data type.
	 *
	 * @param column The name of the column for which to set the default value.
	 *               It must correspond to an existing column in the table.
	 * @param value  The new default value to be assigned to the column.
	 *               It must match the column's data type.
	 */
	public void setDefaultValue(String column, String value) {
		Column col = findColumn(column);
		col.setDefaultValue(value);
	}

	public UUID getId() {
		return id;
	}

	public HashMap<String, List<String>> getData() {
		HashMap<String, List<String>> dataMap = new HashMap<>();
		for (Column column : columns) {
			dataMap.put(column.getName(), column.getColumn());
		}
		return dataMap;
	}
	
	public int getNumberOfRows() {
		if (columns.get(0) != null) {
			return columns.get(0).getSize();
		}
		return 0;
	}
}
