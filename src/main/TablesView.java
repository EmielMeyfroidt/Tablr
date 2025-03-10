package main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class TablesView extends AbstractView {

	private final int stepX = 20;
	private final int stepY = 20;
	private List<Table> selectedTables;
	
	public TablesView(TablrManager mgr) {
		super(mgr);
		this.selectedTables = new ArrayList<Table>();
	}

	@Override
	public void handleDoubleClick(int x, int y) {
		int elementNumber = (int) Math.floor(y/this.stepY);
		System.out.println(elementNumber);
		try {
			Table tableClicked = getMgr().getTables().get(elementNumber);
			this.fireModeChanged(new DesignView(getMgr(), tableClicked));
		}catch(Exception e){
			getMgr().addTable();
		}
		
	}

	@Override
	public void handleSingleClick(int x, int y) {
		int elementNumber = (int) Math.floor(y/this.stepY);
		if (x < stepX) {
			//Left margin of table, indicate that selected
			selectedTables.add(getMgr().getTables().get(elementNumber));
			fireModeChanged(this);
		} else if (elementNumber <= getMgr().getTables().size()) {
			//Click on table, edit name
			fireModeChanged(new EditNameView(this.getMgr(), this, this.getMgr().getTables().get(elementNumber)));
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
		for (Table t : selectedTables) {
			getMgr().getTables().remove(t);
		}
		fireModeChanged(this);
	}

	@Override
	public void handleCharTyped(char keyChar) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void paint(Graphics g) {
		int y = stepY;
		for (Table table : getMgr().getTables()) {
			if (selectedTables.contains(table)) {
				g.drawString("*", 0, y);
			}
			g.drawString(table.getName(), stepX, y);
			y += stepY;
		}
	}

	@Override
	public String getTitle() {
		return "Tables Mode";
	}
}
