package main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a view for displaying and interacting with a list of tables.
 */
public class TablesView extends AbstractView {

	private List<UUID> selectedTables;

	/**
	 * Constructs a new TablesView instance to manage and display a list of tables.
	 *
	 * @param mgr the TablrManager instance used to manage table data and actions
	 */
	public TablesView(TablrManager mgr, LayoutInfo layoutInfo, ViewList viewList) {
		super(mgr, layoutInfo, viewList);
		this.selectedTables = new ArrayList<UUID>();
	}

	/**
	 * Handles a double-click event at the specified coordinates within the view.
	 * This method determines which table, if any, was clicked based on the y-coordinate
	 * and switches to a new design view for the clicked table. If no table is found
	 * at the given position, a new table is added.
	 *
	 * @param x The x-coordinate of the double-click event.
	 * @param y The y-coordinate of the double-click event.
	 */
	@Override
	public void handleDoubleClick(int x, int y) {
		int elementNumber = getLayoutInfo().getElementYNumber(y);
		try {
			UUID tableClicked = getMgr().getTableIds().get(elementNumber);
			DesignView newView = new DesignView(getMgr(), getLayoutInfo(), getViewList(), tableClicked);
			getViewList().substituteView(this, newView);
		} catch (Exception e) {
			getMgr().addTable();
		}
	}

	/**
	 * Handles a single-click event at the specified coordinates within the view.
	 * This method determines the clicked element based on the y-coordinate.
	 * If the click occurs in the left margin, the corresponding table is marked as selected.
	 * If the click occurs on the table name, the view switches to editing the table name.
	 *
	 * @param x The x-coordinate of the single-click event.
	 * @param y The y-coordinate of the single-click event.
	 */
	@Override
	public void handleSingleClick(int x, int y) {
		int elementNumber = getLayoutInfo().getElementYNumber(y);
		if (elementNumber <= getMgr().getTableNames().size()) {
			if (x < getLayoutInfo().getOffsetX()) {
				//Left margin of table, indicate that selected
				selectedTables.add(getMgr().getTableIds().get(elementNumber));
				System.out.println(elementNumber);
			} else {
				//Click on table, edit name
				UUID tableId = this.getMgr().getTableIds().get(elementNumber);
				EditTableNameView newView = new EditTableNameView(this.getMgr(), this.getLayoutInfo(), this.getViewList(), this, tableId);
				this.getViewList().substituteView(this, newView);
			}
		}
	}

	/**
	 * Handles the escape event within the view.
	 * does nothing.
	 */
	@Override
	public void handleEscape() {
		// nothing should happen
	}

	/**
	 * Handles the backspace key event within the view.
	 * does nothing.
	 */
	@Override
	public void handleBackSpace() {
		// nothing

	}

	/**
	 * Handles the ctrl+enter key event within the view.
	 * does nothing.
	 */
	@Override
	public void handleCtrlEnter() {
		// nothing
	}

	/**
	 * Handles the enter key event within the view.
	 * does nothing.
	 */
	@Override
	public void handleEnter() {
		// nothing
	}

	/**
	 * Handles the deletion of selected tables from the view.
	 */
	@Override
	public void handleDelete() {
		for (UUID t : selectedTables) {
			getMgr().removeTable(t);
			getLayoutInfo().clear(t);
		}
		selectedTables.clear();
	}

	/**
	 * Handles a character typed within the view.
	 * does nothing.
	 *
	 * @param keyChar The character that was typed during the input event.
	 */
	@Override
	public void handleCharTyped(char keyChar) {
		// nothing

	}

	/**
	 * Paints the list of table names within the view. Selected tables are marked
	 * with an asterisk symbol (*) to the left of their name.
	 *
	 * @param g The Graphics object used for rendering the table names.
	 */
	@Override
	public void paint(Graphics g) {
		int y = 0;
		for (UUID table : getMgr().getTableIds()) {
			y += getLayoutInfo().getOffsetY();
			if (selectedTables.contains(table)) {
				g.drawString("*", 0, y);
			}
			g.drawString(this.getMgr().getTableName(table), getLayoutInfo().getOffsetX(), y);
		}
	}

	/**
	 * Retrieves the title of the current view.
	 *
	 * @return the title of the view.
	 */
	@Override
	public String getTitle() {
		return "Tables Mode";
	}
	
}
