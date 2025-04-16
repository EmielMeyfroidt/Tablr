package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LayoutInfo {

	private LinkedHashMap<UUID, Integer> heightTablesView = new LinkedHashMap<>();
	private int widthTablesView = 20;
	private HashMap<UUID, HashMap<String, List<Integer>>> widthsDesignView = new HashMap<>();
	private HashMap<UUID, HashMap<String, Integer>> heightsDesignView = new HashMap<>();
	private HashMap<UUID, HashMap<String, Integer>> widthsRowView = new HashMap<>();
	private HashMap<UUID, List<Integer>> heightsRowView = new HashMap<>();

	public void addTable(UUID id) {
		heightTablesView.put(id, setDynamicHeight());
		widthsDesignView.put(id, new HashMap<>());
		heightsDesignView.put(id, new HashMap<>());
		widthsRowView.put(id, new HashMap<>());
		heightsRowView.put(id, new ArrayList<>());
	}

	public void removeTable(UUID id) {
		heightTablesView.remove(id);
		widthsDesignView.remove(id);
		heightsDesignView.remove(id);
		widthsRowView.remove(id);
		heightsRowView.remove(id);
	}

	public void addColumnToTable(UUID id, String column) {
		widthsDesignView.get(id).put(column, designViewWidths());
		heightsDesignView.get(id).put(column, setDynamicHeight());
		widthsRowView.get(id).put(column, setDynamicWidth());
	}

	public void deleteColumnFromTable(UUID id, String column) {
		widthsDesignView.get(id).remove(column);
		heightsDesignView.get(id).remove(column);
		widthsRowView.get(id).remove(column);
	}

	public void addRowToTable(UUID id) {
		heightsRowView.get(id).add(setDynamicHeight());
	}

	public void deleteRowFromTable(UUID id) {
		List<Integer> rowHeights = heightsRowView.get(id);
		if (!rowHeights.isEmpty()) {
			rowHeights.remove(rowHeights.size() - 1);
		}
	}

	public void changeHeightTableView(UUID id, int newHeight) {
		heightTablesView.put(id, newHeight);
	}

	public void changeWidthDesignView(UUID id, String column, int fieldIndex, int newWidth) {
		List<Integer> widths = widthsDesignView.get(id).get(column);
		if (widths != null && fieldIndex < widths.size()) {
			widths.set(fieldIndex, newWidth);
		}
	}

	public void changeHeightDesignView(UUID id, String column, int newHeight) {
		heightsDesignView.get(id).put(column, newHeight);
	}

	public void changeWidthRowView(UUID id, String column, int newWidth) {
		widthsRowView.get(id).put(column, newWidth);
	}

	public void changeHeightRowView(UUID id, int rowIndex, int newHeight) {
		List<Integer> heights = heightsRowView.get(id);
		if (rowIndex < heights.size()) {
			heights.set(rowIndex, newHeight);
		}
	}
	
	public int calculateElementIndex(List<Integer> sizes, int offset) {
		int position = 0;
		for (int i = 0; i < sizes.size(); i++) {
			int size = sizes.get(i);
			if (offset < position + size) {
				return i; // Found the element containing the offset
			}
			position += size;
		}
		return -1; // Not found (e.g., offset is outside all elements)
	}

	// === Getters ===

	public List<Integer> getHeightsTable() {
		return new ArrayList<>(heightTablesView.values());
	}
	
	public int getWidthTable() {
		return widthTablesView;
	}

	public Map<String, List<Integer>> getWidthsDesignView(UUID id) {
		return widthsDesignView.get(id);
	}

	public Map<String, Integer> getHeightsDesignView(UUID id) {
		return heightsDesignView.get(id);
	}

	public Map<String, Integer> getWidthsRowView(UUID id) {
		return widthsRowView.get(id);
	}

	public List<Integer> getHeightsRowView(UUID id) {
		return heightsRowView.get(id);
	}

	// === Helpers ===

	private int setDynamicHeight() {
		return 20;
	}

	private int setDynamicWidth() {
		return 60;
	}

	private List<Integer> designViewWidths() {
		// name, type, blanks allowed, default value
		return new ArrayList<>(Arrays.asList(20, 20, 20, 20, 20));
	}
}
