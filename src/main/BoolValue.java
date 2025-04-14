/**
 * 
 */
package main;

/**
 * 
 */
public class BoolValue extends CellValue {
	private final boolean value;
	
	public BoolValue(String input) {
		this.value = Boolean.parseBoolean(input);
	}
	
	public static boolean isValid(String input) {
        return input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false");
	}
	@Override
	public String toString() {
        return Boolean.toString(value);
	}

}
