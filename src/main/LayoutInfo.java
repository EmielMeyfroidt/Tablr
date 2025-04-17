package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LayoutInfo {

	// Store layout information for each table
	private final Map<UUID, TableLayout> tableLayouts = new HashMap<>();
	private static int offsetY = 20; //Step in vertical direction
	private static int offsetX = 20; //Left margin

	// Retrieves or creates a TableLayout for a given table
	public TableLayout getTableLayout(UUID tableId) {
		return tableLayouts.computeIfAbsent(tableId, k -> new TableLayout());
	}

	// Clears layout data for a table
	public void clear(UUID tableId) {
		tableLayouts.remove(tableId);
	}

	public int getOffsetY() {
		return offsetY;
	}

	public int getOffsetX() {
		return offsetX;
	}

	public int getElementYNumber(int y) {
		return (int) Math.floor(y / offsetY);
	}

	public static class TableLayout {

		// Store layouts for all views dynamically using class types as keys
		private final Map<Class<?>, ViewLayout> viewLayouts = new HashMap<>();

		// Retrieve or create a ViewLayout for a specific view type using its class
		public ViewLayout getViewLayout(Class<?> viewClass) {
			return viewLayouts.computeIfAbsent(viewClass, k -> new ViewLayout());
		}

	}

	// Layout specific to each view (TablesView, DesignView, RowView, etc.)
	public static class ViewLayout {
		private int offsetY; // Vertical offset for this view (DesignView, RowView, NewView)
		private List<Integer> widths = new ArrayList<>();

		public int getOffsetY() {
			return offsetY;
		}

		public void setOffsetY(int offsetY) {
			this.offsetY = offsetY;
		}

		public List<Integer> getWidths() {
			return widths;
		}

		public void setWidths(List<Integer> widths) {
			this.widths = widths;
		}
		
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
		
		public void deleteElement(int elementNumber) {
			widths.remove(elementNumber);
		}
		
		public void addElement(int size) {
			widths.add(size);
		}
	}
}
