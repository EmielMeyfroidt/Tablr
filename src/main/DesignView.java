package main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class DesignView extends AbstractView {

	private final int stepX = 20;
	private final int stepY = 20;
	private String table;
	private List<String> selectedColumns;
	private List<Integer> margin;

	public DesignView(TablrManager mgr, String table) {
		super(mgr);
		this.table = table;
		this.selectedColumns = new ArrayList<String>();
		this.margin = new ArrayList<>();
	}

	@Override
	public void handleDoubleClick(int x, int y) {
		System.out.println("design view");
		int elementNumber = (int) Math.floor(y / this.stepY);
		if (elementNumber > getMgr().getColumnNames(table).size()) {
			getMgr().addColumn(table);
		} else {
			// nothing
		}
	}

	@Override
	public void handleSingleClick(int x, int y) {
		int elementNumber = (int) Math.floor(y / this.stepY);
		if ((elementNumber <= getMgr().getColumnNames(table).size())) {
			List<Integer> runningMargin = new ArrayList<>();
			int sum = 0;
			sum += stepX;
			for (int i = 0; i < margin.size(); i++) {
			    sum += margin.get(i) * 10;
			    runningMargin.add(sum);
			}
			if (x < stepX) {
				// Left margin of table, indicate that selected
				selectedColumns.add(getMgr().getColumnNames(table).get(elementNumber));
				fireModeChanged(this);
			} else {
				String column = this.getMgr().getColumnNames(table).get(elementNumber);
				if (x < runningMargin.get(0)){
					// Click on table name, edit name
					fireModeChanged(new EditColumnCharacteristicsView(this.getMgr(), this,
							column, table));
				} else if (x < runningMargin.get(1)){
					// Click on type
					this.getMgr().changeType(table, column);
				} else if (x < runningMargin.get(2)) {
					// Click on allowBlanks
					this.getMgr().changeAllowBlanks(table, column);
				} else if (x < runningMargin.get(3)) {
					// Click on defaultValue
					if (getMgr().getClass(table, column) == Boolean.class) {
						getMgr().setDefaultValue(table, column, String.valueOf(!(boolean) getMgr().getDefaultValue(table, column)));
					} else {
						fireModeChanged(new EditDefaultValueView(this.getMgr(), this, column, table));
					}
				}
			}
		}
	}

	@Override
	public void handleEscape() {
		TablesView newView = new TablesView(getMgr());
		newView.setChangeModeListeners(getChangeModeListeners());
		fireModeChanged(newView);
	}

	@Override
	public void handleBackSpace() {
		// nothing should happen

	}

	@Override
	public void handleCtrlEnter() {
		System.out.println("ctrl enter");
		RowsView newView = new RowsView(getMgr(), table);
		newView.setChangeModeListeners(getChangeModeListeners());
		fireModeChanged(newView);
	}

	@Override
	public void handleEnter() {
		// nothing should happen
	}

	@Override
	public void handleDelete() {
		for (String c : selectedColumns) {
			getMgr().removeColumn(table, c);
		}
		selectedColumns.clear();
	}

	@Override
	public void handleCharTyped(char keyChar) {
		// nothing should happen
	}

	@Override
	public String getTitle() {
		return "Design Mode";
	}

	@Override
	public void paint(Graphics g) {
		List<List<String>> splitList = new ArrayList<>();
		for (String s : getMgr().getColumnsInfo(table)) {
			List<String> columnData = Arrays.asList(s.split(" "));
			splitList.add(columnData);
		}
		int y = stepY;
		int x = stepX;
		int i = 0;
		try {
			// calculate margins
			margin = splitList.getFirst().stream().map(String::length).toList();
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
					g.drawString(s, x, y);
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
