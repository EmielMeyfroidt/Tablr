/**
 *
 */
package main;

/**
 * Represents a boolean value for a cell.
 */
public class BoolValue extends CellValue {
	private final boolean value;

	/**
	 * Constructs a BoolValue instance by parsing the given input string as a boolean value.
	 *
	 * @param input The input string to be parsed. Valid values are "true" or "false" (case-insensitive).
	 *              Any other string will default to false.
	 */
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

	/**
	 * Returns the string representation of the boolean value.
	 *
	 * @return The string "true" if the value is true; otherwise, the string "false".
	 */
	@Override
	public String toString() {
		return Boolean.toString(value);
	}

}
