/**
 *
 */
package main;

/**
 * Represents a value that can be assigned to a cell.
 * This is an abstract base class that provides a common interface
 * for various types of cell values.
 *
 * Subclasses should provide concrete implementations for specific
 * data types, such as String, Integer, or Boolean.
 */
public abstract class CellValue {

	public abstract String toString();
}
