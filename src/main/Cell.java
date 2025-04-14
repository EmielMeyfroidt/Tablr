package main;

/**
 * Represents a single cell in a table structure that holds a value of a
 * generic type.
 *
 * @param <T> The type of the value this cell holds.
 */
public class Cell {
	private CellValue value;

	/**
	 * @param value The value of the cell.
	 */
	public Cell(CellValue value) {
		this.value = value;
	}
	public String getValue() {
		if (value == null) {
			return null;
		}
		return value.toString();
	}
	
	public void setValue(CellValue cellValue) {
		this.value = cellValue;
	}

}
