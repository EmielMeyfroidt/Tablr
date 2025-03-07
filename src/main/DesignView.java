package main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DesignView extends AbstractView {

	private final int stepX = 20;
	private final int stepY = 20;
	private Table table;

	public DesignView(TablrManager mgr, Table table) {
		super(mgr);
		this.table = table;
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
		return "Design Mode";
	}

	@Override
	public void paint(Graphics g) {
		List<List<String>> splitList = new ArrayList<>();
		for (String s : table.getColumnsInfo()) {
			List<String> columnData = Arrays.asList(s.split(" "));
			splitList.add(columnData);
		}
		int y = stepY;
		int x = stepX;
		for (List<String> l : splitList) {
			for (String s : l) {
				g.drawString(s, x, y);
				x += 3*stepX;
			}
			y+= stepY;
		}
		
	}

}
