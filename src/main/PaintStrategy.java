package main;

import java.awt.Graphics;
import java.util.List;

public interface PaintStrategy {
	public abstract String getTitle();
	public abstract void paint(Graphics g, List<String> paintData, int stepX, int stepY);
}
