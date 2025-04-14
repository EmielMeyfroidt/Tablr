package main;

import java.util.List;

/**
 * Represents a column in a table structure with a generic value type.
 *
 * @param <T> The type of the values this column holds.
 */
public class Column {

	private String name;
	private String type;
	private boolean allowsBlanks;
	private String defaultValue;
	private List<Cell> cells;

	/**
	 * Constructs a new Column object with a specified name, type, allowance for
	 * blanks, and default value.
	 *
	 * @param name         The name of the column.
	 * @param typeClass    The class type of the values that will be stored in the
	 *                     column.
	 * @param allowsBlanks A boolean indicating whether the column allows blank
	 *                     (null) values.
	 * @param defaultValue The default value to populate cells in the column.
	 */
	public Column(String name, String typeClass, boolean allowsBlanks, String defaultValue) {
		this.name = name;
		this.type = typeClass;
		this.allowsBlanks = allowsBlanks;
		this.defaultValue = defaultValue;
		this.cells = new java.util.ArrayList<Cell>();
	}

	/**
	 * Retrieves a string containing information about the column.
	 *
	 * @return A string representing the column's name, type, blank permission, and
	 *         default value.
	 */
	public String getInfo() {
		return name + " " + type + " " + allowsBlanks + " " + defaultValue;
	}

	/**
	 * Retrieves all the values from the cells in the column as a list of strings.
	 *
	 * @return A list of strings representing the values of the column's cells.
	 */
	public List<String> getColumn() {
		return cells.stream().map(cell -> cell.getValue().toString()).toList();
	}

	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the column.
	 *
	 * @param name The new name to be assigned to the column.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Adds a new cell to the column with the default value of the column.
	 */
	public void addCell() {
		cells.add(new Cell(parseInput(defaultValue)));
	}

	/**
	 * Returns the number of cells in the column.
	 *
	 * @return The size of the column as the count of its cells.
	 */
	public int getSize() {
		return cells.size();
	}

	/**
	 * Removes a row from the column at the specified index.
	 *
	 * @param rowIndx The index of the row to be removed. It should be within the
	 *                valid bounds of the list of cells.
	 */
	public void removeRow(int rowIndx) {
		cells.remove(rowIndx);
	}

	/**
	 * Updates the value of a cell at a specified row index within the column.
	 *
	 * @pre the value is compatible with the type of the column
	 *
	 * @pre column is of type String, Boolean or Integer
	 *
	 * @param rowIndex The index of the row whose cell value needs to be updated. It
	 *                 must be within the valid bounds of the column's cells list.
	 * @param value    The new value to be set for the cell. The value must be a
	 *                 string representation compatible with the column's data type.
	 */
	public void updateCell(Integer rowIndex, String value) {
		if (!isValidInput(value)) {
			throw new IllegalArgumentException("Invalid cell value");
		}
		cells.get(rowIndex).setValue(parseInput(value));
	}

	/**
	 * Retrieves the value of a cell at the specified row index as a string.
	 *
	 * @param rowIndex The index of the row from which to retrieve the cell value.
	 *
	 * @return A string representation of the value of the specified cell.
	 *
	 * @throws IndexOutOfBoundsException – if the index is out of range
	 */
	public String getCell(Integer rowIndex) {
		return cells.get(rowIndex).getValue().toString();
	}

	/**
	 * Toggles the allowance of blank values for the column.
	 */
	public void changeAllowBlanks() {
		this.allowsBlanks = !this.allowsBlanks;
	}

	/**
	 * Retrieves the class type of the values stored in the column.
	 *
	 * @return The class type of the values in the column.
	 */
	public String getType() {
		return type;
	}

	/**
	 * Retrieves the default value for the column.
	 *
	 * @return The default value of the column cells.
	 */
	public String getDefaultValue() {
		return this.defaultValue;
	}

	/**
	 * Sets the default value for the column.
	 *
	 * @pre the value is compatible with the type of the column
	 *
	 * @pre column is of type String, Boolean or Integer
	 *
	 * @param value The default value to be set for the column as a string.
	 */
	public void setDefaultValue(String value) {
		this.defaultValue = value;
	}

	public boolean isValidInput(String input) {
		switch (type) {
		case "string":
			return StringValue.isValid(input);
		case "int":
			return IntValue.isValid(input);
		case "bool":
			return BoolValue.isValid(input);
		default:
			return false;
		}
	}
	
	public CellValue parseInput(String input) {
		switch (type) {
        case "string": return new StringValue(input);
        case "int": return new IntValue(input);
        case "bool": return new BoolValue(input);
        default: throw new IllegalArgumentException("Unknown type: " + type);
    }
	}

}
