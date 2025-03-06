package main;

import java.util.List;

public class DesignView extends AbstractView {

	private Table table;

	public DesignView(TablrManager mgr, Table table) {
		super(mgr);
		this.table = table;
	}

	/**
	 * In Table Design mode, the application window shows the list of the columns of
	 * a particular table, in a tabular view. For each column, the name, the type,
	 * whether blanks are allowed, and the default value are shown.
	 */
	@Override
	public List<String> getPaintData() {
		return this.table.getColumnsInfo();
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
		// TODO Auto-generated method stub

	}

	@Override
	public void handleBackSpace() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object handleCtrlEnter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object handleEnter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object handleDelete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object handleCharTyped(char keyChar) {
		// TODO Auto-generated method stub
		return null;
	}

}
