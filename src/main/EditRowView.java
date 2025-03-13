package main;

import java.awt.*;

public class EditRowView extends AbstractView {

	private RowsView underlyingMode;
	private String name;
	private String nameTable;
	private final String originalName;
	private Integer rowIndex;

	public EditRowView(TablrManager mgr, RowsView underlyingMode, String nameTable, String nameColumn, Integer rowIndex) {
		super(mgr);
		this.underlyingMode = underlyingMode;
		this.nameTable = nameTable;
		this.name = nameColumn;
		this.originalName = nameColumn;
		this.rowIndex = rowIndex;
		this.setChangeModeListeners(underlyingMode.getChangeModeListeners());
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
	}

	@Override
	public void handleBackSpace() {

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
	}

	@Override
	public String getTitle() {
		return "TODO";
	}

	@Override
	public void paint(Graphics g) {
		underlyingMode.paint(g);
	}


}
