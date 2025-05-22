/**
 *
 */
package main;

/**
 * Represents a string value that can be assigned to a cell.
 * This class is a concrete implementation of the {@code CellValue} abstract class.
 * It encapsulates a string value and provides a mechanism to retrieve and validate it.
 */
public class StringValue extends CellValue {

	private final String value;

	public StringValue(String input) {
		this.value = input;
	}

	/**
	 * Validates the given input string.
	 */
	public static boolean isValid(String input) {
		return true;
	}

	@Override
	public String toString() {
		return value;
	}


}
