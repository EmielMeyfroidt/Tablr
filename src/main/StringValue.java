/**
 * 
 */
package main;

/**
 * 
 */
public class StringValue extends CellValue {

	private final String value;
	
	public StringValue(String input) {
		this.value = input;
	}

	public static boolean isValid(String input) {
		return true;
	}
	
	@Override
	public String toString() {
		return value;
	}


}
