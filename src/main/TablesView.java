package main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a view for displaying and interacting with a list of tables.
 */
public class TablesView extends AbstractView {

	private final int stepX = 20;
	private final int stepY = 20;
	private List<String> selectedTables;

	/**
	 * Constructs a new TablesView instance to manage and display a list of tables.
	 *
	 * @param mgr the TablrManager instance used to manage table data and actions
	 */
	public TablesView(TablrManager mgr) {
		super(mgr);
		this.selectedTables = new ArrayList<String>();
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
		int elementNumber = (int) Math.floor(y/this.stepY);
		try {
			String tableClicked = getMgr().getTableNames().get(elementNumber);
			DesignView newView = new DesignView(getMgr(), tableClicked);
			newView.setChangeModeListeners(getChangeModeListeners());
			this.fireModeChanged(newView);
		}catch(Exception e){
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
        int elementNumber = (int) Math.floor(y / this.stepY);
        if (elementNumber <= getMgr().getTableNames().size()) {
            if (x < stepX) {
                //Left margin of table, indicate that selected
                selectedTables.add(getMgr().getTableNames().get(elementNumber));
                fireModeChanged(this);
            } else {
                //Click on table, edit name
				String tableName = this.getMgr().getTableNames().get(elementNumber);
				fireModeChanged(new EditTableNameView(this.getMgr(), this, tableName));
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
		// TODO Auto-generated method stub

	}
	/**
	 * Handles the ctrl+enter key event within the view.
	 * does nothing.
	 */
	@Override
	public void handleCtrlEnter() {
		// TODO Auto-generated method stub
	}
	/**
	 * Handles the enter key event within the view.
	 * does nothing.
	 */
	@Override
	public void handleEnter() {
		// TODO Auto-generated method stub
	}

	/**
	 * Handles the deletion of selected tables from the view.
	 */
	@Override
	public void handleDelete() {
		for (String t : selectedTables) {
			getMgr().removeTable(t);
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
		// TODO Auto-generated method stub
		
	}

	/**
	 * Paints the list of table names within the view. Selected tables are marked
	 * with an asterisk symbol (*) to the left of their name.
	 *
	 * @param g The Graphics object used for rendering the table names.
	 */
	@Override
	public void paint(Graphics g) {
		int y = stepY;
		for (String table : getMgr().getTableNames()) {
			if (selectedTables.contains(table)) {
				g.drawString("*", 0, y);
			}
			g.drawString(table, stepX, y);
			y += stepY;
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
