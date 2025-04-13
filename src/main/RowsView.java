package main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * RowsView is a view
 * for displaying and interacting with rows in a specific table managed by the TablrManager.
 */
public class RowsView extends AbstractView {

	private String table;
	private final int leftMargin = 20;
	private final int topMargin = 20;
	private final int stepY = 20;
	private List<Integer> selectedRows;
	private List<Integer> margins;

	/**
	 * Constructs a RowsView instance for managing the view of rows in a specific table.
	 *
	 * @param mgr   the TablrManager instance responsible for managing views and data models
	 * @param table the name of the table whose rows are to be displayed and managed
	 */
	public RowsView(TablrManager mgr, LayoutInfo layoutInfo, ViewList viewList, String table) {
		super(mgr, layoutInfo, viewList);
		this.table = table;
		this.selectedRows = new ArrayList<Integer>();
	}

	/**
	 * Handles the double-click event. adds a new row if below the list of rows.
	 *
	 * @param x The x-coordinate of the double-click position.
	 * @param y The y-coordinate of the double-click position. Determines the row index.
	 */
	@Override
	public void handleDoubleClick(int x, int y) {
		int elementNumber = (int) Math.floor(y / this.stepY) - 1;
		if (elementNumber > getMgr().getColumns(table).getFirst().size()) {
			this.getMgr().addRow(table);
		}
	}

	/**
	 * Handles a single-click event in the view. if click lands on a cell of a row, the contents of the cell will be changed.
	 * If the cell is a boolean value is toggled, else a new EditRowView is opened to handle the entry of a new value.
	 * <p>
	 * it the click lands left of the leftMargin, the column is selected.
	 *
	 * @param x The x-coordinate of the click position.
	 * @param y The y-coordinate of the click position. Determines the row index.
	 */
	@Override
	public void handleSingleClick(int x, int y) {
		int rowIndex = (int) Math.floor((y - topMargin) / this.stepY);
		if ((rowIndex <= getMgr().getColumnNames(table).size())) {
			if (x < leftMargin) {
				// Left margin of table, indicate that selected
				selectedRows.add(rowIndex);
//				fireModeChanged(this);
				//TODO
			} else {
				// Click on table, edit cell
				String column = this.getMgr().getColumnNames(table).get(locateColumn(x));
				if (getMgr().getClass(table, column) == Boolean.class) {
					// edit boolean value
					getMgr().updateCell(table, column, rowIndex,
							String.valueOf(!Boolean.valueOf(getMgr().getCell(table, column, rowIndex))));
				} else {
					// edit string or integer value
//					fireModeChanged(new EditRowView(this.getMgr(), this, table, column, rowIndex));
					//TODO
				}
			}
		}
	}

	/**
	 * Determines the column index corresponding to the provided x-coordinate.
	 *
	 * @param x The x-coordinate for which the column index needs to be identified.
	 * @return The index of the column that the x-coordinate falls into.
	 */
	private int locateColumn(int x) {
		int columnIndex = 0;
		for (int i = 0; i < margins.size(); i++) {
			if (x > margins.get(i)) {
				columnIndex = i;
			}
		}
		return columnIndex;
	}

	/**
	 * Handles the escape key press event in the RowsView.
	 * This method transitions the view from the current RowsView instance
	 * to a new TablesView instance.
	 */
	@Override
	public void handleEscape() {
		TablesView newView = new TablesView(getMgr(), getLayoutInfo(), getViewList());
//		newView.setChangeModeListeners(getChangeModeListeners());
//		fireModeChanged(newView);
		//TODO
	}

	/**
	 * Handles the backspace key press event in the RowsView.
	 * does nothing.
	 */
	@Override
	public void handleBackSpace() {
	}

	/**
	 * Handles the Ctrl+Enter key press event in the RowsView.
	 * This method switches the current view to a new DesignView.
	 */
	@Override
	public void handleCtrlEnter() {
		System.out.println("switch to design");
		DesignView newView = new DesignView(getMgr(), getLayoutInfo(), getViewList(), table);
//		newView.setChangeModeListeners(getChangeModeListeners());
//		fireModeChanged(newView);
		//TODO
	}

	/**
	 * Handles the Enter key press event in the RowsView.
	 * does nothing.
	 */
	@Override
	public void handleEnter() {
		// TODO Auto-generated method stub
	}

	/**
	 * Handles the deletion of selected rows in the table view.
	 */
	@Override
	public void handleDelete() {
		for (int row : selectedRows) {
			getMgr().removeRow(table, row);
		}
	}

	/**
	 * Handles a character typed event in the RowsView. does nothing.
	 *
	 * @param keyChar The character that was typed.
	 */
	@Override
	public void handleCharTyped(char keyChar) {
		// TODO Auto-generated method stub
	}

	/**
	 * Retrieves the title of the current view.
	 *
	 * @return the title of the view .
	 */
	@Override
	public String getTitle() {
		return "Rows Mode";
	}

	/**
	 * Paints the graphical representation of rows and columns for the associated table.
	 *
	 * @param g The {@code Graphics} object used for rendering the content on the screen.
	 */
	@Override
	public void paint(Graphics g) {
		int charSize = 10;

		List<List<String>> columns = getMgr().getColumns(table);
		int currentMargin = leftMargin;
		this.margins = new ArrayList<>();
		margins.add(currentMargin);
		for (int col = 0; col < columns.size() - 1; col++) {
			int maxLength = getMgr().getColumnNames(table).get(col).length();
			int maxLenOfCol = columns.get(col).stream().mapToInt(String::length).max().orElse(0);
			if (maxLength < maxLenOfCol) {
				maxLength = maxLenOfCol;
			}
			currentMargin += maxLength * charSize;
			margins.add(currentMargin);
		}

		for (int col = 0; col < getMgr().getColumnNames(table).size(); col++) {
			g.drawString(getMgr().getColumnNames(table).get(col), margins.get(col), topMargin);
		}
		for (int row = 0; row < columns.getFirst().size(); row++) {
			if (selectedRows.contains(row)) {
				g.drawString("*", 5, ((row + 1) * stepY) + topMargin);
			}
			for (int col = 0; col < columns.size(); col++) {
				g.drawString(columns.get(col).get(row), margins.get(col), ((row + 1) * stepY) + topMargin);
			}
		}
	}
}
