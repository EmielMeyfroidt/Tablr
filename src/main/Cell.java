package main;

/**
 * Represents a single cell in a table structure that holds a value of a
 * generic type.
 *
 * @param <T> The type of the value this cell holds.
 */
public class Cell<T> {
	private T value;

	/**
	 * @param value The value of the cell.
	 */
	public Cell(T value) {
		this.value = value;
	}
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}

}
