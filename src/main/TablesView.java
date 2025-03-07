package main;

import java.awt.Graphics;
import java.util.List;

public class TablesView extends AbstractView {

	private final int stepX = 20;
	private final int stepY = 20;
	public TablesView(TablrManager mgr) {
		super(mgr);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleDoubleClick(int x, int y) {
		int elementNumber = (int) Math.floor(y/this.stepY);
		System.out.println(elementNumber);
		try {
			Table tableClicked = getMgr().getTables().get(elementNumber);
			getMgr().openTable(tableClicked);
			this.fireModeChanged(new DesignView(getMgr(), tableClicked));
		}catch(Exception e){
			getMgr().addTable();
		}
		
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
	
	@Override
	public void paint(Graphics g) {
		int y = stepY;
		for (String table : getMgr().getTableNames()) {
			g.drawString(table, stepX, y);
			y += stepY;
		}
	}

	@Override
	public String getTitle() {
		return "Tables Mode";
	}
}
