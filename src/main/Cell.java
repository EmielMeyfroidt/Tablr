package main;

public class Cell<T> {
	private T value;
	private Column<T> column;

	/**
	 * @param value The value of the cell.
	 */
	public Cell(T value) {
		this.value = value;
	}
	public T getValue() {
		return value;
	}

}
