package main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DesignView extends AbstractView {

	private final int stepX = 20;
	private final int stepY = 20;
	private String table;
	private List<String> selectedColumns;

	public DesignView(TablrManager mgr, String table) {
		super(mgr);
		this.table = table;
		this.selectedColumns = new ArrayList<String>();
	}

	@Override
	public void handleDoubleClick(int x, int y) {
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
			if (x < stepX) {
				// Left margin of table, indicate that selected
				selectedColumns.add(getMgr().getColumnNames(table).get(elementNumber));
				fireModeChanged(this);}
			else {
				// Click on table, edit name
				fireModeChanged(new EditNameView(this.getMgr(), this, this.getMgr().getColumnNames(table).get(elementNumber), table));
		}}

	}

	@Override
	public void handleEscape() {
		TablesView newView = new TablesView(getMgr());
		newView.setChangeModeListeners(getChangeModeListeners());
		fireModeChanged(newView);
	}

	@Override
	public void handleBackSpace() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleCtrlEnter() {
		// TODO Auto-generated method stub
	}

	@Override
	public void handleEnter() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
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
		for (List<String> l : splitList) {
			if (selectedColumns.contains(l.get(0))) {
				g.drawString("*", 0, y);
			}
			for (String s : l) {
				g.drawString(s, x, y);
				x += 3 * stepX;
			}
			y += stepY;
			x = stepX;
		}
	}
}
