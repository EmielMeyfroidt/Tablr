package main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PaintDesignMode implements PaintStrategy {

	@Override
	public String getTitle() {
		return "Design Mode";
	}

	/**
	 * For each column, the name, the 
	 * type, whether blanks are allowed, 
	 * and the default value are shown. 
	 */
	@Override
	public void paint(Graphics g, List<String> paintData, int stepX, int stepY) {
		System.out.println("ik ben aan het tekenen");
		System.out.println(paintData);
		List<List<String>> splitList = new ArrayList<>();
		for (String s : paintData) {
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
			y+=stepY;
		}
	}

}
