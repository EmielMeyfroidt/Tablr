/**
 *
 */
package main;

/**
 * Represents an integer value that can be assigned to a cell.
 * This class is a concrete implementation of the abstract {@link CellValue} class.
 * It encapsulates an integer value and provides functionality for validation
 * and string representation.
 */
public class IntValue extends CellValue {

	private final int value;

	/**
	 * Constructs an {@code IntValue} instance by parsing the given string input as an integer.
	 *
	 * @param input The string input to be parsed into an integer.
	 *              This value must be a valid representation of an integer.
	 */
	public IntValue(String input) {
		this.value = Integer.parseInt(input);
	}

	/**
	 * Returns the string representation of the integer value encapsulated by this instance.
	 *
	 * @return The string representation of the integer value.
	 */
	@Override
	public String toString() {
		return Integer.toString(value);
	}

	/**
	 * Determines whether the given input string can be parsed as a valid integer.
	 *
	 * @param input The string to be validated.
	 * @return {@code true} if the input can be parsed as an integer, {@code false} otherwise.
	 */
	public static boolean isValid(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
