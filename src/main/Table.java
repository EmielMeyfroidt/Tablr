package main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//Holds columns and rows
public class Table {
	private String name;
	private List<Column<?>> columns;

	/**
	 * 
	 * @param name The name of the table.
	 */
	public Table(String name) {
		this.setName(name);
		columns = new ArrayList<Column<?>>();
	}

	/**
	 * Adds a column, generates a unique name.
	 */
	public void addColumn() {
		String name = generateUniqueName();
		Column<String> column;
		try {
			column = new Column<String>(name, String.class, true, "");
			columns.add(column);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public void addRow() {
		// TODO
	}

	/**
	 * 
	 */
	public void deleteRow() {
		// TODO
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return A string of information for all columns.
	 */
	//TODO: split in different getters.
	public List<String> getColumnsInfo() {
		List<String> columnsInfo = new ArrayList<String>();
		for (Column<?> i : columns) {
			columnsInfo.add(i.getInfo());
		}
		return columnsInfo;
	}

	/**
	 * 
	 * @return A list of all column names.
	 */
	public List<String> getColumnNames() {
		return columns.stream().map(c -> c.getName()).collect(Collectors.toList());
	}

	/**
	 * 
	 * @param c The name of the column.
	 */
	public void removeColumn(String c) {
		columns.removeIf(t -> t.getName().equals(c));
	}

	private String generateUniqueName() {
		int n = 0;
		while (getColumnNames().contains("Column" + n)) {
			n++;
		}
		return "Column" + n;
	}

}
