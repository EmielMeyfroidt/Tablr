/**
 *
 */
package main;

/**
 * Represents a command that defines two operations: executing an action and undoing it.
 */
public interface Command {
	/**
	 * Executes the action encapsulated by the command.
	 * This method performs the main operation defined by the command.
	 */
	public void execute();

	/**
	 * Reverses the last executed action of the command.
	 * This method restores the state to what it was before the command was executed.
	 */
	public void undo();
}
