package main;

public class Cell {
	private Object value;
	
	public Cell(Object value) {
		this.value = value;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
}
