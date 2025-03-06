package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//Holds columns and rows
public class Table {
	private String name;
	private List<Column> columns;

	public Table(String name) {
		this.setName(name);
		columns = new ArrayList<Column>();
	}

	public void addColumn() {
		// TODO
	}

	public void addRow() {
		// TODO
	}

	public void deleteColumn() {
		// TODO
	}

	public void deleteRow() {
		// TODO
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
