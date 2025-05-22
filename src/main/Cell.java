package main;

/**
 * Represents a single cell in a table structure that holds a value of a
 * generic type.
 */
public class Cell {
	private CellValue value;

	/**
	 * @param value The value of the cell.
	 */
	public Cell(CellValue value) {
		this.value = value;
	}

	/**
	 * Retrieves the string representation of the cell's value.
	 *
	 * @return A string representation of the value if it exists, or null if the value is not set.
	 */
	public String getValue() {
		if (value == null) {
			return null;
		}
		return value.toString();
	}

	/**
	 * Sets the value of the cell.
	 *
	 * @param cellValue The new value to be assigned to the cell.
	 */
	public void setValue(CellValue cellValue) {
		this.value = cellValue;
	}

}
