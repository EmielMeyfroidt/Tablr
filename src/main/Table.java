package main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//Holds columns and rows
public class Table {
	private String name;
	private List<Column<?>> columns;

	public Table(String name) {
		this.setName(name);
		columns = new ArrayList<Column<?>>();
		this.addColumn();
	}

	public void addColumn() {
		String name = generateUniqueName();
		Column<String> column;
		try {
			column = new Column<String>(name, String.class, true , "");
			columns.add(column);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public List<String> getColumnsInfo() {
		List<String> columnsInfo = new ArrayList<String>();
		for (Column<?> i : columns) {
			columnsInfo.add(i.getInfo());
		}
		return columnsInfo;
	}
	
	public List<String> getColumnNames(){
		return columns.stream().map(c -> c.getName()).collect(Collectors.toList());
	}
	
	public String generateUniqueName() {
		int n = 0;
		while (getColumnNames().contains("Column" + n)) {
			n++;
		}
		return "Column" + n;
	}

}
