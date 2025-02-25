package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//Holds columns and rows
public class Table {
	private String name;
	private List<Column> columns;
	private List<Row> rows;

	public Table(String name) {
		this.name = name;
		columns = new ArrayList<Column>();
		rows = new ArrayList<Row>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addColumn() {
		String newName = generateUniqueName("Column");
		try {
			Column newColumn = new Column(newName, ColumnType.STRING, true, "");
			columns.add(newColumn);
		} catch (Exception e) {
			// should never happen
		}
	}
	
	public void addRow() {
		List<Cell> cells = new ArrayList<Cell>();
		for (Column c : columns) {
			cells.add(new Cell(c.getDefaultValue()));
		}
	}
	
	public void deleteColumn() {
		//TODO
	}

	public void deleteRow() {
		//TODO
	}
	
	public List<Column> getColumns() {
		return columns;
	}
	
	private String generateUniqueName(String s) {
		Set<String> existingNames = columns.stream()
                .map(Column::getName)
                .collect(Collectors.toSet());
		
		int i = 0;
		String newName = s + i;
		while (existingNames.contains(newName)) {
			i++;
			newName = s + i;
		}
		return newName;
	}
}
