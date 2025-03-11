package main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class TablesView extends AbstractView {

	private final int stepX = 20;
	private final int stepY = 20;
	private List<String> selectedTables;
	
	public TablesView(TablrManager mgr) {
		super(mgr);
		this.selectedTables = new ArrayList<String>();
	}

	@Override
	public void handleDoubleClick(int x, int y) {
		int elementNumber = (int) Math.floor(y/this.stepY);
		try {
			String tableClicked = getMgr().getTableNames().get(elementNumber);
			DesignView newView = new DesignView(getMgr(), tableClicked);
			newView.setChangeModeListeners(getChangeModeListeners());
			this.fireModeChanged(newView);
		}catch(Exception e){
			getMgr().addTable();
		}
	}

	@Override
	public void handleSingleClick(int x, int y) {
		int elementNumber = (int) Math.floor(y/this.stepY);
		if (x < stepX) {
			//Left margin of table, indicate that selected
			selectedTables.add(getMgr().getTableNames().get(elementNumber));
			fireModeChanged(this);
		} else if (elementNumber <= getMgr().getTableNames().size()) {
			//Click on table, edit name
			fireModeChanged(new EditNameView(this.getMgr(), this, this.getMgr().getTableNames().get(elementNumber)));
		}
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
	public void handleCtrlEnter() {
		// TODO Auto-generated method stub
	}

	@Override
	public void handleEnter() {
		// TODO Auto-generated method stub
	}

	@Override
	public void handleDelete() {
		for (String t : selectedTables) {
			getMgr().removeTable(t);
		}
		selectedTables.clear();
	}

	@Override
	public void handleCharTyped(char keyChar) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void paint(Graphics g) {
		int y = stepY;
		for (String table : getMgr().getTableNames()) {
			if (selectedTables.contains(table)) {
				g.drawString("*", 0, y);
			}
			g.drawString(table, stepX, y);
			y += stepY;
		}
	}

	@Override
	public String getTitle() {
		return "Tables Mode";
	}
}
