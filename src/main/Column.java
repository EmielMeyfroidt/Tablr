package main;

public class Column {


	private String name;
	private ColumnType type;
	private boolean allowsBlanks;
	private Object defaultValue;
	
	public Column(String name, ColumnType type, boolean allowsBlanks, Object defaultValue) throws Exception {
		this.name = name;
		this.type = type;
		this.allowsBlanks = allowsBlanks;
		if (!type.validate(defaultValue)) {
			throw new Exception("Default value not of type");
		}
		this.defaultValue = defaultValue;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getType() {
		return type;
	}
	public void setType(ColumnType type) {
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
	public boolean setDefaultValue(Object defaultValue) {
		if (!type.validate(defaultValue)) {
			return false;
		}
		this.defaultValue = defaultValue;
		return true;
	}
}
