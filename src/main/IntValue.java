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

	public IntValue(String input) {
		this.value = Integer.parseInt(input);
	}

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
