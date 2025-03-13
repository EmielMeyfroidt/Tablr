package main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class RowsView extends AbstractView {

	private String table;
	private final int leftMargin = 20;
	private final int topMargin = 20;
	private final int stepY = 20;
	private List<Integer> selectedRows;
	private List<Integer> margins;


	public RowsView(TablrManager mgr, String table) {
		super(mgr);
		this.table = table;
		this.selectedRows = new ArrayList<Integer>();
	}

	@Override
	public void handleDoubleClick(int x, int y) {
		int elementNumber = (int) Math.floor(y / this.stepY) -1;
		if (elementNumber > getMgr().getColumns(table).getFirst().size()) {
			this.getMgr().addRow(table);
		}
	}

	@Override
	public void handleSingleClick(int x, int y) {
		int elementNumber = (int) Math.floor((y - topMargin) / this.stepY);
		if ((elementNumber <= getMgr().getColumnNames(table).size())) {
			if (x < leftMargin) {
				// Left margin of table, indicate that selected
				selectedRows.add(elementNumber);
				fireModeChanged(this);}
			else {
				// Click on table, edit row
				fireModeChanged(new EditRowView(this.getMgr(), this, table, this.getMgr().getColumnNames(table).get(locateColumn(x)), elementNumber));
			}
		}
	}
	private int locateColumn(int x) {
		int columnIndex = 0;
		for (int i = 0; i < margins.size(); i++) {
			if (x > margins.get(i)) {
				columnIndex = i;
			}
		}
		return columnIndex;
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
		System.out.println("switch to design");
		DesignView newView = new DesignView(getMgr(), table);
		newView.setChangeModeListeners(getChangeModeListeners());
		fireModeChanged(newView);
	}

	@Override
	public void handleEnter() {
		// TODO Auto-generated method stub
	}

	@Override
	public void handleDelete() {
		for(int row : selectedRows) {
			getMgr().removeRow(table, row);
		}
	}

	@Override
	public void handleCharTyped(char keyChar) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getTitle() {
		return "Rows Mode";
	}

	@Override
	public void paint(Graphics g) {
		int charSize = 10;

		List<List<String>> columns = getMgr().getColumns(table);
		int currentMargin = leftMargin;
		this.margins = new ArrayList<>();
		margins.add(currentMargin);
		for (int col = 0; col < columns.size()-1; col++) {
			int maxLength = getMgr().getColumnNames(table).get(col).length();
			int maxLenOfCol = columns.get(col).stream().mapToInt(String::length).max().orElse(0);
			if (maxLength < maxLenOfCol) {
				maxLength = maxLenOfCol;
			}
			currentMargin += maxLength * charSize;
			margins.add(currentMargin);
		}

		for (int col = 0 ; col< getMgr().getColumnNames(table).size(); col++) {
			g.drawString(getMgr().getColumnNames(table).get(col), margins.get(col), topMargin);
		}
		for (int row = 0; row < columns.getFirst().size(); row++) {
			if (selectedRows.contains(row)) {
				g.drawString("*", 5, ((row +1) * stepY )+ topMargin);
			}
			for (int col = 0; col < columns.size(); col++) {
				g.drawString(columns.get(col).get(row), margins.get(col), ((row +1) * stepY )+ topMargin);
			}
		}
	}
}
