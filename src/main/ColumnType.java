package main;

public enum ColumnType {
	STRING,
	EMAIL,
	BOOLEAN,
	INTEGER;
	
	public boolean validate(Object data) {
		switch (this) {
		case STRING:
			return data instanceof String;
		case EMAIL:
			return data instanceof Email;
		case BOOLEAN:
			return data instanceof Boolean;
		case INTEGER:
			return data instanceof Integer;
		default:
			return false;
		}
	}
}
