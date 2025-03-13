package main;

import java.awt.*;

public class EditRowView extends AbstractView {

	private RowsView underlyingMode;
	private String nameColumn;
	private String nameTable;
	private Integer rowIndex;
	private String value;

	public EditRowView(TablrManager mgr, RowsView underlyingMode, String nameTable, String nameColumn, Integer rowIndex) {
		super(mgr);
		this.underlyingMode = underlyingMode;
		this.nameTable = nameTable;
		this.nameColumn = nameColumn;
		this.rowIndex = rowIndex;
		this.setChangeModeListeners(underlyingMode.getChangeModeListeners());
		value = getMgr().getCell(nameTable, nameColumn, rowIndex);
	}

	@Override
	public void handleDoubleClick(int x, int y) {
		// nothing should happen
		
	}

	@Override
	public void handleSingleClick(int x, int y) {
		//TODO: check for validity
		fireModeChanged(underlyingMode);
	}

	@Override
	public void handleEscape() {
		fireModeChanged(underlyingMode);
	}

	@Override
	public void handleBackSpace() {
		if (!value.isEmpty()) {
			getMgr().updateCell(nameTable, nameColumn, rowIndex, value.substring(0, value.length() - 1));
			value = value.substring(0, value.length() - 1);
		}
	}

	@Override
	public void handleCtrlEnter() {
		// nothing should happen
	}

	@Override
	public void handleEnter() {
		//TODO: check for validity
		fireModeChanged(underlyingMode);
	}

	@Override
	public void handleDelete() {
		// nothing should happen
	}

	@Override
	public void handleCharTyped(char keyChar) {
		getMgr().updateCell(nameTable, nameColumn, rowIndex, value += keyChar);
	}

	@Override
	public String getTitle() {
		return "Editing value of " + nameColumn + " at row " + rowIndex;
	}

	@Override
	public void paint(Graphics g) {
		underlyingMode.paint(g);
	}

}
