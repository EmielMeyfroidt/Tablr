package main;

public class Column<T> {

	private String name;
	private Class<T> typeClass;
	private boolean allowsBlanks;
	private T defaultValue;

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
	}

	/**
	 * 
	 * @return The info of the column.
	 */
	public String getInfo() {
		String typeName = (typeClass != null) ? typeClass.getSimpleName() : "Unknown Type";
		return name + " " + typeName + " " + allowsBlanks + " " + defaultValue.toString();
	}

	public String getName() {
		return name;
	}
}
