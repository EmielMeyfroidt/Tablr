package main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class DesignView extends AbstractView {

	private final int stepX = 20;
	private final int stepY = 20;
	private UUID tableId;
	private List<String> selectedColumns;
	private List<Integer> margin;

	/**
	 * Constructs a new DesignView instance with the specified manager and table name.
	 *
	 * @param mgr   The TablrManager instance managing this view.
	 * @param table The name of the table associated with this design view.
	 */
	public DesignView(TablrManager mgr, LayoutInfo layoutInfo, ViewList viewList, UUID table) {
		super(mgr, layoutInfo, viewList);
		this.tableId = table;
		this.selectedColumns = new ArrayList<String>();
		this.margin = new ArrayList<>();
	}

	/**
	 * Handles a double-click event in the design view.
	 * If below the tables, triggers an action to add a new column
	 * to the associated table otherwise performs no operation.
	 *
	 * @param x The x-coordinate of the double-click.
	 * @param y The y-coordinate of the double-click.
	 */
	@Override
	public void handleDoubleClick(int x, int y) {
		System.out.println("design view");
		int elementNumber = (int) Math.floor(y / this.stepY);
		if (elementNumber > getMgr().getColumnNames(tableId).size()) {
			getMgr().addColumn(tableId);
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
		int elementNumber = (int) Math.floor(y / this.stepY);
		if ((elementNumber <= getMgr().getColumnNames(tableId).size())) {
			List<Integer> runningMargin = new ArrayList<>();
			int sum = 0;
			sum += stepX;
			for (int i = 0; i < margin.size(); i++) {
				sum += margin.get(i) * 10;
				runningMargin.add(sum);
			}
			if (x < stepX) {
				// Left margin of table, indicate that selected
				selectedColumns.add(getMgr().getColumnNames(tableId).get(elementNumber));
			} else {
				String column = this.getMgr().getColumnNames(tableId).get(elementNumber);
				if (x < runningMargin.get(0)) {
					// Click on table name, edit name
					EditColumnCharacteristicsView newView = new EditColumnCharacteristicsView(this.getMgr(), this.getLayoutInfo(), this.getViewList(), this, column, tableId);
					this.getViewList().substituteView(this, newView);
				} else if (x < runningMargin.get(1)) {
					// Click on type
					this.getMgr().changeType(tableId, column);
				} else if (x < runningMargin.get(2)) {
					// Click on allowBlanks
					this.getMgr().changeAllowBlanks(tableId, column);
				} else if (x < runningMargin.get(3)) {
					// Click on defaultValue
					if (getMgr().getClass(tableId, column) == "boolean") {
						getMgr().setDefaultValue(tableId, column, String.valueOf(!(boolean) getMgr().getDefaultValue(tableId, column)));
					} else {
						EditDefaultValueView newView = new EditDefaultValueView(this.getMgr(), this.getLayoutInfo(), this.getViewList(), this, column, tableId);
						this.getViewList().substituteView(this, newView);
					}
				}
			}
		}
	}

	/**
	 * Handles the escape key event in the design view.
	 * <p>
	 * This method transitions the current view from the DesignView to a
	 * TablesView.
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
			getMgr().removeColumn(tableId, c);
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

	/**
	 * Paints the DesignView.
	 *
	 * @param g The Graphics object used to render the content on the component.
	 */
	@Override
	public void paint(Graphics g) {
		List<List<String>> splitList = new ArrayList<>();
		for (String s : getMgr().getColumnsInfo(tableId)) {
			List<String> columnData = Arrays.asList(s.split(" "));
			splitList.add(columnData);
		}
		int y = stepY;
		int x = stepX;
		int i = 0;
		try {
			// calculate margins
			margin = new ArrayList<>(splitList.getFirst().stream().map(String::length).toList());
			for (List<String> l : splitList) {
				i = 0;
				for (String s : l) {
					if (s.length() > margin.get(i)) {
						margin.set(i, s.length());
					}
					i++;
				}
			}
			for (List<String> l : splitList) {
				if (selectedColumns.contains(l.get(0))) {
					g.drawString("*", 0, y);
				}
				i = 0;
				for (String s : l) {
					try {
						g.drawString(s, x, y);
					} catch (Exception e) {
						g.drawString("", x, y);
					}
					x += margin.get(i) * 10;
					i++;
				}
				y += stepY;
				x = stepX;
			}
		} catch (NoSuchElementException e) {
		}

	}
}
