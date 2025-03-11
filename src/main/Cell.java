package main;

public class Cell<T> {
	private T value;
	private Column<T> column;

	public Cell(T value, Column<T> column) {
		this.value = value;
		this.column = column;
	}

}
