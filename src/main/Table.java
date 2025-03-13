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
			column = new Column<String>(name, String.class, true, "x");
			columns.add(column);
			if (columns.getFirst().getSize() > 0) {
				for (int i = 0; i < columns.getFirst().getSize(); i++) {
					column.addCell();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void renameColumn(String name, String newName) {
		for (Column<?> col : columns) {
			if (col.getName().equals(name)) {
				if (newName != null && !this.getColumnNames().contains(newName)) {
					col.setName(newName);
					return;
				}
			}
		}
	}
	
	public <T> void changeColumn(String name, Column<T> newColumn) {
		Column<?> col = findColumn(name);
		int index = columns.indexOf(col);
		columns.set(index, newColumn);
	}

	/**
	 * 
	 */
	public void addRow() {
		for (Column<?> col : columns) {
			col.addCell();
		}
	}

	/**
	 * 
	 */
	public void removeRow(int rowIndx) {
		for (Column<?> col : columns) {
			col.removeRow(rowIndx);
		}
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
	// TODO: split in different getters.
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

	public List<List<String>> getColumns() {
		return columns.stream().map(Column::getColumn).toList();
	}

	public void updateCell(String nameColumn, Integer rowIndex, String value) {
		Column<?> col = findColumn(nameColumn);
		col.updateCell(rowIndex, value);
	}

	public String getCell(String nameColumn, Integer rowIndex) {
		Column<?> col = findColumn(nameColumn);
		return col.getCell(rowIndex);
	}

	/**
	 * 
	 */
	public void changeAllowBlanks(String columnName) {
		Column<?> col = findColumn(columnName);
		col.changeAllowBlanks();
	}

	public void changeType(String columnName) {
		Column<?> col = findColumn(columnName);
		if (col.getType() == String.class) {
	        Column<Boolean> newCol = new Column<Boolean>(columnName, Boolean.class, true, true);
	        changeColumn(columnName, newCol);
	    }else if (col.getType() == Boolean.class) {
	    	Column<Integer> newCol = new Column<Integer>(columnName, Integer.class, true, 0);
	    	changeColumn(columnName, newCol);
	    }else if (col.getType() == Integer.class) {
	    	Column<String> newCol = new Column<String>(columnName, String.class, true, "");
	    	changeColumn(columnName, newCol);
	    }
	}

	private Column<?> findColumn(String columnName) {
		for (Column<?> col : columns) {
			if (col.getName().equals(columnName)) {
				return col;
			}
		}
		return null;
	}
}
