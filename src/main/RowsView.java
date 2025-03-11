package main;

import java.awt.Graphics;

public class RowsView extends AbstractView {

	public RowsView(TablrManager mgr) {
		super(mgr);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleDoubleClick(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleSingleClick(int x, int y) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub

	}

}
