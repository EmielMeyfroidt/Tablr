package main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for creating various view components in a graphical user interface
 * that interact with a TablrManager instance. Provides a mechanism to manage change mode listeners
 * and handle events such as mouse clicks, keyboard inputs, and painting the view.
 */
public abstract class AbstractView {

	private TablrManager mgr;
	private List<ChangeModeListener> changeModeListeners = new ArrayList<ChangeModeListener>();

	/**
	 * @return The changeModeListeners.
	 */
	public List<ChangeModeListener> getChangeModeListeners() {
		return changeModeListeners;
	}

	/**
	 * @param changeModeListeners The changeModeListeners to set.
	 */
	public void setChangeModeListeners(List<ChangeModeListener> changeModeListeners) {
		this.changeModeListeners = changeModeListeners;
	}

	/**
	 * 
	 * @param mgr The tablrManager.
	 */
	public AbstractView(TablrManager mgr) {
		this.setMgr(mgr);
	}

	public abstract String getTitle();

	/**
	 * 
	 * @param g The Graphics object.
	 */
	public abstract void paint(Graphics g);

	/**
	 * 
	 * @param x The x-coordinate for the mouse click.
	 * @param y The y-coordinate for the mouse click.
	 */
	public abstract void handleDoubleClick(int x, int y);

	/**
	 * 
	 * @param x The x-coordinate for the mouse click.
	 * @param y The y-coordinate for the mouse click.
	 */
	public abstract void handleSingleClick(int x, int y);

	/**
	 * Handles the action triggered by an Escape key event.
	 */
	public abstract void handleEscape();

	/**
	 * Handles the action triggered by a Backspace key event.
	 */
	public abstract void handleBackSpace();

	/**
	 * Handles the action triggered by the Ctrl+Enter key combination.
	 */
	public abstract void handleCtrlEnter();

	/**
	 * Handles the action triggered by an Enter key event.
	 */
	public abstract void handleEnter();

	/**
	 * Handles the action triggered by a Delete key event.
	 */
	public abstract void handleDelete();

	/**
	 * Handles a typed character event.
	 *
	 * @param keyChar The character that was typed.
	 */
	public abstract void handleCharTyped(char keyChar);

	/**
	 * Retrieves the associated TablrManager instance.
	 *
	 * @return The TablrManager instance managed by this view.
	 */
	public TablrManager getMgr() {
		return mgr;
	}

	/**
	 * Sets the TablrManager instance to be associated with this view.
	 *
	 * @param mgr The TablrManager instance to set.
	 */
	public void setMgr(TablrManager mgr) {
		this.mgr = mgr;
	}

	/**
	 * Adds a ChangeModeListener to the list of listeners for mode change events.
	 *
	 * @param c The ChangeModeListener to be added. It will be notified
	 *          when there is a mode change in the implementing view.
	 */
	public void addListener(ChangeModeListener c) {
		changeModeListeners.add(c);
	}

	/**
	 * Notifies all registered ChangeModeListeners about a mode change event
	 * in the specified AbstractView instance.
	 *
	 * @param view The AbstractView instance that triggered the mode change event.
	 */
	public void fireModeChanged(AbstractView view) {
		for (ChangeModeListener listener : changeModeListeners) {
			listener.modeChanged(view);
		}
	}

}
