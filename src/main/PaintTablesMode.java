package main;

import java.awt.Graphics;
import java.util.List;

public class PaintTablesMode implements PaintStrategy {

	@Override
	public void paint(Graphics g, List<String> paintData, int stepX, int stepY) {
		int y = stepY;
		for (String table : paintData) {
			g.drawString(table, stepX, y);
			y += stepY;
		}
	}

	@Override
	public String getTitle() {
		return "Tables Mode";
	}

}
