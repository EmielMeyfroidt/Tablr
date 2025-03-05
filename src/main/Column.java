package main;

public class Column {

	private String name;
	private Object type;
	private boolean allowsBlanks;
	private Object defaultValue;

	public Column(String name, Object type, boolean allowsBlanks, Object defaultValue) throws Exception {
		this.name = name;
		this.type = type;
		this.allowsBlanks = allowsBlanks;
		this.defaultValue = defaultValue;
	}

}
