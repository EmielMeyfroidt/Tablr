package main;

import java.util.List;

public class Column<T> {

	private String name;
	private Class<T> typeClass;
	private boolean allowsBlanks;
	private T defaultValue;
	private List<Cell<T>> cells;

	/**
	 * 
	 * @param name         The name of the column.
	 * @param typeClass    The type of the column.
	 * @param allowsBlanks Does the column allow blanks?
	 * @param defaultValue The default value for the column.
	 */
	public Column(String name, Class<T> typeClass, boolean allowsBlanks, T defaultValue) {
		this.name = name;
		this.typeClass = typeClass;
		this.allowsBlanks = allowsBlanks;
		this.defaultValue = defaultValue;
		this.cells = new java.util.ArrayList<Cell<T>>();
	}

	/**
	 * 
	 * @return The info of the column.
	 */
	public String getInfo() {
		String typeName = (typeClass != null) ? typeClass.getSimpleName() : "Unknown Type";
		return name + " " + typeName + " " + allowsBlanks + " " + defaultValue.toString();
	}

	/**
	 *
	 * @return The contents of the column as string.
	 */
	public List<String> getColumn() {
		return cells.stream().map(cell -> cell.getValue().toString()).toList();
	}

	public String getName() {
		return name;
	}

	/**
	 *
	 * @param name new column name validated by table
	 */
	public void setName(String name) {
		this.name = name;
	}

	public void addCell() {
		cells.add(new Cell<>(defaultValue));
	}

	public int getSize() {
		return cells.size();
	}

	public void removeRow(int rowIndx) {
		cells.remove(rowIndx);
	}

	public void updateCell(Integer rowIndex, String value) {
		if (typeClass == String.class) {
			cells.get(rowIndex).setValue((T) typeClass.cast(value));
			return;
		} else if (typeClass == Boolean.class) {
			boolean b = Boolean.parseBoolean(value);
			cells.get(rowIndex).setValue((T) typeClass.cast(value));
			return;
		}
	}

	public String getCell(Integer rowIndex) {
		return cells.get(rowIndex).getValue().toString();
	}

	/**
	 * 
	 */
	public void changeAllowBlanks() {
		this.allowsBlanks = !this.allowsBlanks;
	}
	
	public Class<T> getType(){
		return typeClass;
	}
	
	public T getDefaultValue() {
		return this.defaultValue;
	}

	/**
	 * @param value
	 */
	public void setDefaultValue(String value) {
		this.defaultValue = toT(value);
	}

	/**
	 * @param value
	 * @return
	 */
	private T toT(String value) {
		if (typeClass == String.class) {
			return (T) value;
		} else if (typeClass == Boolean.class) {
			return (T) Boolean.valueOf(value);
		} else if (typeClass == Integer.class) {
			return (T) Integer.valueOf(value);
		} 
		return null;
	}
}
