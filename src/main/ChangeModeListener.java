/**
 * 
 */
package main;

/**
 * The ChangeModeListener interface is used to define listeners that respond to mode change
 * events in views implementing the AbstractView class. When a mode change occurs, all
 * registered ChangeModeListeners are notified through the modeChanged method.
 */
public interface ChangeModeListener {

	/**
	 * Handles the action to be performed when the mode of an AbstractView instance changes.
	 *
	 * @param view The AbstractView instance whose mode has changed.
	 */
	public void modeChanged(AbstractView view);
}