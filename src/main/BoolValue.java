/**
 *
 */
package main;

/**
 * Represents a boolean value for a cell.
 */
public class BoolValue extends CellValue {
	private final boolean value;

	public BoolValue(String input) {
		this.value = Boolean.parseBoolean(input);
	}

	/**
	 * Checks if the given input string represents a valid boolean value.
	 *
	 * @param input The input string to validate.
	 *              It is considered valid if it equals (case-insensitively) to "true" or "false".
	 * @return true if the input is a valid boolean string; false otherwise.
	 */
	public static boolean isValid(String input) {
		return input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false");
	}

	@Override
	public String toString() {
		return Boolean.toString(value);
	}

}
