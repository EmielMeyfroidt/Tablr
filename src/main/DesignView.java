package main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class DesignView extends AbstractView {

	private UUID tableId;
	private List<String> selectedColumns;

	/**
	 * Constructs a new DesignView instance with the specified manager and table
	 * name.
	 *
	 * @param mgr   The TablrManager instance managing this view.
	 * @param table The name of the table associated with this design view.
	 */
	public DesignView(TablrManager mgr, LayoutInfo layoutInfo, ViewList viewList, UUID table) {
		super(mgr, layoutInfo, viewList);
		this.tableId = table;
		this.selectedColumns = new ArrayList<String>();
		if (getLayoutInfo().getTableLayout(tableId).getViewLayout(getClass()).getWidths().isEmpty()) {
			getLayoutInfo().getTableLayout(tableId).getViewLayout(getClass())
			.setWidths(new ArrayList<>(Collections.nCopies(4, 50)));
		}
	}

	/**
	 * Handles a double-click event in the design view. If below the tables,
	 * triggers an action to add a new column to the associated table otherwise
	 * performs no operation.
	 *
	 * @param x The x-coordinate of the double-click.
	 * @param y The y-coordinate of the double-click.
	 */
	@Override
	public void handleDoubleClick(int x, int y) {
		System.out.println("design view");
		if (getLayoutInfo().getElementYNumber(y) > getMgr().getColumnNames(tableId).size()) {
			getMgr().addColumn(tableId);
			getLayoutInfo().getTableLayout(tableId).getViewLayout(RowsView.class).getWidths().add(50);
		} else {
			// nothing
		}
	}

	/**
	 * Handles a single-click event within the design view.
	 *
	 * @param x The x-coordinate of the single click.
	 * @param y The y-coordinate of the single click.
	 */
	@Override
	public void handleSingleClick(int x, int y) {
		if ((getLayoutInfo().getElementYNumber(y) <= getMgr().getColumnNames(tableId).size())) {
			int elementXNumber = getLayoutInfo().getTableLayout(tableId).getViewLayout(getClass()).getElementXNumber(x);
			System.out.println(elementXNumber);
			if (x < getLayoutInfo().getOffsetX()) {
				// Left margin of table, indicate that selected
				selectedColumns.add(getMgr().getColumnNames(tableId).get(getLayoutInfo().getElementYNumber(y)));
			} else {
				String column = this.getMgr().getColumnNames(tableId).get(getLayoutInfo().getElementYNumber(y));
				if (elementXNumber == 0) {
					// Click on table name, edit name
					EditColumnCharacteristicsView newView = new EditColumnCharacteristicsView(this.getMgr(),
							this.getLayoutInfo(), this.getViewList(), this, column, tableId);
					this.getViewList().substituteView(this, newView);
				} else if (elementXNumber == 1) {
					// Click on type
					this.getMgr().changeType(tableId, column);
				} else if (elementXNumber == 2) {
					// Click on allowBlanks
					this.getMgr().changeAllowBlanks(tableId, column);
				} else if (elementXNumber == 3) {
					// Click on defaultValue
					if (getMgr().getClass(tableId, column) == "boolean") {
						getMgr().setDefaultValue(tableId, column,
								String.valueOf(!(boolean) getMgr().getDefaultValue(tableId, column)));
					} else {
						EditDefaultValueView newView = new EditDefaultValueView(this.getMgr(), this.getLayoutInfo(),
								this.getViewList(), this, column, tableId);
						this.getViewList().substituteView(this, newView);
					}
				}
			}
		}
	}

	/**
	 * Handles the escape key event in the design view.
	 * <p>
	 * This method transitions the current view from the DesignView to a TablesView.
	 */
	@Override
	public void handleEscape() {
		TablesView newView = new TablesView(getMgr(), getLayoutInfo(), getViewList());
		this.getViewList().substituteView(this, newView);
	}

	/**
	 * Handles the 'Backspace' key event in the design view.
	 * <p>
	 * This method is intentionally left as no-op.
	 */
	@Override
	public void handleBackSpace() {
		// nothing should happen

	}

	/**
	 * Handles the "Ctrl+Enter" key press event in the context of the DesignView.
	 * <p>
	 * This method transitions the current view from the DesignView to a RowsView.
	 */
	@Override
	public void handleCtrlEnter() {
		System.out.println("ctrl enter");
		RowsView newView = new RowsView(getMgr(), getLayoutInfo(), getViewList(), tableId);
		this.getViewList().substituteView(this, newView);
	}

	/**
	 * Handles the 'Enter' key event in the design view.
	 * <p>
	 * This method is intentionally left as a no-op.
	 */
	@Override
	public void handleEnter() {
		// nothing should happen
	}

	/**
	 * Handles the deletion of selected columns in the design view.
	 */
	@Override
	public void handleDelete() {
		for (String c : selectedColumns) {
			int elementNumber = getMgr().getColumnNames(tableId).indexOf(c);
			getMgr().removeColumn(tableId, c);
			getLayoutInfo().getTableLayout(tableId).getViewLayout(RowsView.class).deleteElement(elementNumber);

		}
		selectedColumns.clear();
	}

	/**
	 * Handles the event when a character is typed in the design view.
	 * <p>
	 * This method is intentionally left as a no-op.
	 *
	 * @param keyChar The character that was typed by the user.
	 */
	@Override
	public void handleCharTyped(char keyChar) {
		// nothing should happen
	}

	/**
	 * Retrieves the title of the design view.
	 *
	 * @return A string representing the title of the design view.
	 */
	@Override
	public String getTitle() {
		return "Design Mode";
	}

	@Override
	public void paint(Graphics g) {
		if (this.getMgr().getTableName(tableId) == null) {
			this.getViewList().closeView(this);
			return;
		}

		int heightOffset = this.getLayoutInfo().getOffsetY();
		int y = 0;
		for (String column : getMgr().getColumnsInfo(tableId)) {
			int x = getLayoutInfo().getOffsetX();
			List<String> columnData = Arrays.asList(column.split(" "));
			List<Integer> widths = getLayoutInfo().getTableLayout(tableId).getViewLayout(getClass()).getWidths();

			y += heightOffset;

			if (selectedColumns.contains(column.split(" ")[0])) {
				g.drawString("*", 0, y);
			}

			for (int i = 0; i < columnData.size(); i++) {
				String value = columnData.get(i);
				int width = (i < widths.size()) ? widths.get(i) : 50;
				try {
					g.drawString(value, x, y);
				} catch (Exception e) {
					g.drawString("", x, y);
				}
				x += width;
			}
		}
	}

	@Override
	public void handleMouseDrag(int startX, int startY, int endX, int endY) {
		int elementNumber = getLayoutInfo().getTableLayout(tableId).getViewLayout(getClass()).getElementXNumber(startX) - 1;
		List<Integer> widths = getLayoutInfo().getTableLayout(tableId).getViewLayout(getClass()).getWidths();
		int newWidth = widths.get(elementNumber) + (endX - startX);
		widths.set(elementNumber, newWidth);
	}

}
