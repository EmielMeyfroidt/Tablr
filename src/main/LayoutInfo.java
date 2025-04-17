package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Manages layout configurations for all tables and their associated view types.
 */
public class LayoutInfo {

	private final Map<UUID, TableLayout> tableLayouts = new HashMap<>();
	private static int offsetY = 20;
	private static int offsetX = 20;

	/***
	 * 
	 * @param tableId The unique id for the table.
	 * @return The TableLayout for the table.
	 */
	public TableLayout getTableLayout(UUID tableId) {
		return tableLayouts.computeIfAbsent(tableId, k -> new TableLayout());
	}

	/***
	 * Clears layout data for a table
	 * 
	 * @param tableId
	 */
	public void clear(UUID tableId) {
		tableLayouts.remove(tableId);
	}

	/***
	 * 
	 * @return The step size in vertical direction.
	 */
	public int getOffsetY() {
		return offsetY;
	}

	/***
	 * 
	 * @return The left margin.
	 */
	public int getOffsetX() {
		return offsetX;
	}

	/***
	 * 
	 * @param y The vertical pixel position
	 * @return The index of the element above the pixel position.
	 */
	public int getElementYNumber(int y) {
		return (int) Math.floor(y / offsetY);
	}

	/***
	 * Layout specified for each Table. Holds for ViewLayout specified for each View.
	 */
	public static class TableLayout {

		private final Map<Class<?>, ViewLayout> viewLayouts = new HashMap<>();

		/***
		 * Returns the ViewLayout associated with the given view class.
		 * 
		 * If a layout does not yet exist for the specified view class, it is created
		 * and stored. This ensures that each view type has a dedicated layout
		 * configuration for this table.
		 * 
		 * @param viewClass The class of the view.
		 * @return The ViewLayout associated with the given view class.
		 */
		public ViewLayout getViewLayout(Class<?> viewClass) {
			return viewLayouts.computeIfAbsent(viewClass, k -> new ViewLayout());
		}

	}

	/***
	 * Layout specific to each view (TablesView, DesignView, RowView, etc.)
	 */
	public static class ViewLayout {
		private List<Integer> widths = new ArrayList<>();

		/***
		 * 
		 * @return The widths.
		 */
		public List<Integer> getWidths() {
			return widths;
		}

		/***
		 * 
		 * @param widths
		 */
		public void setWidths(List<Integer> widths) {
			this.widths = widths;
		}

		/***
		 * 
		 * @param x The horizontal pixel position.
		 * @return The number of the element to the left the pixel x position.
		 */
		public int getElementXNumber(int x) {
			int runningSum = offsetX;

			for (int i = 0; i < widths.size(); i++) {
				runningSum += widths.get(i);
				if (x < runningSum) {
					return i;
				}
			}
			return -1; // x is beyond all elements
		}

		/***
		 * Removes the width of an element.
		 * 
		 * @param elementNumber The index of the element to delete.
		 */
		public void deleteElement(int elementNumber) {
			widths.remove(elementNumber);
		}

		/***
		 * Adds the width to the width list for all elements.
		 * 
		 * @param size The width.
		 */
		public void addElement(int size) {
			widths.add(size);
		}
	}
}
