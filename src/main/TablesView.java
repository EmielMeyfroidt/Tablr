package main;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TablesView extends AbstractView {

	public TablesView(TablrManager mgr) {
		super(mgr);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return a list of the names of the tables in the system
	 * 
	 */
	@Override
	public List<String> getPaintData() {
		return this.getMgr().getTableNames();
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
