/**
 *
 */
package main;

import java.awt.Graphics;
import java.util.UUID;

/**
 * A view class for editing the default value of a specific column in a table.
 */
public class EditDefaultValueView extends AbstractView {

	private DesignView underlyingView;
	private UUID tableId;
	private String column;
	private final String originalValue;
	private String newValue;

	/**
	 * Constructs an EditDefaultValueView object.
	 *
	 * @param mgr            The TablrManager used to manage and manipulate table data.
	 * @param underlyingView The underlying DesignView associated with this EditDefaultValueView.
	 * @param column         The name of the column whose default value is being edited.
	 * @param table          The name of the table containing the specified column.
	 */
	public EditDefaultValueView(TablrManager mgr, LayoutInfo layoutInfo, ViewList viewList, DesignView underlyingView, String column, UUID tableId) {
		super(mgr, layoutInfo, viewList);
		this.underlyingView = underlyingView;
		this.tableId = tableId;
		this.column = column;
		this.originalValue = mgr.getDefaultValue(tableId, column).toString();
		this.newValue = mgr.getDefaultValue(tableId, column).toString();
	}

	/**
	 * Retrieves the title of this view.
	 *
	 * @return A string representing the title of the view, which includes the column whose default value is being edited.
	 */
	@Override
	public String getTitle() {
		return "Editing default value of " + column + ".";
	}

	/**
	 * Renders this view by delegating the painting operation to the underlying view.
	 *
	 * @param g The Graphics object used to render the content on the component.
	 */
	@Override
	public void paint(Graphics g) {
		underlyingView.paint(g);

	}

	/**
	 * Handles a double-click event at a specific location.
	 *
	 * @param x The x-coordinate of the double-click location.
	 * @param y The y-coordinate of the double-click location.
	 */
	@Override
	public void handleDoubleClick(int x, int y) {
		// nothing

	}

	/**
	 * Handles a single mouse click event at the specified coordinates.
	 * exits back to underlying view
	 *
	 * @param x The x-coordinate of the mouse click location.
	 * @param y The y-coordinate of the mouse click location.
	 */
	@Override
	public void handleSingleClick(int x, int y) {
		getViewList().substituteView(this, underlyingView);
	}

	/**
	 * Handles the action of canceling edits to a default value, following by a return to underlying view
	 */
	@Override
	public void handleEscape() {
		getMgr().setDefaultValue(tableId, column, originalValue);
		getViewList().substituteView(this, underlyingView);
	}

	/**
	 * Removes the last character from the currently edited
	 * default value of a column.
	 */
	@Override
	public void handleBackSpace() {
		try {
			getMgr().setDefaultValue(tableId, column, newValue.substring(0, newValue.length() - 1));
			;
			newValue = newValue.substring(0, newValue.length() - 1);
		} catch (Exception e) {
			getMgr().setDefaultValue(tableId, column, "");
			newValue = "";
		}

	}

	/**
	 * Handles the control + enter (Ctrl + Enter) key combination event.
	 * Does nothing
	 */
	@Override
	public void handleCtrlEnter() {

	}

	/**
	 * Handles the "Enter" key event.
	 * This method triggers a mode change event for the associated underlying view.
	 */
	@Override
	public void handleEnter() {
		getViewList().substituteView(this, underlyingView);
	}

	/**
	 * Handles the action of delete key event. Does nothing.
	 */
	@Override
	public void handleDelete() {
		// nothing

	}

	/**
	 * Handles the event of typing a character. This method appends the provided
	 * character to the current value of the default value being edited and updates
	 * the default value for the specified table and column.
	 *
	 * @param keyChar The character that was typed.
	 */
	@Override
	public void handleCharTyped(char keyChar) {
		getMgr().setDefaultValue(tableId, column, newValue.toString() + keyChar);
		newValue += keyChar;
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
