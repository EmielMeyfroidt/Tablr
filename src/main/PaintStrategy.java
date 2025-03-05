package main;

import java.awt.Graphics;
import java.util.List;

public interface PaintStrategy {
	public abstract void paint(Graphics g, List<String> paintData);
}
