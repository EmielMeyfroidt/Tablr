package main;

import java.awt.Graphics;
import java.util.UUID;

/**
 * Represents a view for editing the name of a table in the application.
 */
public class EditTableNameView extends AbstractView {

	private TablesView underlyingView;
	private UUID tableId;
	private String name;
	private final String originalName;

	/**
	 * Constructs a view for editing the name of a table.
	 *
	 * @param mgr            The manager responsible for handling application operations.
	 * @param layoutInfo     The LayoutInfo object used to determine the size and position of the view.
	 * @param viewList       The ViewList object used to store and manage the views in the application.
	 * @param underlyingMode The underlying view mode associated with this table.
	 * @param tableId        The initial UUID of the table being edited.
	 */
	public EditTableNameView(TablrManager mgr, LayoutInfo layoutInfo, ViewList viewList, TablesView underlyingMode, UUID tableId) {
		super(mgr, layoutInfo, viewList);
		this.underlyingView = underlyingMode;
		this.tableId = tableId;
		this.originalName = mgr.getTableName(tableId);
		this.name = mgr.getTableName(tableId);
	}

	/**
	 * Handles a double-click event. Does not perform any action.
	 *
	 * @param x The x-coordinate of the double-click event.
	 * @param y The y-coordinate of the double-click event.
	 */
	@Override
	public void handleDoubleClick(int x, int y) {
		// nothing should happen

	}

	/**
	 * Handles a single-click event in the view, triggering a mode change to the underlying mode.
	 *
	 * @param x The x-coordinate of the single-click event.
	 * @param y The y-coordinate of the single-click event.
	 */
	@Override
	public void handleSingleClick(int x, int y) {
		// TODO: check for validity
		getViewList().substituteView(this, underlyingView);
	}

	/**
	 * Handles Escape key event. Restores the table name to its original value and switches the view mode
	 * back to the underlying mode.
	 */
	@Override
	public void handleEscape() {
		getMgr().changeName(tableId, originalName);
		getViewList().substituteView(this, underlyingView);

	}

	/**
	 * Handles the Backspace key event within the table name editing view. Adjusts the table name.
	 */
	@Override
	public void handleBackSpace() {
		try {
			getMgr().changeName(tableId, name.substring(0, name.length() - 1));
			name = name.substring(0, name.length() - 1);
		} catch (Exception e) {
			getMgr().changeName(tableId, "");
			name = "";
		}

	}

	/**
	 * Handles the Ctrl+Enter key event within the view. Does nothing.
	 */
	@Override
	public void handleCtrlEnter() {
		// nothing should happen
	}

	/**
	 * Handles the Enter key event within the table name editing view.
	 * This method validates the current Table name and triggers a mode change
	 * to the underlying view.
	 */
	@Override
	public void handleEnter() {
		// TODO: check for validity
		getViewList().substituteView(this, underlyingView);

	}

	/**
	 * Handles the Delete key event. Does nothing.
	 */
	@Override
	public void handleDelete() {
		// nothing should happen
	}

	/**
	 * Handles the event when a character is typed during table name editing.
	 * Updates the current table name by appending the typed character.
	 *
	 * @param keyChar The character that has been typed.
	 */
	@Override
	public void handleCharTyped(char keyChar) {
		getMgr().changeName(tableId, name + keyChar);
		name += keyChar;

	}

	/**
	 * Retrieves the title of the view, indicating the operation being performed.
	 *
	 * @return The title of the view, which includes "Editing name" followed by the original name being edited.
	 */
	@Override
	public String getTitle() {
		return "Editing name " + originalName + ".";
	}

	/**
	 * Paints the current view by delegating to the paint() of the `underlyingMode`.
	 *
	 * @param g The `Graphics` object used for rendering.
	 */
	@Override
	public void paint(Graphics g) {
		underlyingView.paint(g);
	}

	/**
	 * this method is invoked if underlyingView is killed and should be disposed.
	 * Returns true
	 *
	 * @param view that is to be disposed
	 * @return True if this object is to be disposed also
	 */
	@Override
	public boolean handleDeadView(AbstractView view) {
		if (this.underlyingView == view || this.underlyingView.handleDeadView(view)) {
			this.underlyingView = null;
			return true;
		}
		return false;
	}
}
