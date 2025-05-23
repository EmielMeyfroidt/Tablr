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

	/**
	 * Constructs a {@code StringValue} instance with the specified input string.
	 *
	 * @param input The string to be encapsulated by this {@code StringValue} instance.
	 */
	public StringValue(String input) {
		this.value = input;
	}

	/**
	 * Validates the given input string to determine if it meets the criteria for being a valid string value.
	 *
	 * @param input The string to validate.
	 * @return {@code true} if the input string is valid; {@code false} otherwise.
	 */
	public static boolean isValid(String input) {
		return true;
	}

	/**
	 * Returns the string representation of the encapsulated value.
	 *
	 * @return The string value stored in this instance.
	 */
	@Override
	public String toString() {
		return value;
	}


}
