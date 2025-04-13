/**
 *
 */
package main;

import java.awt.Graphics;

/**
 * Represents a view for editing the characteristics of a column.
 */
public class EditColumnCharacteristicsView extends AbstractView {

	private DesignView underlyingView;
	private String nameTable;
	private String name;
	private String originalName;

	/**
	 * Constructs a view for editing the characteristics of a column.
	 *
	 * @param mgr            the TablrManager instance responsible for managing the application's data and views
	 * @param underlyingMode the underlying DesignView providing the base visual representation and functionality
	 * @param name           the initial name of the column being edited
	 * @param nameTable      the name of the table containing the column
	 */
	public EditColumnCharacteristicsView(TablrManager mgr, LayoutInfo layoutInfo, ViewList viewList, DesignView underlyingMode, String name, String nameTable) {
		super(mgr, layoutInfo, viewList);
		this.underlyingView = underlyingMode;
		this.nameTable = nameTable;
		this.name = name;
		this.originalName = name;
	}

	/**
	 * Returns the title of the view, indicating the column currently being edited.
	 *
	 * @return the title string in the format "Editing column {name}."
	 */
	@Override
	public String getTitle() {
		return "Editing column " + name + ".";
	}

	/**
	 * Paints the current view by delegating the rendering to the underlying DesignView.
	 *
	 * @param g the Graphics object used to render the content on the component
	 */
	@Override
	public void paint(Graphics g) {
		underlyingView.paint(g);

	}

	/**
	 * Handles the double-click interaction within the view by specifying the
	 * coordinates where the action occurred.
	 *
	 * @param x the x-coordinate of the double-click event
	 * @param y the y-coordinate of the double-click event
	 */
	@Override
	public void handleDoubleClick(int x, int y) {
		// TODO Auto-generated method stub

	}

	/**
	 * exits back to underlying mode view.
	 *
	 * @param x the x-coordinate of the click event
	 * @param y the y-coordinate of the click event
	 */
	@Override
	public void handleSingleClick(int x, int y) {
//		fireModeChanged(underlyingView);
//		TODO
	}

	/**
	 * Handles the escape key event within the view. Reverts the column name
	 * being edited to its original name and switches the mode back to the underlying view.
	 */
	@Override
	public void handleEscape() {
		getMgr().changeNameColumn(nameTable, name, originalName);
//		fireModeChanged(underlyingView);
//		TODO
	}

	/**
	 * Handles the backspace key event within the view to update the column name being
	 * edited.
	 */
	@Override
	public void handleBackSpace() {
		try {
			getMgr().changeNameColumn(nameTable, name, name.substring(0, name.length() - 1));
			name = name.substring(0, name.length() - 1);
		} catch (Exception e) {
			getMgr().changeNameColumn(nameTable, name, "");
			name = "";
		}

	}

	/**
	 * Handles the Ctrl+Enter keypress event.
	 * <p>
	 * does nothing.
	 */
	@Override
	public void handleCtrlEnter() {
		// TODO Auto-generated method stub

	}

	/**
	 * Handles the 'Enter' key event in the view.
	 * <p>
	 * exits back to underlying view
	 */
	@Override
	public void handleEnter() {
//		TODO
//		fireModeChanged(underlyingView);
	}

	/**
	 * Handles the delete key event within the view.
	 * <p>
	 * Does nothing.
	 */
	@Override
	public void handleDelete() {
		// TODO Auto-generated method stub

	}

	/**
	 * Handles the input of a character by appending it to the current column name
	 * and updating the column name within the table.
	 *
	 * @param keyChar The character that was typed.
	 */
	@Override
	public void handleCharTyped(char keyChar) {
		getMgr().changeNameColumn(nameTable, name, name + keyChar);
		name += keyChar;
	}
}
