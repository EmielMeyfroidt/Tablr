package main;

public class Column {


	private String name;
	private Object type;
	private boolean allowsBlanks;
	private Object defaultValue;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getType() {
		return type;
	}
	public void setType(Object type) {
		this.type = type;
	}
	public boolean isAllowsBlanks() {
		return allowsBlanks;
	}
	public void setAllowsBlanks(boolean allowsBlanks) {
		this.allowsBlanks = allowsBlanks;
	}
	public Object getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}
}
