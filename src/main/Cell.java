package main;

public class Cell<T> {
	private T value;
	private Column<T> column;

	/**
	 * 
	 * @param value  The value of the cell.
	 * @param column The column of the cell.
	 */
	public Cell(T value, Column<T> column) {
		this.value = value;
		this.column = column;
	}

}
