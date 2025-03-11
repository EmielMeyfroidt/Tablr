package main;

public class Column<T> {

	private String name;
	private Class<T> typeClass;
	private boolean allowsBlanks;
	private T defaultValue;

	public Column(String name, Class<T> typeClass, boolean allowsBlanks, T defaultValue) throws Exception {
		this.name = name;
		this.typeClass = typeClass;
		this.allowsBlanks = allowsBlanks;
		this.defaultValue = defaultValue;
	}

	public String getInfo() {
		String typeName = (typeClass != null) ? typeClass.getSimpleName() : "Unknown Type";
		return name + " " + typeName + " " + allowsBlanks + " " + defaultValue.toString();
	}

	public String getName() {
		return name;
	}
}
