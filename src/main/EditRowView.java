package main;

import java.awt.*;
import java.util.UUID;

/**
 * Represents a view for editing a specific cell value in a table. The EditRowView is
 * associated with an underlying RowsView.
 */
public class EditRowView extends AbstractView {

	private RowsView underlyingView;
	private String nameColumn;
	private UUID tableId;
	private Integer rowIndex;
	private String value;

	/**
	 * Constructs an EditRowView object.
	 *
	 * @param mgr            the TablrManager instance responsible for managing the table data
	 * @param underlyingMode the underlying RowsView used to provide the basis of this view
	 * @param tableId        the UUID of the table containing the cell being edited
	 * @param nameColumn     the name of the column containing the cell being edited
	 * @param rowIndex       the index of the row containing the cell being edited
	 */
	public EditRowView(TablrManager mgr, LayoutInfo layoutInfo, ViewList viewList, RowsView underlyingMode, UUID tableId, String nameColumn, Integer rowIndex) {
		super(mgr, layoutInfo, viewList);
		this.underlyingView = underlyingMode;
		this.tableId = tableId;
		this.nameColumn = nameColumn;
		this.rowIndex = rowIndex;
		value = getMgr().getCell(tableId, nameColumn, rowIndex);
	}

	/**
	 * Handles a double-click event at the specified coordinates.
	 * no action is performed.
	 *
	 * @param x The x-coordinate where the double-click occurred.
	 * @param y The y-coordinate where the double-click occurred.
	 */
	@Override
	public void handleDoubleClick(int x, int y) {
		// nothing should happen

	}

	/**
	 * Handles a single-click event at the specified coordinates and reverts the mode to rowsView.
	 *
	 * @param x The x-coordinate where the single-click occurred.
	 * @param y The y-coordinate where the single-click occurred.
	 */
	@Override
	public void handleSingleClick(int x, int y) {
		//TODO: check for validity
		this.getViewList().substituteView(this, underlyingView);

	}

	/**
	 * Handles the escape action in the editing view by reverting to RowsView.
	 */
	@Override
	public void handleEscape() {
		this.getViewList().substituteView(this, underlyingView);

	}

	/**
	 * Handles the backspace action, and adjusts cell contents.
	 */
	@Override
	public void handleBackSpace() {
		if (!value.isEmpty()) {
			getMgr().updateCell(tableId, nameColumn, rowIndex, value.substring(0, value.length() - 1));
			value = value.substring(0, value.length() - 1);
		}
	}

	/**
	 * Handles the "Ctrl + Enter" key action.
	 * No operation is performed.
	 */
	@Override
	public void handleCtrlEnter() {
		// nothing should happen
	}

	/**
	 * Handles the "Enter" key action.
	 * <p>
	 * reverts to rowsView.
	 */
	@Override
	public void handleEnter() {
		//TODO: check for validity
		this.getViewList().substituteView(this, underlyingView);

	}

	/**
	 * Handles the delete action in the editing view of a cell. Does nothing.
	 */
	@Override
	public void handleDelete() {

	}

	/**
	 * Handles the typing of a single character into the editing view of a cell.
	 * The character is appended to the current cell's value, and the cell is updated
	 * with the new value.
	 *
	 * @param keyChar The character that was typed.
	 */
	@Override
	public void handleCharTyped(char keyChar) {
		getMgr().updateCell(tableId, nameColumn, rowIndex, value += keyChar);
	}

	/**
	 * Retrieves the title of the editing view which reflects the cell currently being edited.
	 *
	 * @return A string representing the title in the format:
	 * "Editing value of [columnName] at row [rowIndex]".
	 */
	@Override
	public String getTitle() {
		return "Editing value of " + nameColumn + " at row " + rowIndex;
	}

	/**
	 * Paints the view through the RowsView object.
	 *
	 * @param g The Graphics object used to perform the rendering.
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
