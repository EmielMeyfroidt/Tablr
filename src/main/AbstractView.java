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
	private LayoutInfo layoutInfo;
	private ViewList viewList;

	/**
	 * @param mgr The tablrManager.
	 */
	public AbstractView(TablrManager mgr, LayoutInfo layoutInfo, ViewList viewList) {
		this.setMgr(mgr);
		this.layoutInfo = layoutInfo;
		this.viewList = viewList;
	}

	public abstract String getTitle();

	/**
	 * @param g The Graphics object.
	 */
	public void paint(Graphics g) {
		//TODO paint empty Window
	}

	/**
	 * @param x The x-coordinate for the mouse click.
	 * @param y The y-coordinate for the mouse click.
	 */
	public abstract void handleDoubleClick(int x, int y);

	/**
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
	protected TablrManager getMgr() {
		return mgr;
	}

	/**
	 * Sets the TablrManager instance to be associated with this view.
	 *
	 * @param mgr The TablrManager instance to set.
	 */
	protected void setMgr(TablrManager mgr) {
		this.mgr = mgr;
	}

	protected LayoutInfo getLayoutInfo() {
		return layoutInfo;
	}

	protected ViewList getViewList() {
		return viewList;
	}

	protected void setViewList(ViewList viewList) {
		this.viewList = viewList;
	}

	public void handleDoubleClickOutside() {
	}

	public void handleSingleClickOutside() {
	}

	/**
	 * is called when a left pressed mouse changes position to x, y
	 *
	 * @param x
	 * @param y
	 */
	public void handleMouseDrag(int x, int y) {
	}
}
