/**
 * 
 */
package main;

/**
 * 
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
	 * @param input
	 * @return
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
