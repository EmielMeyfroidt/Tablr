package main;

import java.util.List;

//Contains cells
public class Row {

	private List<Cell> cells;

	public Row(List<Cell> cells) {
		this.cells = cells;
	}
	
	public List<Cell> getCells() {
		return cells;
	}

	public void setCells(List<Cell> cells) {
		this.cells = cells;
	}
}
